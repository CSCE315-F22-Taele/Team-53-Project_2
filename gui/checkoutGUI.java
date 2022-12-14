import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements the checkout graphical user interface. This helps us submit an order information to the checkout data table.
 */
public class checkoutGUI implements ActionListener {

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

    JLabel total_price = new JLabel();

    double amount;
    Connection conn;
    int employeeid;
    int orderid;

    /**
     * Setup the Checkout GUI
     * @param order  The orderid number.
     * @param price  The amount that needs to be charged for one order.
     * @param id     The employee id of the cashier taking that order.
     */
    public checkoutGUI(int order, double price, int id) {
        dbConnect c1= new dbConnect();
        conn = c1.connectionSet();

        orderid = order;
        amount = price;
        employeeid = id;

        f.setLayout(new BorderLayout());

        cashButton.setBounds(130, 100, 100, 40);

        cardButton.setBounds(130, 200, 100, 40);

        ddButton.setBounds(130, 300, 100, 40);

        mealswipeButton.setBounds(130, 400, 100, 40);

        logoutButton.setBounds(130, 600, 50, 40);

        cancelButton.setBounds(200, 600, 50, 40);

        Color blueCute = new Color(194, 194, 252);

        ////////// Payment Area //////////
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(blueCute);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new GridLayout(5, 2, 10, 10));

        itemsPanel.add(cashButton);
        itemsPanel.add(cardButton);
        itemsPanel.add(ddButton);
        itemsPanel.add(mealswipeButton);

        f.add(itemsPanel, BorderLayout.NORTH);

        ////////// Options Area //////////
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBounds((int) (width * 0.06), (int) ((height * 0.75)), (int) (width * 0.6),
                (int) (height * 0.1));
        optionsPanel.setLayout(new GridLayout(1, 3));

        optionsPanel.add(logoutButton);
        optionsPanel.add(cancelButton);

        f.add(optionsPanel);

        ////////// Recipt Area //////////

        JPanel receiptPanel = new JPanel();
        JLabel receiptTitle = new JLabel("RECEIPT");
        JLabel itemTitle = new JLabel("ITEM");
        JLabel quantityTitle = new JLabel("QUANTITY");
        String str_amt = "Total Sale: " + String.valueOf(amount);
        JLabel totalSale = new JLabel(str_amt);

        // Panel Setup
        receiptPanel.setBackground(blueCute);
        receiptPanel.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) height);
        receiptPanel.setLayout(null);

        // Position
        receiptTitle.setBounds((int) (width * 0.3 / 2 - 60), 0, 200, 100);
        itemTitle.setBounds((int) (width * 0.065), 70, 100, 100);
        quantityTitle.setBounds((int) (width * 0.19), 70, 100, 100);
        totalSale.setBounds((int) (width * 0.11), (int) (height * 0.6), 200, (int) (height * 0.08));
        

        // Font
        receiptTitle.setFont(new Font("Serif", Font.PLAIN, 28));
        itemTitle.setFont(new Font("Serif", Font.PLAIN, 20));
        quantityTitle.setFont(new Font("Serif", Font.PLAIN, 20));
        totalSale.setFont(new Font("Serif", Font.PLAIN, 20));
        cashButton.setFont(new Font("Serif", Font.PLAIN, 20));
        cardButton.setFont(new Font("Serif", Font.PLAIN, 20));
        ddButton.setFont(new Font("Serif", Font.PLAIN, 20));
        cardButton.setFont(new Font("Serif", Font.PLAIN, 20));
        mealswipeButton.setFont(new Font("Serif", Font.PLAIN, 20));
       

        // Add to panel
        receiptPanel.add(receiptTitle);
        receiptPanel.add(itemTitle);
        receiptPanel.add(quantityTitle);
        receiptPanel.add(totalSale);
        receiptPanel.add(total_price);
        
        cashButton.addActionListener(this);
        ddButton.addActionListener(this);
        cardButton.addActionListener(this);
        mealswipeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        cancelButton.addActionListener(this);

        f.add(receiptPanel);

        
        f.setSize(screenSize.width, screenSize.height);

        f.setLayout(null);
        f.setVisible(true);
    }

    /**
     * The actions that happen with the click of each button.
     * @param e  The button that was clicked.
     */
    public void actionPerformed(ActionEvent e) {
        // Collect each digit to a passtempCode
        String cardnumber;

        if (e.getSource() == cashButton) {
            try {
                get_checkout(conn, 3, null);
                JOptionPane.showMessageDialog(null, "Cash payment accepted");
            } catch (Exception cash) {
                JOptionPane.showMessageDialog(null, "Cash payment not accepted");
            }

            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            f.setVisible(false);

        } else if (e.getSource() == cardButton) {
            Random rd = new Random();
            cardnumber = Long.toString( Math.abs( rd.nextLong() / 1000) );

            try {
                get_checkout(conn, 2, cardnumber);
                JOptionPane.showMessageDialog(null, "Card payment accepted");
            } catch (Exception card) {
                JOptionPane.showMessageDialog(null, "Card payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == ddButton) {
            int uin = getRandomValue(100000000, 900000000);
            cardnumber = String.format("%d", uin);
            try {
                get_checkout(conn, 1, cardnumber);
                JOptionPane.showMessageDialog(null, "Dining Dollars payment accepted");
            } catch (Exception dd) {
                JOptionPane.showMessageDialog(null, "Dining Dollars payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == mealswipeButton) {
            int uin = getRandomValue(100000000, 900000000);
            cardnumber = String.format("%d", uin);
            try {
                get_checkout(conn, 0, cardnumber);
                JOptionPane.showMessageDialog(null, "Meal Swipes payment accepted");
            } catch (Exception dd) {
                JOptionPane.showMessageDialog(null, "Meal Swipes payment not accepted");
            }
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == logoutButton) {
            new loginGUI();
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == cancelButton) {
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }

    }

    /**
     * generate a random value.
     * @param  Min               A minimum range of values to randomly generate.
     * @param  Max               A maximum range of values to randomly generate.
     * @return     Random Integer generated between the range of min and max.
     */
    public static int getRandomValue(int Min, int Max) {

        return ThreadLocalRandom
                .current()
                .nextInt(Min, Max + 1);
    }

    /**
     * Insert into the checkout data table.
     * @param conn           Connection to database.
     * @param paymentMethod  Int enum value of Enum. one of the 4 options->
     * 0: meal swipes, 1: dining dollars, 2: card, 3: cash
     * @param cardnumber     The customers card number or UIN.
     */
    public void get_checkout(Connection conn, int paymentMethod, String cardnumber) {
        try {
            PreparedStatement checkoutStatement = conn.prepareStatement(
                    "INSERT INTO checkout(paymentmethod, amount, cardnumber, employeeid, orderid) VALUES (?, ?,?, ?, ?)");
            
            checkoutStatement.setDouble(2, amount);
            checkoutStatement.setInt(1, paymentMethod);
            checkoutStatement.setString(3, cardnumber);
            checkoutStatement.setInt(4, employeeid);
            checkoutStatement.setInt(5, orderid);

            checkoutStatement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "");
        }
    }


    /**
     * Checkout GUI main function.
     * @param args[]  main function.
     */
    public static void main(String args[]) {

        new checkoutGUI(0, 0, 0);
    }
}
