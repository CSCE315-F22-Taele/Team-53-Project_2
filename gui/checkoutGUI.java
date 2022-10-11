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
        JButton cashButton = new JButton("CASH");
        cashButton.setBounds(130,100,100, 40);

        JButton cardButton = new JButton("CREDIT / DEBIT");
        cashButton.setBounds(130,200,100, 40);
        
        JButton ddButton = new JButton("DINING DOLLARS");
        cashButton.setBounds(130,300,100, 40);

        JButton mealswipeButton = new JButton("MEAL SWIPES");
        cashButton.setBounds(130,400,100, 40);
        ////////// Menu-items Area //////////
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.yellow);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new GridLayout(5, 2, 10, 10)); 

        itemsPanel.add(cashButton);
        itemsPanel.add(cardButton);
        itemsPanel.add(ddButton);
        itemsPanel.add(mealswipeButton);

        f.add(itemsPanel);

        f.setSize(screenSize.width, screenSize.height);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String args[]) {
        new checkoutGUI();
    }
}
