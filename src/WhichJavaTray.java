import java.awt.*;
import java.net.MalformedURLException;
import java.awt.TrayIcon.MessageType;

public class WhichJavaTray {

    public static void main(String[] args) throws AWTException, MalformedURLException {
        if (SystemTray.isSupported()) {
            WhichJavaTray wjt = new WhichJavaTray();
            wjt.displayTray();
        } else {
            System.err.println("System tray not supported!");
        }
    }

    public void displayTray() throws AWTException, MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
    }
}
