package project.licenta.notifications;


import project.licenta.entity.Reminder;
import project.licenta.entity.User;
import project.licenta.service.ReminderService;
import project.licenta.utils.GetInstance;
import project.licenta.utils.Notification;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

public class NotificationObservable extends Observable implements Runnable {
    private String user;
    private boolean initialPass = false;
    private Calendar today;


    public String getUser() {
        return user;
    }
    public void setUser(String user) {this.user = user;}
    public Calendar getToday(){return today;}
    public void setToday(Calendar today){this.today = today;}

    public void checkDatabase() {
        ReminderService reminderService = GetInstance.of(ReminderService.class);
        List<Reminder> all = reminderService.findAll();
        for (Reminder reminder : all) {
            if (reminder.getUsername().equals(user)) {
                Notification not = new Notification(reminder.getTitle(), reminder.getMessage(), reminder.getDeadline(), reminder.getDays());
                try {
                    not.display();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void trigger() {

        while (true) { // check if a day has passed check LocalDate || Calendar
            if (!initialPass) {
                checkDatabase();
                this.initialPass = true;
            }
            try {
                Thread.sleep(1000);
               if(today.get(Calendar.MINUTE)+1==Calendar.getInstance().get(Calendar.MINUTE))
               {

                   setChanged();
                   notifyObservers(user);
                   setToday(Calendar.getInstance());
               }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void run() {
        trigger();
    }
}