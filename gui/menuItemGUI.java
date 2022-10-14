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

public class menuItemGUI implements ActionListener {

    ////////// Declaration //////////

    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu viewMenu = new JMenu("View");
    JMenu editMenu = new JMenu("Edit");

    ArrayList<JMenuItem> itemList = new ArrayList<JMenuItem>();
    Integer inventory_id[];
    String inventory_names[];

    // View

    JLabel nameInfo = new JLabel("Item Name: ");
    JLabel itemName = new JLabel("");

    JLabel costInfo = new JLabel("Cost: ");
    JLabel itemCost = new JLabel("");

    JButton clearBtn = new JButton("Clear");

    // Back to Cashier
    JButton backToCashier = new JButton("Back To Cashier");

    // Store data
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Double> costList = new ArrayList<Double>();

    DateFormat dateFormat;

    // Add
    JButton addBtn = new JButton("Add");
    JTextField inputName = new JTextField("");
    JTextField inputCost = new JTextField("");

    ArrayList<JTextField> inputList = new ArrayList<JTextField>();
    JMenuItem addItem = new JMenuItem("Add");

    // Update
    JLabel ask_Name;
    JMenuItem updateItem = new JMenuItem("Update");
    JButton updateBtn = new JButton("Update");
    JButton searchBtn_Update = new JButton("Search");
    JButton searchBtn_Delete = new JButton("Search");

    // Delete
    JMenuItem deleteItem = new JMenuItem("Delete");
    JButton deleteBtn = new JButton("Delete");

    // Frame
    JFrame f = new JFrame();

    // Const Vars
    int i = 0;
    Connection conn;
    int employeeid;
    menuItemGUI(int id) {
        employeeid =id;
        try {
            conn = connectionSet();
            // int size = get_inventory_size(conn);
            nameList = get_menu_item(conn);
            costList = get_cost(conn);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Connection Failed.");
        }

        // Add the inventory items to menu bar
        for (int i = 0; i < nameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            viewMenu.add(newItem);
            itemList.add(newItem);
        }

        ////////// Background //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(1400, 1600);
        f.setLayout(null);
        f.setVisible(true);
        f.setJMenuBar(menuBar);
        f.setVisible(true);

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        ////////// Menu Setup //////////
        menuBar.add(viewMenu);
        menuBar.add(editMenu);

        editMenu.add(addItem);
        editMenu.add(updateItem);
        editMenu.add(deleteItem);
        addItem.addActionListener(this);
        updateItem.addActionListener(this);
        deleteItem.addActionListener(this);

        ////////// Data Output Area for each inventory item //////////

        nameInfo.setBounds(620, 210, 80, 20);
        itemName.setBounds(730, 210, 160, 20);

        costInfo.setBounds(620, 270, 80, 20);
        itemCost.setBounds(730, 270, 80, 20);

        // Hide before user clicks view
        info_display(false);

        // Clear the Data Output Area
        clearBtn.setBounds(780, 420, 115, 40);
        addBtn.setBounds(670, 420, 115, 40);

        clearBtn.addActionListener(this);
        clearBtn.setVisible(false);

        f.add(itemName);
        f.add(nameInfo);

        f.add(costInfo);
        f.add(itemCost);

        f.add(clearBtn);

        ////////// Logout //////////
        backToCashier.addActionListener(this);
        backToCashier.setBounds((int) (screenWidth * 0.06), (int) (screenHeight * 0.8), (int) (screenWidth * 0.1),
                (int) (screenHeight * 0.05));
        f.add(backToCashier);

        // ADD Items
        inputName.setBounds(730, 210, 160, 20);
        inputCost.setBounds(730, 270, 160, 20);

        inputName.setVisible(false);
        inputCost.setVisible(false);

        addBtn.addActionListener(this);
        addBtn.setVisible(false);
        f.add(addBtn);
        f.add(inputName);
        f.add(inputCost);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ///// Update /////
        ask_Name = new JLabel("Please enter the Item's name");
        ask_Name.setBounds(510, 210, 200, 20);
        f.add(ask_Name);
        f.add(searchBtn_Update);
        f.add(searchBtn_Delete);
        f.add(updateBtn);

        ask_Name.setVisible(false);
        searchBtn_Update.addActionListener(this);
        updateBtn.addActionListener(this);
        searchBtn_Update.setBounds(910, 210, 100, 20);
        updateBtn.setBounds(910, 300, 100, 20);
        searchBtn_Update.setVisible(false);
        updateBtn.setVisible(false);

        // Delete
        f.add(deleteBtn);
        searchBtn_Delete.addActionListener(this);
        searchBtn_Delete.setBounds(910, 210, 100, 20);
        searchBtn_Delete.setVisible(false);
        deleteBtn.addActionListener(this);
        deleteBtn.setBounds(910, 300, 100, 20);
        deleteBtn.setVisible(false);

    }

