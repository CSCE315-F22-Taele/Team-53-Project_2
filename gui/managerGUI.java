import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

public class managerGUI implements ActionListener {

    ////////// Declaration //////////

    // Buttons
    JButton inventoryBtn = new JButton("INVENTORY");
    JButton menuBtn = new JButton("MENU");
    JButton salesBtn = new JButton("SALES REPORTS");
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

    managerGUI(int id) {
        employeeid = id;
        // Buttons
        inventoryBtn.setPreferredSize(new Dimension(300, 40));
        menuBtn.setPreferredSize(new Dimension(300, 40));
        salesBtn.setPreferredSize(new Dimension(300, 40));
        backToCashier.setPreferredSize(new Dimension(300, 40));

        inventoryBtn.addActionListener(this);
        menuBtn.addActionListener(this);
        salesBtn.addActionListener(this);
        backToCashier.addActionListener(this);

        // Panel Format
        panel.setBounds((int) (width * 0.025), (int) (height * 0.04), (int) (width * 0.95), (int) (height * 0.9));
        Color pink = new Color(244, 220, 245);
        panel.setBackground(pink);

        panel.add(inventoryBtn);
        panel.add(menuBtn);
        panel.add(salesBtn);
        panel.add(backToCashier);

        f.add(panel);

        // frame
        f.setSize(screenSize);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inventoryBtn) {
            new inventoryGUI(employeeid);
        } else if (e.getSource() == menuBtn) {
            new menuItemGUI(employeeid);
        } else if (e.getSource() == salesBtn) {
            new managerReportGUI();
        } else if (e.getSource() == backToCashier) {
            new cashierGUI(employeeid);
            f.dispose();
        }
    }

   

    public static void main(String[] args) {
        new managerGUI(0);
    }

}