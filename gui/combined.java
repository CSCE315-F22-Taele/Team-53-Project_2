import javax.swing.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.text.AbstractDocument.LeafElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class combined {

    public combined() {
        class loginGUI implements ActionListener {
            private void makeFrameFullSize(JFrame aFrame) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                aFrame.setSize(screenSize.width, screenSize.height);
            }

            // Store the passcode
            String tempCode = "";
            int passCode;

            // Title Declaration
            JLabel title = new JLabel("LOGIN");

            // Button Declaration
            JButton btn0 = new JButton("0");
            JButton btn1 = new JButton("1");
            JButton btn2 = new JButton("2");
            JButton btn3 = new JButton("3");
            JButton btn4 = new JButton("4");
            JButton btn5 = new JButton("5");
            JButton btn6 = new JButton("6");
            JButton btn7 = new JButton("7");
            JButton btn8 = new JButton("8");
            JButton btn9 = new JButton("9");
            JButton btn10 = new JButton("ENTER");

            // Frame Declaration
            JFrame f = new JFrame("Login GUI");

            loginGUI() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                f.setSize(screenSize.width, screenSize.height);
                f.setBackground(Color.gray);

                // Position for each button and label
                title.setBounds(720, 40, 200, 100);
                btn1.setBounds(500, 150, 130, 130);
                btn2.setBounds(670, 150, 130, 130);
                btn3.setBounds(840, 150, 130, 130);
                btn4.setBounds(500, 300, 130, 130);
                btn5.setBounds(670, 300, 130, 130);
                btn6.setBounds(840, 300, 130, 130);
                btn7.setBounds(500, 460, 130, 130);
                btn8.setBounds(670, 460, 130, 130);
                btn9.setBounds(840, 460, 130, 130);
                btn0.setBounds(670, 620, 130, 130);
                btn10.setBounds(620, 770, 250, 130);

                // Add ActionListener to each button
                btn1.addActionListener(this);
                btn2.addActionListener(this);
                btn3.addActionListener(this);
                btn4.addActionListener(this);
                btn5.addActionListener(this);
                btn6.addActionListener(this);
                btn7.addActionListener(this);
                btn8.addActionListener(this);
                btn9.addActionListener(this);
                btn0.addActionListener(this);
                btn10.addActionListener(this);

                // Frame
                f.add(btn1);
                f.add(btn2);
                f.add(btn3);
                f.add(btn4);
                f.add(btn5);
                f.add(btn6);
                f.add(btn7);
                f.add(btn7);
                f.add(btn8);
                f.add(btn9);
                f.add(btn0);
                f.add(btn10);
                f.add(title);

                f.setSize(400, 400);
                f.setLayout(null);
                f.setVisible(true);
            }

            public void actionPerformed(ActionEvent e) {
                // Collect each digit to a passtempCode
                if (e.getSource() == btn1) {
                    tempCode += "1";
                } else if (e.getSource() == btn2) {
                    tempCode += "2";
                } else if (e.getSource() == btn3) {
                    tempCode += "3";
                } else if (e.getSource() == btn4) {
                    tempCode += "4";
                } else if (e.getSource() == btn5) {
                    tempCode += "5";
                } else if (e.getSource() == btn6) {
                    tempCode += "6";
                } else if (e.getSource() == btn7) {
                    tempCode += "7";
                } else if (e.getSource() == btn8) {
                    tempCode += "8";
                } else if (e.getSource() == btn9) {
                    tempCode += "9";
                } else if (e.getSource() == btn0) {
                    tempCode += "0";
                } else if (e.getSource() == btn10) {
                    passCode = Integer.parseInt(tempCode);

                    if (checkPassword(passCode)) {
                        // If yes, go directly to cashierGUI

                        // cashierGUI newGui = new cashierGUI();
                        new cashierGUI();
                        // newGui.get_employee(passCode);

                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        // FIX ME: ADD A WAY TO GET THE EMPLOYEE ID TO THE CASHIER PAGE

                        JOptionPane.showMessageDialog(null, "Pincode is correct.");
                        System.out.println(" true");
                    } else {
                        System.out.println("not true");
                        JOptionPane.showMessageDialog(null, "Pincode is incorrect. Please retry.");
                        tempCode = "";

                    }
                }
            }

            public boolean checkPassword(int password) {
                Connection conn = connectionSet();
                boolean value = false;
                try {
                    PreparedStatement employeeCheck = conn
                            .prepareStatement("SELECT exists (SELECT 1 FROM employee WHERE employeeid = (?))");
                    employeeCheck.setInt(1, password);
                    ResultSet employees = employeeCheck.executeQuery();

                    while (employees.next()) {
                        value = employees.getBoolean("exists");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error accessing Database.");
                }

                return value;
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
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                } // end try catch
                  // JOptionPane.showMessageDialog(null, "Opened database successfully");
                return conn;
            }
        }

        class cashierGUI implements ActionListener {
            private void makeFrameFullSize(JFrame aFrame) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                aFrame.setSize(screenSize.width, screenSize.height);
            }

            // Button declaration
            JButton btn1;
            JButton btn2;
            JButton btn3;
            JButton btn4;
            JButton btn5;
            JButton btn6;
            JButton btn7;
            JButton btn8;
            JButton btn9;
            JButton btn10;
            JButton logoutBtn;
            JButton checkoutBtn;
            JButton btnArr[] = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, logoutBtn, checkoutBtn };

            private JLabel labelEmployee;
            // Label Declaration
            JLabel l1 = new JLabel("GYRO");
            JLabel l2 = new JLabel("BOWL");
            JLabel l3 = new JLabel("2 falafels ");
            JLabel l4 = new JLabel("Extra pita and humus");
            JLabel l5 = new JLabel("Extra Chicken");
            JLabel l6 = new JLabel("Extra Harissa");
            JLabel l7 = new JLabel("Extra Sp Meatball");
            JLabel l8 = new JLabel("Extra Tzatziki Sauce");
            JLabel l9 = new JLabel("Extra Balsamic Vinegar");
            JLabel l10 = new JLabel("Fountain drink");
            JLabel labelArr[] = { l1, l2, l3, l4, l5, l6, l7, l8, l9, l10 };

            // Quantity input Declaration
            JTextField input_1 = new JTextField("0");
            JTextField input_2 = new JTextField("0");
            JTextField input_3 = new JTextField("0");
            JTextField input_4 = new JTextField("0");
            JTextField input_5 = new JTextField("0");
            JTextField input_6 = new JTextField("0");
            JTextField input_7 = new JTextField("0");
            JTextField input_8 = new JTextField("0");
            JTextField input_9 = new JTextField("0");
            JTextField input_10 = new JTextField("0");
            JTextField inputArr[] = { input_1, input_2, input_3, input_4, input_5, input_6, input_7,
                    input_8, input_9, input_10 };

            // Frame Declaraiton
            JFrame f = new JFrame("Cashier GUI");

            // Global Var --> Unused, hardcoded can change later
            int height_items;

            // Store quantity of each menu item
            int quantityArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

            // Price of each menu item
            double priceArr[] = priceArr();

            JLabel sale = new JLabel("0");
            double totalPrice = 0;

            Integer inventory[] = new Integer[24];

            // Increments
            int gyroClick = 0;
            int bowlClick = 0;
            int falafelClick = 0;
            int pitaAndHumusClick = 0;
            int extraChickenClick = 0;
            int extraMeatballClick = 0;
            int extraHarissaClick = 0;
            int extraTzatzikiSauceClick = 0;
            int extraVinegarClick = 0;
            int drinkClick = 0;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height;
            int width = screenSize.width;
            int width_item = 0;
            int item_width = (int) (width * 0.092);
            int quantity_width = (int) (width * 0.19);

            public cashierGUI() {
                ////////// Background //////////
                for (int i = 0; i < 24; i++) {
                    inventory[i] = 0;
                }

                f.setSize(screenSize.width, screenSize.height);
                f.setBackground(Color.gray); // TODO: Fix background color
                System.out.println(screenSize.height);
                System.out.println(screenSize.width);

                ////////// Buttons //////////
                // Button 1
                btn1 = new JButton("Gyro");
                btn1.setBackground(Color.RED);
                btn1.addActionListener(this);

                // Button 2
                btn2 = new JButton("Bowl");
                btn2.setBackground(Color.RED);
                btn2.addActionListener(this);

                // Button 3
                btn3 = new JButton("2 Falafels");
                btn3.setBackground(Color.BLUE);
                btn3.addActionListener(this);

                // Button 4
                btn4 = new JButton("Extra pita and humus");
                btn4.setBackground(Color.BLUE);
                btn4.addActionListener(this);

                // Button 5
                btn5 = new JButton("Extra Chicken");
                btn5.setBackground(Color.GREEN);
                btn5.addActionListener(this);

                // Button 6
                btn6 = new JButton("Extra Harissa");
                btn6.setBackground(Color.GREEN);
                btn6.addActionListener(this);

                // Button 7
                btn7 = new JButton("Extra Sp Meatball");
                btn7.setBackground(Color.GREEN);
                btn7.addActionListener(this);

                // Button 8
                btn8 = new JButton("Extra Tzatziki Sauce");
                btn8.setBackground(Color.GREEN);
                btn8.addActionListener(this);

                // Button 9
                btn9 = new JButton("Extra Balsamic Vinegar");
                btn9.setBackground(Color.GREEN);
                btn9.addActionListener(this);

                // Button 10
                btn10 = new JButton("Fountain drink");
                btn10.setBackground(Color.GREEN);
                btn10.addActionListener(this);

                // Button 11
                logoutBtn = new JButton("LOGOUT");
                logoutBtn.setBounds((int) (width * 0.17), (int) (height * 0.8), 300, 80);
                logoutBtn.setBackground(Color.LIGHT_GRAY);
                logoutBtn.addActionListener(this);

                // Button 12
                checkoutBtn = new JButton("CHECKOUT");
                checkoutBtn.setBackground(Color.LIGHT_GRAY);
                checkoutBtn.addActionListener(this);

                ////////// Welcome Area //////////
                // FIX ME: GET THIS TO WORK BASED OFF THE EMPLOYEE ID BASED ON THE LOGIN
                // TODO: Update after db finished

                String userName = "Paul Taele";
                JLabel weclomeTitle = new JLabel("Welcome " + userName);
                weclomeTitle.setBounds((int) (width * 0.29), (int) (height * 0.02), 300, 50);
                weclomeTitle.setFont(new Font("Arial", Font.PLAIN, 30));
                f.add(weclomeTitle);

                ////////// Menu-items Area //////////
                JPanel itemsPanel = new JPanel();
                itemsPanel.setBackground(Color.yellow);
                itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6),
                        (int) (height * 0.7));
                itemsPanel.setLayout(new GridLayout(5, 2, 10, 10));

                itemsPanel.add(btn1);
                itemsPanel.add(btn2);
                itemsPanel.add(btn3);
                itemsPanel.add(btn4);
                itemsPanel.add(btn5);
                itemsPanel.add(btn6);
                itemsPanel.add(btn7);
                itemsPanel.add(btn8);
                itemsPanel.add(btn9);
                itemsPanel.add(btn10);

                f.add(itemsPanel);

                ////////// Logout Area //////////
                JPanel logoutPanel = new JPanel();
                logoutPanel.setBackground(Color.pink);
                logoutPanel.setBounds((int) (width * 0.06), (int) ((height * 0.82)), (int) (width * 0.6),
                        (int) (height * 0.1));
                logoutPanel.setLayout(new BorderLayout());
                logoutBtn.setVerticalAlignment(JButton.CENTER);
                logoutBtn.setHorizontalAlignment(JButton.CENTER);
                logoutPanel.add(logoutBtn);
                f.add(logoutPanel);

                ////////// Receipt Area //////////
                JPanel receiptPanel = new JPanel();
                JLabel receiptTitle = new JLabel("RECEIPT");
                JLabel itemTitle = new JLabel("ITEM");
                JLabel quantityTitle = new JLabel("QUANTITY");
                JLabel totalSale = new JLabel("Total Sale: ");

                // Panel Setup
                receiptPanel.setBackground(Color.green);
                receiptPanel.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) height);
                receiptPanel.setLayout(null);

                // Position
                receiptTitle.setBounds((int) (width * 0.3 / 2 - 50), 0, 200, 100);
                itemTitle.setBounds((int) (width * 0.092), 70, 100, 100);
                quantityTitle.setBounds((int) (width * 0.19), 70, 100, 100);
                totalSale.setBounds((int) (width * 0.11), (int) (height * 0.6), 200, (int) (height * 0.08));
                sale.setBounds((int) (width * 0.2), (int) (height * 0.6), 200, (int) (height * 0.08));
                checkoutBtn.setBounds((int) (width * 0.09), (int) (height * 0.8), (int) (width * 0.15),
                        (int) (height * 0.1));

                // Font
                receiptTitle.setFont(new Font("Arial", Font.PLAIN, 28));
                itemTitle.setFont(new Font("Arial", Font.PLAIN, 20));
                quantityTitle.setFont(new Font("Arial", Font.PLAIN, 20));
                totalSale.setFont(new Font("Arial", Font.PLAIN, 20));
                sale.setFont(new Font("Arial", Font.PLAIN, 20));

                // Add to panel
                receiptPanel.add(receiptTitle);
                receiptPanel.add(itemTitle);
                receiptPanel.add(quantityTitle);
                receiptPanel.add(totalSale);
                receiptPanel.add(sale);
                receiptPanel.add(checkoutBtn);

                for (int i = 0; i < 10; ++i) {
                    receiptPanel.add(labelArr[i]);
                    receiptPanel.add(inputArr[i]);
                }

                ////////// Frame //////////
                f.add(receiptPanel);

                f.setSize(400, 400);
                f.setLayout(null);
                f.setVisible(true);
            }

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn1) {
                    // l1.setBounds(1000, 60 + height, 100, 50); --> hardcoded
                    l1.setBounds(item_width, 140, 100, 30);
                    inputArr[0].setBounds(quantity_width, 140, 100, 30);
                    // btn1.setEnabled(false); -- ONLY ALLOW TO BE CLICKED ONCE
                    gyroClick++;
                    input_1.setText(Integer.toString(gyroClick));
                    totalPrice += priceArr[0] * gyroClick;
                    // TESTING
                    // new inventoryPerOrder();

                } else if (e.getSource() == btn2) {
                    l2.setBounds(item_width, 180, 100, 30);
                    inputArr[1].setBounds(quantity_width, 180, 100, 30);
                    bowlClick++;
                    input_2.setText(Integer.toString(bowlClick));
                    totalPrice += priceArr[1] * bowlClick;

                } else if (e.getSource() == btn3) {
                    l3.setBounds(item_width, 220, 100, 30);
                    inputArr[2].setBounds(quantity_width, 220, 100, 30);
                    falafelClick++;
                    input_3.setText(Integer.toString(falafelClick));
                    totalPrice += priceArr[3] * falafelClick;

                } else if (e.getSource() == btn4) {
                    l4.setBounds(item_width, 260, 160, 30);
                    inputArr[3].setBounds(quantity_width, 260, 100, 30);
                    pitaAndHumusClick++;
                    input_4.setText(Integer.toString(pitaAndHumusClick));
                    totalPrice += priceArr[2] * pitaAndHumusClick;

                } else if (e.getSource() == btn5) {
                    l5.setBounds(item_width, 300, 100, 30);
                    inputArr[4].setBounds(quantity_width, 300, 100, 30);
                    extraChickenClick++;
                    input_5.setText(Integer.toString(extraChickenClick));
                    totalPrice += priceArr[6] * extraChickenClick;

                } else if (e.getSource() == btn6) {
                    l6.setBounds(item_width, 340, 100, 30);
                    inputArr[5].setBounds(quantity_width, 340, 100, 30);
                    extraHarissaClick++;
                    input_6.setText(Integer.toString(extraHarissaClick));
                    totalPrice += priceArr[4] * extraHarissaClick;

                } else if (e.getSource() == btn7) {
                    l7.setBounds(item_width, 380, 100, 30);
                    inputArr[6].setBounds(quantity_width, 380, 100, 30);
                    extraMeatballClick++;
                    input_7.setText(Integer.toString(extraMeatballClick));
                    totalPrice += priceArr[6] * extraMeatballClick;

                } else if (e.getSource() == btn8) {
                    l8.setBounds(item_width, 420, 180, 30);
                    inputArr[7].setBounds(quantity_width, 420, 100, 30);
                    extraTzatzikiSauceClick++;
                    input_8.setText(Integer.toString(extraTzatzikiSauceClick));
                    totalPrice += priceArr[4] * extraTzatzikiSauceClick;

                } else if (e.getSource() == btn9) {
                    l9.setBounds(item_width, 460, 160, 30);
                    inputArr[8].setBounds(quantity_width, 460, 100, 30);
                    extraVinegarClick++;
                    input_9.setText(Integer.toString(extraVinegarClick));
                    totalPrice += priceArr[4] * extraVinegarClick;

                } else if (e.getSource() == btn10) {
                    l10.setBounds(item_width, 500, 100, 30);
                    inputArr[9].setBounds(quantity_width, 500, 100, 30);
                    drinkClick++;
                    input_10.setText(Integer.toString(drinkClick));
                    totalPrice += priceArr[5] * drinkClick;

                } else if (e.getSource() == checkoutBtn) {
                    for (int i = 0; i < 10; ++i) {
                        if (inputArr[i].getText() != "") {
                            quantityArray[i] = Integer.parseInt(inputArr[i].getText());
                        }
                    }

                    insertOrder();
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    // FIX ME: ADD CONNECTION TO CHECKOUT
                    // FIX ME: PUSH THE VALUES OF AMOUNT AND CHECKOUT INFO TO CHECKOUT.

                } else if (e.getSource() == logoutBtn) {
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    new loginGUI();
                }
                sale.setText(String.valueOf(Math.round(totalPrice * 100.0) / 100.0));

                // height += 70; --> Hardcoded height, can fix later

            }

            public int getDateforId() {
                // transform java data into proper SQL variables
                DateFormat formatDate = new SimpleDateFormat("yyMMdd");
                Date date = Calendar.getInstance().getTime();
                String strDate = formatDate.format(date);

                int value = Integer.valueOf(strDate);

                return value * 1000;
            }

            public int getOrderId(Connection conn) {

                int lastRecord = 0;
                int date = getDateforId();

                try {
                    PreparedStatement lastOrder = conn.prepareStatement(
                            "SELECT orderid FROM ordering ORDER BY orderid DESC LIMIT 1");

                    ResultSet orderInfo = lastOrder.executeQuery();

                    while (orderInfo.next()) {
                        lastRecord = orderInfo.getInt("orderid");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                if (lastRecord / 1000 == date / 1000) {
                    return lastRecord + 1;
                }

                return date + 1;

            }

            public void insertOrder() {

                Connection conn = connectionSet();

                try {
                    int orderId = getOrderId(conn);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                    String stringVal = formatTime.format(calendar.getTime());
                    Time time = java.sql.Time.valueOf(stringVal); //

                    PreparedStatement statement = conn.prepareStatement(
                            "INSERT INTO ordering(orderid , timeoforder , amount , orderedgyro , orderedbowl , orderedpitahummus , orderedfalafel , orderedprotein , ordereddressing , ordereddrink , inventoryused ) VALUES (?,?,?, ?, ?, ?,?,?, ?, ?,?)");

                    int protein = extraChickenClick + extraMeatballClick;
                    int dressing = extraHarissaClick + extraTzatzikiSauceClick + extraVinegarClick;
                    double amount = getAmount(gyroClick, bowlClick, pitaAndHumusClick, falafelClick, protein, dressing,
                            drinkClick);

                    statement.setInt(1, orderId);
                    statement.setTime(2, time);
                    statement.setDouble(3, amount);
                    statement.setInt(4, gyroClick);
                    statement.setInt(5, bowlClick);
                    statement.setInt(6, pitaAndHumusClick);
                    statement.setInt(7, falafelClick);
                    statement.setInt(8, protein);
                    statement.setInt(9, dressing);
                    statement.setInt(10, drinkClick);

                    // FIX ME: MUST GET THIS FROM POPUPS. ALSO MUST KEEP inventory as global
                    // version.
                    Array inventoryused = conn.createArrayOf("INT", inventory);
                    statement.setArray(11, inventoryused);

                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

            }

            public double getAmount(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
                    int orderedprotein, int ordereddressing, int ordereddrink) {

                // FIX ME: MUST GET THE AMOUNTS FROM THE AMOUNTS TABLE
                double ordertotal = (8.09 * orderedgyro) +
                        (8.09 * orderedbowl) +
                        (3.49 * orderedpitahummus) +
                        (3.49 * orderedfalafel) +
                        (1.99 * orderedprotein) +
                        (0.39 * ordereddressing) +
                        (2.45 * ordereddrink);

                return (double) ordertotal;
            }

            public double[] priceArr() {
                Connection conn = connectionSet();
                double items[] = new double[10];
                double cost;
                int i = 0;
                try {
                    PreparedStatement itemsStat = conn.prepareStatement(
                            "SELECT cost FROM menucost");

                    ResultSet itemInfo = itemsStat.executeQuery();

                    while (itemInfo.next()) {
                        cost = itemInfo.getDouble("cost");
                        items[i] = cost;
                        i++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                return items;
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
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }

                return conn;
            }
        }

    }

    public static void main(String args[]) {
        new loginGUI();
    }

}