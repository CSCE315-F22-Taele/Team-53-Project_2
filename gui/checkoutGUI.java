import javax.swing.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class checkoutGUI implements ActionListener{

    JFrame f = new JFrame("Checkout GUI");
    

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    int width_item = 0;
    int item_width = (int) (width * 0.092);
    JButton cashButton = new JButton("CASH");
    JButton cardButton = new JButton("CREDIT / DEBIT");
    JButton ddButton = new JButton("DINING DOLLARS");
    JButton mealswipeButton = new JButton("MEAL SWIPES");
    JButton logoutButton = new JButton("LOGOUT");
    JButton cancelButton = new JButton("CANCEL ORDER");
    JButton editButton = new JButton("EDIT ORDER");
    JLabel total_price = new JLabel();

    double amount;
    Connection conn; 
    int employeeid; 
    int orderid;

    public checkoutGUI( int order, double price, int id){
        conn = connectionSet();
        orderid= order;
        amount = price;
        employeeid = id;


        f.setLayout(new BorderLayout());

       
        cashButton.setBounds(130,100,100, 40);

        
        cardButton.setBounds(130,200,100, 40);
        
        
        ddButton.setBounds(130,300,100, 40);

        
        mealswipeButton.setBounds(130,400,100, 40);

        
        logoutButton.setBounds(130,600,50, 40);

       
        cancelButton.setBounds(200,600,50, 40);

        
        editButton.setBounds(270,600,50, 40);


        ////////// Payment Area //////////
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.green);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new GridLayout(5, 2, 10, 10)); 

        itemsPanel.add(cashButton);
        itemsPanel.add(cardButton);
        itemsPanel.add(ddButton);
        itemsPanel.add(mealswipeButton);

        f.add(itemsPanel, BorderLayout.NORTH);

        ////////// Options Area //////////
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBounds((int) (width * 0.06), (int) ((height * 0.75)), (int) (width * 0.6), (int) (height * 0.1));
        optionsPanel.setLayout(new GridLayout(1, 3));

        optionsPanel.add(logoutButton);
        optionsPanel.add(cancelButton);
        optionsPanel.add(editButton);

        f.add(optionsPanel);

        ////////// Recipt Area //////////
        // TODO: integrate w cashier, get actual values from cashier

        JPanel receiptPanel = new JPanel();
        JLabel receiptTitle = new JLabel("RECEIPT");
        JLabel itemTitle = new JLabel("ITEM");
        JLabel quantityTitle = new JLabel("QUANTITY");
        String str_amt = "Total Sale: " +String.valueOf(amount);
        JLabel totalSale = new JLabel(str_amt);

        // Panel Setup
        receiptPanel.setBackground(Color.green);
        receiptPanel.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) height);
        receiptPanel.setLayout(null);

        // Position
        receiptTitle.setBounds((int) (width * 0.3 / 2 - 60), 0, 200, 100);
        itemTitle.setBounds((int) (width * 0.065), 70, 100, 100);
        quantityTitle.setBounds((int) (width * 0.19), 70, 100, 100);
        totalSale.setBounds((int) (width * 0.11), (int) (height * 0.6), 200, (int) (height * 0.08));
        // sale.setBounds((int) (width * 0.2), (int) (height * 0.6), 200, (int) (height * 0.08));
        // checkoutBtn.setBounds((int) (width * 0.09), (int) (height * 0.8), (int) (width * 0.15), (int) (height * 0.1));

        // Font
        receiptTitle.setFont(new Font("Arial", Font.PLAIN, 28));
        itemTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        quantityTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        totalSale.setFont(new Font("Arial", Font.PLAIN, 20));
        // sale.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add to panel
        receiptPanel.add(receiptTitle);
        receiptPanel.add(itemTitle);
        receiptPanel.add(quantityTitle);
        receiptPanel.add(totalSale);
        receiptPanel.add(total_price);
        // receiptPanel.add(sale);
        // receiptPanel.add(checkoutBtn);

        // for (int i = 0; i < 10; ++i) {
        //     receiptPanel.add(labelArr[i]);
        //     receiptPanel.add(inputArr[i]);
        // }
        cashButton.addActionListener(this);
        ddButton.addActionListener(this);
        cardButton.addActionListener(this);
        mealswipeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        cancelButton.addActionListener(this);
        editButton.addActionListener(this);

        f.add(receiptPanel);
            
        f.setSize(1400, 1600);
        
        f.setLayout(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Collect each digit to a passtempCode
        String cardnumber; 

        if (e.getSource() == cashButton) {
            try{
                get_checkout(conn, 3, null);
                JOptionPane.showMessageDialog(null, "Cash payment accepted");
            }
            catch(Exception cash){
                JOptionPane.showMessageDialog(null, "Cash payment not accepted");
            }
            
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            
        } else if (e.getSource() == cardButton) {
            Random rd = new Random();
            cardnumber = Long.toString(rd.nextLong() / 1000);

            try{
                get_checkout(conn, 2, cardnumber);
                JOptionPane.showMessageDialog(null, "Card payment accepted");
            }
            catch(Exception card){
                JOptionPane.showMessageDialog(null, "Card payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            
            
        } else if (e.getSource() == ddButton) {
            int uin = getRandomValue(100000000, 900000000);
            cardnumber = String.format("%d", uin);
            try{
                get_checkout(conn, 1, cardnumber);
                JOptionPane.showMessageDialog(null, "Dining Dollars payment accepted");
            }
            catch(Exception dd){
                JOptionPane.showMessageDialog(null, "Dining Dollars payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            
        } else if (e.getSource() == mealswipeButton) {
            int uin = getRandomValue(100000000, 900000000);
            cardnumber = String.format("%d", uin);
            try{
                get_checkout(conn, 0, cardnumber);
                JOptionPane.showMessageDialog(null, "Meal Swipes payment accepted");
            }
            catch(Exception dd){
                JOptionPane.showMessageDialog(null, "Meal Swipes payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        }else if (e.getSource() == logoutButton) {
            new loginGUI(); 
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    
        }
    }
    
    public static int getRandomValue(int Min, int Max) {
        // FIX ME: SEE IF WE CAN RANDOMIZE THIS SO THAT THE AMOUNT IS 0 50% OF THE TIME,
        // 1 30% AND MORE THAN 1 OTHER: 20%
        return ThreadLocalRandom
            .current()
            .nextInt(Min, Max + 1);
      }

    public void get_checkout(Connection conn, int paymentMethod, String cardnumber){
        try{
            PreparedStatement checkoutStatement = conn.prepareStatement("INSERT INTO checkout(paymentmethod, amount, cardnumber, employeeid, orderid) VALUES (?, ?,?, ?, ?)");
          // checkoutStatement.setInt(1, checkoutid)
          checkoutStatement.setDouble(2, amount);
          checkoutStatement.setInt(1, paymentMethod);
          checkoutStatement.setString(3, cardnumber);
          checkoutStatement.setInt(4, employeeid);
          checkoutStatement.setInt(5, orderid);

          checkoutStatement.executeUpdate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "");
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
            JOptionPane.showMessageDialog(null, "Connection not made with database.");
        } // end try catch
          
        return conn;
    }

    public static void main(String args[]) {
        
        new checkoutGUI( 0, 0, 0);
    }
}
