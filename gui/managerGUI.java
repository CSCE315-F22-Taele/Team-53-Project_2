import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;


public class managerGUI implements ActionListener{

    ////////// Declaration //////////

    // Buttons
    JButton inventoryBtn = new JButton("INVENTORY");
    JButton menuBtn = new JButton("MENU");
    JButton salesBtn = new JButton("SALES REPORT");
    JButton excessBtn = new JButton("EXCESS REPORT");
    JButton restockBtn = new JButton("RESTOCK REPORT");
    JButton backToCashier = new JButton("BACK TO CASHIER");

    // Dimensions
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;

    // Panels
    JPanel panel = new JPanel();

    // Frame
    JFrame f = new JFrame();

    // Const Vars
    int i = 0;
    Connection conn;
    int employeeid;

    managerGUI(int id){

        employeeid = 5; // hardcode to test, remove later

        // Buttons
        inventoryBtn.addActionListener(this);
        menuBtn.addActionListener(this);
        salesBtn.addActionListener(this);
        excessBtn.addActionListener(this);
        restockBtn.addActionListener(this);
        backToCashier.addActionListener(this);

        // Panel Format
        panel.setBounds((int)(width * 0.025),(int)(height*0.04) , (int)(width*0.95), (int)(height*0.9));
        Color pink = new Color(244, 220, 245);
        panel.setBackground(pink);

        panel.add(inventoryBtn);
        panel.add(menuBtn);
        panel.add(salesBtn);
        panel.add(excessBtn);
        panel.add(restockBtn);
        panel.add(backToCashier);
        
        f.add(panel);

        // frame
        f.setSize(screenSize);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == inventoryBtn){
            new inventoryGUI(employeeid); 
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource() == menuBtn){
            new menuItemGUI(employeeid); 
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }
    }

    public Connection connectionSet() {
        dbSetup my = new dbSetup();
        // Building the connection
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce331_904_53",
                    my.user, my.pswd);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection Failed");
        }

        return conn;
    }

    public static void main(String[] args) {
        new managerGUI(0);
    }

}