package project.licenta.notifications;

import java.awt.*;
import java.util.Calendar;

public class Notification {

    private String title;
    private String message;
    private Calendar deadline;
    private String days;


    public Notification(String title, String message, Calendar deadline, String days) {
        this.title = title;
        this.message = message;
        this.deadline = deadline;
        this.days = days;
    }

    public void display() throws AWTException {
        Calendar c = Calendar.getInstance();
        switch (days) {
            case "Day":
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 1);
                break;
            case "Week":
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 7);
                break;
            case "Month":
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
                break;
            default:
                break;
        }
        if (c.get(Calendar.YEAR) == deadline.get(Calendar.YEAR) && c.get(Calendar.MONTH) == deadline.get(Calendar.MONTH)
                && c.get(Calendar.DAY_OF_MONTH) == deadline.get(Calendar.DAY_OF_MONTH)) {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("@images/icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "MYUNISIT: Reminders");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);

        }

    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", deadline=" + deadline +
                ", days='" + days + '\'' +
                '}';
    }
}
