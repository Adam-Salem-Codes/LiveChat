package UI;

import java.awt.Dimension;


import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SpringLayout;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import Logging.Logger;

public class SettingsMenu extends Frame {
    public static LookAndFeel[] names = {new FlatMacDarkLaf(), new FlatMacLightLaf() , new FlatDarkLaf(), new FlatDarculaLaf(), new FlatLightLaf()};
    //===========Constructor===========//
    public SettingsMenu(JPanel p) throws Exception {
        Logger log = new Logger();
        SpringLayout layout = new SpringLayout();
        JComboBox<LookAndFeel> b = new JComboBox<LookAndFeel>(names);
        b.setPreferredSize(new Dimension(250, 35));
        JLabel l = new JLabel("Theme of app.");

        layout.putConstraint(SpringLayout.WEST, l, 15, SpringLayout.EAST, b);
        add(l);
        b.addActionListener(e -> {
            System.out.println("Action listener...");
            setAllLaf((LookAndFeel) b.getSelectedItem());
        });
        init();
        setTitle("Settings");
        setLayout(layout);
        add(b);
        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent we){
                log.set_saved_theme((LookAndFeel) b.getSelectedItem());
                log.dispose();
            }
        });
    }

}
