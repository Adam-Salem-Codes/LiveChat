package UI;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import Networking.Communication;

public class MainMenu {
    JButton connect, settings, host;
    //===========Constructor===========//
    public MainMenu(JPanel parent, Communication c) {
        SpringLayout layout = new SpringLayout();
        connect = new JButton("Connect?");
        settings = new JButton("Settings...");
        host = new JButton("Host?");
        layout.putConstraint(SpringLayout.WEST, connect, 15, SpringLayout.EAST, settings);
        layout.putConstraint(SpringLayout.WEST, host, 15, SpringLayout.EAST, connect);

        //connect.setBounds(75, 375, 125, 25);

        // For the x, 500 width - 425 = 75 - button width to align the same as the other side
        // Button left corner aligned...
        //settings.setBounds(425-125, 375, 125, 25);
        connect.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog("Enter IP:");

            while(!Communication.isValidIPAddress(ip))
            {
                JOptionPane.showMessageDialog(parent, "Invalid IPV4 address...", "Error", JOptionPane.ERROR_MESSAGE);

                ip = JOptionPane.showInputDialog("Enter IP:");
            }

            if (!c.connect(ip)){
                int reply = JOptionPane.showConfirmDialog(null, "Want to try again?", "Connection failure... Try again?", JOptionPane.YES_NO_OPTION);

                while(reply == JOptionPane.YES_OPTION)
                    if (!c.connect(ip))
                        reply = JOptionPane.showConfirmDialog(null, "Want to try again?", "Connection failure... Try again?", JOptionPane.YES_NO_OPTION);
            }
        });
        settings.addActionListener(e -> {
            try {
                new SettingsMenu(parent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        host.addActionListener(e -> {
            c.start_host(6000);

        });

        parent.setLayout(layout);
        parent.add(connect);
        parent.add(settings);
        parent.add(host);
        parent.revalidate();
        SwingUtilities.getWindowAncestor(parent).addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent we){
                System.exit(0);
            }
            /* Makes sure if parent JFrame (Main Menu JFrame) is closed
            the whole program stops including networking...
            */
        });
    }
}

