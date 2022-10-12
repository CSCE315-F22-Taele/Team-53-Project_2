package gui;

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

public class cashierGUI {
        private void makeFrameFullSize(JFrame aFrame) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                aFrame.setSize(screenSize.width, screenSize.height);
        }

        cashierGUI() {
                JFrame f = new JFrame("Cashier GUI");
                JPanel panel = new JPanel();
                // panel.setBounds(40, 80, 1000, 1000);

                // Background
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                f.setSize(screenSize.width, screenSize.height);
                f.setBackground(Color.gray); // TODO: Fix background color

                // Button 1
                JButton b1 = new JButton("Gyro");
                b1.setBounds(30, 30, 300, 300);
                b1.setBackground(Color.red); // TODO: Fix color

                final JTextField tf = new JTextField();
                tf.setBounds(50, 50, 150, 20);
                // TODO: BACKEND
                b1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                // TODO: Switch pages
                                tf.setText("+1 Gyro");
                        }
                });

                // Button 2

                f.add(b1);
                f.add(tf);

                f.add(panel);
                f.setSize(400, 400);
                f.setLayout(null);
                f.setVisible(true);
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
        
        public static void main(String args[]) {
                new cashierGUI();
        }
}