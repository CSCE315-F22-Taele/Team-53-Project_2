import java.awt.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument.LeafElement;
import java.sql.*;
import java.sql.DriverManager;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  
import java.time.LocalDateTime;  
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;


public class openWindow {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("Entered Pom and Honey POS System. ");

    int employeeid;

    openWindow(int passCode){
        label.setBounds(0,0,200, 100);

        frame.add(label);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);

        set_employeeid(passCode);
        cashierGUI cashier = new cashierGUI();

        cashier.set_employeeid(employeeid);

        
        // cashier.setVisible(true);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void set_employeeid(int passCode){
        employeeid= passCode;
    }

    public static void main(String args[]){
        new openWindow(0);
    }
}