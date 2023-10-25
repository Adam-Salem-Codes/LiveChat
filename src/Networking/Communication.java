package Networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Communication {

    protected Socket connection;
    protected ServerSocket server;
    protected PrintWriter pw;
    protected ServerSocket ssocket;
    int i = 0;

    // ===========Constructor===========//
    public Communication() {
        connection = new Socket();
        try {
            ssocket = new ServerSocket();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't start server...", "Networking Error.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void start_host(int port) {
        try {
            ssocket = new ServerSocket(port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't start server...", "Networking Error.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void send_data(String data) {
        if (!(connection.isConnected() || connection.isClosed()))
            return;
        try {
            pw = new PrintWriter(connection.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to send message...", "Networking Error.",
                    JOptionPane.ERROR_MESSAGE);
        }
        pw.println(data);
        pw.flush();
    }

    public boolean connect(String ip) {
        connection = new Socket();
        try {
            connection.setSoTimeout(5000);

            JOptionPane pane = new JOptionPane();
            pane.setMessage("Connection timeout:");
            JProgressBar jProgressBar = new JProgressBar(1, connection.getSoTimeout());
            jProgressBar.setValue(15);
            pane.add(jProgressBar, 1);

            JDialog dialog = pane.createDialog("Connecting...");
            new Thread(()->{new Timer().schedule(new TimerTask() {
                public void run() {
                    try {
                        jProgressBar.setValue(i);
                        System.out.println(i);
                        if(i == connection.getSoTimeout()){
                            dialog.dispose();
                            Thread.currentThread().join();
                        }
                    } catch (Exception e) {
                    }
                    i++;
                }
            }, 0, 2);}).start();
            dialog.setVisible(true);

            connection.connect(new InetSocketAddress(ip, 6000), 5000);

            if(connection.isConnected())
                JOptionPane.showMessageDialog(null, "Connected...", "Information...",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception...", "Networking error...",
                    JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
            return false;
        }

        return true; // No error.
    }

    public void receive() {

    }

    public void dispose() {
        pw.close();
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Credit for validator: https://www.techiedelight.com/validate-ip-address-java/
    public static boolean isValidIPAddress(String ipv4) {

        // Regex for digit from 0 to 255.
        String zeroTo255 = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (ipv4 == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        Matcher m = p.matcher(ipv4);

        // Return if the IP address
        // matched the ReGex
        return m.matches();
    }
}
