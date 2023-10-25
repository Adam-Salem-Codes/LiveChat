package UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import Logging.Logger;

import java.awt.Window;
import javax.swing.SwingUtilities;


public class Frame extends JFrame  {

    //===========Constructor===========//
    public JPanel init() throws Exception {
        JPanel canvas = new JPanel();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.add(canvas);
        
        this.setVisible(true);
        LookAndFeel savedLaf = new FlatMacDarkLaf();
        for(int i = 0; i < SettingsMenu.names.length; i++){
            if(SettingsMenu.names[i].toString().contains((String)Logger.get_saved_theme())){
                if(SettingsMenu.names[i] != null)
                    savedLaf = SettingsMenu.names[i];
                break;
            }            
        }
        Frame.setAllLaf(savedLaf);

        return canvas;

    } 
    public static void setAllLaf(LookAndFeel laf){
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Failed to set theme...", "UI Error.", JOptionPane.ERROR_MESSAGE);
        }
        for (Window window : JFrame.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }
}
