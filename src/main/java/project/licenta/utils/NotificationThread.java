package project.licenta.utils;

import project.licenta.entity.Reminder;
import project.licenta.service.ReminderService;

import java.awt.*;
import java.util.List;

public class NotificationThread extends  Thread
{
    /*private String user;
    public NotificationThread(String user)
    {
        this.user=user;
    }*/
    public void run()
    {
        ReminderService reminderService = GetInstance.of(ReminderService.class);
        List<Reminder> all = reminderService.findAll();
        for(Reminder reminder : all)
        {
            //if(reminder.getUsername().equals(user))
            //{
                Notification not = new Notification(reminder.getTitle(),reminder.getMessage(),reminder.getDeadline(),reminder.getDays());
                try {
                    not.display();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            //}
        }
    }
}
