
package project.licenta.notifications;

import project.licenta.entity.Reminder;
import project.licenta.service.ReminderService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NotificationObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ReminderService reminderService = GetInstance.of(ReminderService.class);
        List<Reminder> all = reminderService.findAll();
        for (Reminder reminder : all) {
            if (reminder.getUsername().equals(arg)) {
                Notification not = new Notification(reminder.getTitle(), reminder.getMessage(), reminder.getDeadline(), reminder.getDays());
                try {
                    not.display();

                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
