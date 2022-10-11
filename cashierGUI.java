
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;  
import java.time.LocalDateTime;  
import javax.swing.JOptionPane;


/* Array Index 
 * 0 - GYRO
 * 1 - BOWL
 * 2 - 2 falafels 
 * 3 - Extra pita and humus 
 * 4 - Extra Chicken
 * 5 - Extra Harissa
 * 6 - Extra Sp Meatball
 * 7 - Extra Tzatziki Sauce
 * 8 - Extra Balsamic Vinegar
 * 9 - Fountain drink
 */

public class cashierGUI implements ActionListener {
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
    int height = 0;

    /* Store quantity of each menu item */
    int quantityArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    

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

    public cashierGUI() {
        // Background
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray); // TODO: Fix background color

        // Button 1
        btn1 = new JButton("Gyro");
        btn1.setBounds(30, 30, 350, 150);
        btn1.setBackground(Color.RED); // TODO: Fix color
        btn1.addActionListener(this);

        // Button 2
        btn2 = new JButton("Bowl");
        btn2.setBounds(500, 30, 350, 150);
        btn2.setBackground(Color.RED);
        btn2.addActionListener(this);

        // Button 3
        btn3 = new JButton("2 Falafels");
        btn3.setBounds(30, 200, 350, 130);
        btn3.setBackground(Color.BLUE);
        btn3.addActionListener(this);

        // Button 4
        btn4 = new JButton("Extra pita and humus");
        btn4.setBounds(500, 200, 350, 130);
        btn4.setBackground(Color.BLUE);
        btn4.addActionListener(this);

        // Button 5
        btn5 = new JButton("Extra Chicken");
        btn5.setBounds(30, 350, 350, 110);
        btn5.setBackground(Color.GREEN);
        btn5.addActionListener(this);

        // Button 6
        btn6 = new JButton("Extra Harissa");
        btn6.setBounds(500, 350, 350, 110);
        btn6.setBackground(Color.GREEN);
        btn6.addActionListener(this);

        // Button 7
        btn7 = new JButton("Extra Sp Meatball");
        btn7.setBounds(30, 480, 350, 110);
        btn7.setBackground(Color.GREEN);
        btn7.addActionListener(this);

        // Button 8
        btn8 = new JButton("Extra Tzatziki Sauce");
        btn8.setBounds(500, 480, 350, 110);
        btn8.setBackground(Color.GREEN);
        btn8.addActionListener(this);

        // Button 9
        btn9 = new JButton("Extra Balsamic Vinegar");
        btn9.setBounds(30, 610, 820, 100);
        btn9.setBackground(Color.GREEN);
        btn9.addActionListener(this);

        // Button 10
        btn10 = new JButton("Fountain drink");
        btn10.setBounds(30, 730, 820, 100);
        btn10.setBackground(Color.GREEN);
        btn10.addActionListener(this);

        // Button 11
        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setBounds(10, 850, 300, 60);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(this);

        // Button 12
        checkoutBtn = new JButton("CHECKOUT");
        checkoutBtn.setBounds(1050, 750, 300, 80);
        checkoutBtn.setBackground(Color.LIGHT_GRAY);
        checkoutBtn.addActionListener(this);

        // frame
        f.add(btn1);
        f.add(btn2);
        f.add(btn3);
        f.add(btn4);
        f.add(btn5);
        f.add(btn6);
        f.add(btn7);
        f.add(btn8);
        f.add(btn9);
        f.add(btn10);
        f.add(btn10);
        f.add(logoutBtn);
        f.add(checkoutBtn);

