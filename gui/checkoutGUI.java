package gui;

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

public class checkoutGUI{

    JFrame f = new JFrame("Checkout GUI");
    

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    int width_item = 0;
    int item_width = (int) (width * 0.092);

    public checkoutGUI(){

        f.setLayout(new BorderLayout());

        JButton cashButton = new JButton("CASH");
        cashButton.setBounds(130,100,100, 40);

        JButton cardButton = new JButton("CREDIT / DEBIT");
        cardButton.setBounds(130,200,100, 40);
        
        JButton ddButton = new JButton("DINING DOLLARS");
        ddButton.setBounds(130,300,100, 40);

        JButton mealswipeButton = new JButton("MEAL SWIPES");
        mealswipeButton.setBounds(130,400,100, 40);

        JButton logoutButton = new JButton("LOGOUT");
        ddButton.setBounds(130,600,50, 40);

        JButton cancelButton = new JButton("CANCEL ORDER");
        ddButton.setBounds(200,600,50, 40);

        JButton editButton = new JButton("EDIT ORDER");
        ddButton.setBounds(270,600,50, 40);

        ////////// Payment Area //////////
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.green);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new GridLayout(5, 2, 10, 10)); 

        itemsPanel.add(cashButton);
        itemsPanel.add(cardButton);
        itemsPanel.add(ddButton);
        itemsPanel.add(mealswipeButton);

        ////////// Options Area //////////
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBounds((int) (width * 0.06), (int) ((height * 0.75)), (int) (width * 0.6), (int) (height * 0.1));
        optionsPanel.setLayout(new GridLayout(1, 3));

        optionsPanel.add(logoutButton);
        optionsPanel.add(cancelButton);
        optionsPanel.add(editButton);

        f.add(optionsPanel);

        f.add(itemsPanel, BorderLayout.NORTH);
        
        f.setSize(screenSize.width, screenSize.height);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String args[]) {
        new checkoutGUI();
    }
}
