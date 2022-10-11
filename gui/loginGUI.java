import javax.swing.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;

import java.sql.*;
import java.sql.DriverManager;

public class loginGUI implements ActionListener {
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
                //newGui.get_employee(passCode);
                
                
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

    public static void main(String args[]) {
        new loginGUI();

    }

}