        for (int i = 0; i < 10; ++i) {
            f.add(labelArr[i]);
            f.add(inputArr[i]);
        }

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            // l1.setBounds(1000, 60 + height, 100, 50); --> hardcoded
            l1.setBounds(1000, 60, 100, 50);
            inputArr[0].setBounds(1200, 60, 100, 50);
            gyroClick++;
            input_1.setText(Integer.toString(gyroClick));

        } else if (e.getSource() == btn2) {
            l2.setBounds(1000, 130, 100, 50);
            inputArr[1].setBounds(1200, 130, 100, 50);
            bowlClick++;
            input_2.setText(Integer.toString(bowlClick));

        } else if (e.getSource() == btn3) {
            l3.setBounds(1000, 200, 100, 50);
            inputArr[2].setBounds(1200, 200, 100, 50);
            falafelClick++;
            input_3.setText(Integer.toString(falafelClick));

        } else if (e.getSource() == btn4) {
            l4.setBounds(1000, 270, 100, 50);
            inputArr[3].setBounds(1200, 270, 100, 50);
            pitaAndHumusClick++;
            input_4.setText(Integer.toString(pitaAndHumusClick));

        } else if (e.getSource() == btn5) {
            l5.setBounds(1000, 340, 100, 50);
            inputArr[4].setBounds(1200, 340, 100, 50);
            extraChickenClick++;
            input_5.setText(Integer.toString(extraChickenClick));

        } else if (e.getSource() == btn6) {
            l6.setBounds(1000, 410, 100, 50);
            inputArr[5].setBounds(1200, 410, 100, 50);
            extraHarissaClick++;
            input_6.setText(Integer.toString(extraHarissaClick));

        } else if (e.getSource() == btn7) {
            l7.setBounds(1000, 480, 100, 50);
            inputArr[6].setBounds(1200, 480, 100, 50);
            extraMeatballClick++;
            input_7.setText(Integer.toString(extraMeatballClick));

        } else if (e.getSource() == btn8) {
            l8.setBounds(1000, 550, 100, 50);
            inputArr[7].setBounds(1200, 550, 100, 50);
            extraTzatzikiSauceClick++;
            input_8.setText(Integer.toString(extraTzatzikiSauceClick));

        } else if (e.getSource() == btn9) {
            l9.setBounds(1000, 620, 100, 50);
            inputArr[8].setBounds(1200, 620, 100, 50);
            extraVinegarClick++;
            input_9.setText(Integer.toString(extraVinegarClick));

        } else if (e.getSource() == btn10) {
            l10.setBounds(1000, 690, 100, 50);
            inputArr[9].setBounds(1200, 690, 100, 50);
            drinkClick++;
            input_10.setText(Integer.toString(drinkClick));
            

        } else if (e.getSource() == checkoutBtn) {
            JOptionPane.showMessageDialog(null, "Pincode is correct.");
            for (int i = 0; i < 10; ++i) {
                if (inputArr[i].getText() != "") {
                    quantityArray[i] = Integer.parseInt(inputArr[i].getText());
                }
            }
            
            System.out.println( "print");
            //insertOrder();
            //FIX ME: NEED TO GET CHECKOUT. ALSO EDIT 
            //checkout(); 

        }

        // height += 70; --> Hardcoded height, can fix later

    }

    public int getDate(){
        // transform java data into proper SQL variables
        String timeStamp = new SimpleDateFormat("yyMMdd").format(java.time.LocalDate.now());
        
        int value = Integer.valueOf( timeStamp);

        return value*1000;
        
    }

    public int getOrderId(Connection conn){

        int lastRecord = 0;

        try{
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

        
        if( lastRecord % 1000 == getDate() ){
            return lastRecord +1; 
        }
      
        return getDate() + 1; 
        
    }

    public void insertOrder(){

        Connection conn = connectionSet();

        int orderId = getOrderId(conn); 

        //FIX ME: GET TIME;


        PreparedStatement statement = conn.prepareStatement(
              "INSERT INTO ordering(orderid , timeoforder , amount , orderedgyro , orderedbowl , orderedpitahummus , orderedfalafel , orderedprotein , ordereddressing , ordereddrink , inventoryused ) VALUES (?,?,?, ?, ?, ?,?,?, ?, ?,?, ?)");

          
        //   for( int i=0; i< l)
          int protein = extraChickenClick + extraMeatballClick;
          int dressing = extraHarissaClick + extraTzatzikiSauceClick + extraVinegarClick;
          double amount = getAmount( gyroClick, bowlClick, pitaAndHumusClick, falafelClick, protein, dressing, drinkClick );

          statement.setInt(1, orderId);
          statement.setTime(2, time);
          statement.setDouble(3, amount);
          statement.setInt(5, gyroClick);
          statement.setInt(6, bowlClick);
          statement.setInt(7, pitaAndHumusClick);
          statement.setInt(8, falafelClick);
          statement.setInt(9, protein);
          statement.setInt(10, dressing);
          statement.setInt(11, drinkClick);

        // FIX ME: MUST GET THIS FROM POPUPS 
          Array inventoryused = conn.createArrayOf("INT", inventory);
          statement.setArray(12, inventoryused);
            
          statement.executeUpdate();
          
          
    }
    
    public double getAmount(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {
        
        double ordertotal = (8.09 * orderedgyro) +
        (8.09 * orderedbowl) +
        (3.49 * orderedpitahummus) +
        (3.49 * orderedfalafel) +
        (1.99 * orderedprotein) +
        (0.39 * ordereddressing) +
        (2.45 * ordereddrink);

        return (double) ordertotal;
    }

    public Connection connectionSet(){
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
        //JOptionPane.showMessageDialog(null, "Opened database successfully");
        return conn;
    }

    public static void main(String args[]) {
        new cashierGUI();
    }
}
