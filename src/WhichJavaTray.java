/*
Which Java Tray
By Sarah Pierce
3/18/2018

Displays the current Java JDK version in the Windows tray.
Useful for keeping track of your environment when switching between JDK versions during development.
Based on the JAVA_HOME environment variable.  I suggest you use %JAVA_HOME%\bin in your path, instead of the absolute path.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        Image image;
        String ver = System.getenv("JAVA_HOME");
        String imageName;
        String tip;
        String caption;

        if (ver.startsWith("1.7",ver.lastIndexOf("\\jdk")+4)) {
            imageName = "7.png";
            tip = "JDK 7";
            caption = "Running JDK version 7";
        } else if (ver.startsWith("1.8",ver.lastIndexOf("\\jdk")+4)) {
            imageName = "8.png";
            tip = "JDK 8";
            caption = "Running JDK version 8";
        } else {
            imageName = "err.png";
            tip = "Unknown JDK";
            caption = "Running unknown JDK version";
            System.err.println("JAVA_HOME not set to 1.7 nor 1.8.");
        }

        image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(imageName));

        PopupMenu popup = new PopupMenu();

        TrayIcon trayIcon = new TrayIcon(image, "Which Java Tray",popup);

        MenuItem about = new MenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame f = new Frame("Which Java Tray");
                f.setSize(300,100);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setLayout(new FlowLayout(FlowLayout.CENTER));
                Label aboutLabel1 = new Label();
                aboutLabel1.setAlignment(Label.CENTER);
                aboutLabel1.setText("Which Java Tray" );
                Label aboutLabel2 = new Label();
                aboutLabel2.setAlignment(Label.CENTER);
                aboutLabel2.setText("By: Sarah Pierce");
                Label aboutLabel3 = new Label();
                aboutLabel3.setAlignment(Label.CENTER);
                aboutLabel3.setText("http://www.munchicken.com/software");
                f.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        f.dispose();
                    }
                });
                f.add(aboutLabel1);
                f.add(aboutLabel2);
                f.add(aboutLabel3);
                f.setVisible(true);
            }
        });
        popup.add(about);

        MenuItem stop = new MenuItem("Quit");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
            }
        });
        popup.add(stop);

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(tip);
        tray.add(trayIcon);
        trayIcon.displayMessage(caption, "Which Java Tray", MessageType.INFO);

    }
}