    public void action(int k) {
        itemName.setText(nameList.get(k));
        itemCost.setText(String.valueOf(costList.get(k)));
        i = k;

        info_display(true);
        clearBtn.setVisible(true);
    }

    public void info_display(Boolean b) {
        nameInfo.setVisible(b);
        costInfo.setVisible(b);
    }

    public void add_input_Display(Boolean b) {
        inputName.setVisible(b);
        inputCost.setVisible(b);
    }

    public void clearItemLabel() {
        itemName.setText("");
        itemCost.setText("");
    }

    public void clearInputText() {
        inputName.setText("");
        inputCost.setText("");
    }

    public void btnDisplay(boolean b) {
        addBtn.setVisible(b);
        updateBtn.setVisible(b);
        searchBtn_Update.setVisible(b);
        searchBtn_Delete.setVisible(b);
        deleteBtn.setVisible(b);
    }

    public boolean checkItemExit(String name) {
        for (int i = 0; i < nameList.size(); ++i) {
            if (name.equals(nameList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        for (int h = 0; h < itemList.size(); ++h) {
            if (e.getSource() == itemList.get(h)) {
                add_input_Display(false);
                action(h);
            }
            btnDisplay(false);
            ask_Name.setVisible(false);
        }

        if (e.getSource() == addItem) {
            btnDisplay(false);
            addBtn.setVisible(true);
            clearBtn.setVisible(true);

            ask_Name.setVisible(false);

            clearInputText();
            clearItemLabel();
            add_input_Display(true);
            info_display(true);

        } else if (e.getSource() == clearBtn) {
            clearInputText();
            clearItemLabel();
            info_display(false);
            add_input_Display(false);
            btnDisplay(false);
            ask_Name.setVisible(false);
            clearBtn.setVisible(false);

        } else if (e.getSource() == backToCashier) {
            // FIX ME: TODO: Implement
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == addBtn) {
            btnDisplay(false);
            addBtn.setVisible(true);

            String name = inputName.getText();
            Double cost = Double.parseDouble(inputCost.getText());

            if (checkItemExit(name)) {
                JOptionPane.showMessageDialog(null, "Item already exists!");
            } else {
                nameList.add(name);
                costList.add(cost);
                try {
                    add_item(conn, name, cost);
                    JOptionPane.showMessageDialog(null, "Item added to Database.");
                } catch (SQLException addException) {
                    JOptionPane.showMessageDialog(null, "Adding of item unsuccessful.");
                }
                JMenuItem newItem = new JMenuItem(name);
                newItem.addActionListener(this);
                viewMenu.add(newItem);
                itemList.add(newItem);
            }
            clearInputText();

        } else if (e.getSource() == updateItem) {
            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_Update.setVisible(true);

        } else if (e.getSource() == searchBtn_Update) {
            btnDisplay(false);
            updateBtn.setVisible(true);

            String name = inputName.getText();
            i = -1;
            for (int h = 0; h < nameList.size(); ++h) {
                if (nameList.get(h).equals(name)) {
                    i = h;
                    break;
                }
            }

            clearInputText();
            if (i == -1) {
                btnDisplay(false);
                ask_Name.setVisible(true);
                clearBtn.setVisible(true);
                clearInputText();
                clearItemLabel();
                info_display(false);
                add_input_Display(false);
                inputName.setVisible(true);
                searchBtn_Update.setVisible(true);
                JOptionPane.showMessageDialog(null, "Item doesn't exist!");
            } else {
                info_display(true);
                add_input_Display(true);
                ask_Name.setVisible(false);

                inputName.setText(nameList.get(i));
                inputCost.setText(String.valueOf(costList.get(i)));
            }

        } else if (e.getSource() == searchBtn_Delete) {
            btnDisplay(false);
            deleteBtn.setVisible(true);
            searchBtn_Delete.setVisible(false);
            String name = inputName.getText();

            i = -1;
            for (int h = 0; h < nameList.size(); ++h) {
                if (nameList.get(h).equals(name)) {
                    i = h;
                    break;
                }
            }
            clearInputText();

            if (i == -1) {
                btnDisplay(false);
                ask_Name.setVisible(true);
                clearBtn.setVisible(true);
                clearItemLabel();
                info_display(false);
                addBtn.setVisible(false);
                add_input_Display(false);
                inputName.setVisible(true);
                searchBtn_Delete.setVisible(true);
                JOptionPane.showMessageDialog(null, "Item doesn't exist!");

            } else {
                info_display(true);
                add_input_Display(false);
                ask_Name.setVisible(false);

                itemName.setText(nameList.get(i));
                itemCost.setText(String.valueOf(costList.get(i)));
            }

        } else if (e.getSource() == updateBtn) {
            // NOTE: MUST BE REMOVED: WE ARE NOT UPDATING PRIMARY KEY
            btnDisplay(false);
            nameList.set(i, inputName.getText());
            costList.set(i, Double.parseDouble(inputCost.getText()));

            itemList.get(i).setText(inputName.getText());

            try {
                update_item(conn, nameList.get(i), i);
                JOptionPane.showMessageDialog(null, "Updated item.");
            } catch (SQLException errorUpdate) {
                JOptionPane.showMessageDialog(null, "Update unsuccessful.");
            }

            clearInputText();

            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_Update.setVisible(true);

        } else if (e.getSource() == deleteItem) {
            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            addBtn.setVisible(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_Delete.setVisible(true);

        } else if (e.getSource() == deleteBtn) {
            btnDisplay(false);

            // FIX ME AFTER BACKEDN DONE
            try {
                delete_item(conn, nameList.get(i));
                JOptionPane.showMessageDialog(null, "Delete successful.");
            } catch (SQLException deleteException) {
                JOptionPane.showMessageDialog(null, "Delete unsuccessful.");
            }
            nameList.remove(i);
            costList.remove(i);
            itemList.remove(i);
            viewMenu.remove(i);
            add_input_Display(false);

            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            addBtn.setVisible(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_Delete.setVisible(true);
        }
    }

    public void add_item(Connection conn, String name, double cost) throws SQLException {
        PreparedStatement addStatement = conn.prepareStatement(
                "INSERT INTO menucost(menuitem, cost) VALUES(?,?)");

        addStatement.setString(1, name);
        addStatement.setDouble(2, cost);

        addStatement.executeUpdate();
    }

    public void update_item(Connection conn, String menu_item, int index) throws SQLException {
        PreparedStatement updateStat = conn.prepareStatement(
                "UPDATE menucost SET menuitem=(?), cost=(?) WHERE menuitem = (?)");

        updateStat.setString(1, nameList.get(index));
        updateStat.setDouble(2, costList.get(index));
        updateStat.setString(3, menu_item);
        updateStat.executeUpdate();
    }

    public void delete_item(Connection conn, String menu_name) throws SQLException {
        PreparedStatement delStatement = conn.prepareStatement("DELETE FROM menucost WHERE menuitem=(?)");
        delStatement.setString(1, menu_name);
        delStatement.executeUpdate();
    }

    // WILL GET MENU ITEM NAMES
    public ArrayList<String> get_menu_item(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT menuitem FROM menucost");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("menuitem"));

        }

        return temp;
    }

    // WILL GET COST OF MENU ITEM
    public ArrayList<Double> get_cost(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM menucost");

        ArrayList<Double> temp = new ArrayList<Double>();

        while (findInventory.next()) {
            temp.add(findInventory.getDouble("cost"));
        }
        return temp;
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
            
            JOptionPane.showMessageDialog(null, "Database connection failed");
        }

        return conn;
    }

    public static void main(String[] args) {
        new menuItemGUI(0);
    }

}