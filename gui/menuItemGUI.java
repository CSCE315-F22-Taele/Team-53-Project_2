import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 * Implement the Menu Item Graphical User Interface. Only accessed by managers, this page will allow managers to add, update and deactive items sold on the menu.
 */
public class menuItemGUI implements ActionListener {

    ////////// Declaration //////////

    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu viewMenu = new JMenu("View");
    JMenu editMenu = new JMenu("Edit");
    JMenu deactivatedMenu = new JMenu("Deactivated");

    ArrayList<JMenuItem> itemList = new ArrayList<JMenuItem>();
    Integer inventory_id[];
    String inventory_names[];

    // View
    JLabel nameInfo = new JLabel("Item Name: ");
    JLabel itemName = new JLabel("");

    JLabel costInfo = new JLabel("Cost: ");
    JLabel itemCost = new JLabel("");

    JButton clearBtn = new JButton("Clear");

    // Back to Manager
    JButton backToManager = new JButton("Back To Manager");

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
    JButton searchBtn_Deactivate = new JButton("Search");

    // Deactivate
    JMenuItem deactivateItem = new JMenuItem("Deactivate");
    JButton deactivateBtn = new JButton("Deactivate");

    // Frame
    JFrame f = new JFrame("Menu Item GUI");

    // Const Vars
    int i = 0;
    Connection conn;
    int employeeid;

    // Deactivated
    ArrayList<String> deactivatedNameList = new ArrayList<String>();
    ArrayList<Double> deactivatedCostList = new ArrayList<Double>();
    ArrayList<JMenuItem> deactivatedItemList = new ArrayList<JMenuItem>();
    JButton activateBtn = new JButton("Activate");

/**
 * Constructor for the Menu Item GUI.
 * @param id  employee id, this page is only accessed by a manager.
 */
    menuItemGUI(int id) {
        employeeid = id;
        try {
            dbConnect c1= new dbConnect();
            conn = c1.connectionSet();
            nameList = get_menu_item(conn);
            costList = get_cost(conn);

            deactivatedNameList = get_deactivate_menu_item(conn);
            deactivatedCostList = get_deactivate_menu_cost(conn);

        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Connection Failed.");
        }

        // Add the inventory items to menu bar
        for (int i = 0; i < nameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            viewMenu.add(newItem);
            itemList.add(newItem);
        }

        // Add the deactivated items to the menu bar
        for (int i = 0; i < deactivatedNameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(deactivatedNameList.get(i));
            newItem.addActionListener(this);
            deactivatedMenu.add(newItem);
            deactivatedItemList.add(newItem);
        }

        ////////// Background //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setLayout(null);
        f.setVisible(true);
        f.setJMenuBar(menuBar);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // What?

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        ////////// Menu Setup //////////
        menuBar.add(viewMenu);
        menuBar.add(editMenu);
        menuBar.add(deactivatedMenu);

        editMenu.add(addItem);
        editMenu.add(updateItem);
        editMenu.add(deactivateItem);
        addItem.addActionListener(this);
        updateItem.addActionListener(this);
        deactivateItem.addActionListener(this);

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
        backToManager.addActionListener(this);
        backToManager.setBounds((int) (screenWidth * 0.06), (int) (screenHeight * 0.8), (int) (screenWidth * 0.1),
                (int) (screenHeight * 0.05));
        f.add(backToManager);

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
        f.add(searchBtn_Deactivate);
        f.add(updateBtn);

        ask_Name.setVisible(false);
        searchBtn_Update.addActionListener(this);
        updateBtn.addActionListener(this);
        searchBtn_Update.setBounds(910, 210, 100, 20);
        updateBtn.setBounds(910, 300, 100, 20);
        searchBtn_Update.setVisible(false);
        updateBtn.setVisible(false);

        ///// Deactivate /////
        searchBtn_Deactivate.addActionListener(this);
        searchBtn_Deactivate.setBounds(910, 210, 100, 20);
        searchBtn_Deactivate.setVisible(false);

        deactivateBtn.addActionListener(this);
        deactivateBtn.setBounds(910, 300, 100, 20);
        deactivateBtn.setVisible(false);

        activateBtn.addActionListener(this);
        activateBtn.setBounds(910, 210, 100, 20);
        f.add(deactivateBtn);
        f.add(activateBtn);

        activateBtn.setVisible(false);

    }

  /**
   * This function will illustrate an active menu item information.
   * @param k  value correlating to location in array of menu items
   */
    public void action(int k) {
        itemName.setText(nameList.get(k));
        itemCost.setText(String.valueOf(costList.get(k)));
        i = k;

        itemName.setVisible(true);
        itemCost.setVisible(true);
        info_display(true);
        clearBtn.setVisible(true);
        activateBtn.setVisible(false);
    }

    /**
     * This function will illustrate a deactive menu item information.
     * @param k  value correlating to location in array of deactive menu items
     */
    public void deaction(int k) {
        itemName.setText(deactivatedNameList.get(k));
        itemCost.setText(String.valueOf(deactivatedCostList.get(k)));
        i = k;

        itemName.setVisible(true);
        itemCost.setVisible(true);
        info_display(true);
        clearBtn.setVisible(true);
    }

    /**
     * This function determines whether a menu item information should be displayed or not.
     * @param b  boolean that determines whether a menu item information should be displayed
     */
    public void info_display(Boolean b) {
        nameInfo.setVisible(b);
        costInfo.setVisible(b);
    }

    /**
     * This function determines whether an input should be displayed or not.
     * @param b  boolean that determines whether the input should be displayed
     */
    public void add_input_Display(Boolean b) {
        inputName.setVisible(b);
        inputCost.setVisible(b);
    }

    /**
     * This function clears the item labels when called.
     */
    public void clearItemLabel() {
        itemName.setText("");
        itemCost.setText("");
    }

    /**
     * This function clears the input text box.
     */
    public void clearInputText() {
        inputName.setText("");
        inputCost.setText("");
    }

  /**
   * This function will determine whether a button will be displayed or removed from the screen accordingly.
   * @param b  boolean value that determines a button vieweablitiy
   */
    public void btnDisplay(boolean b) {
        addBtn.setVisible(b);
        updateBtn.setVisible(b);
        searchBtn_Update.setVisible(b);
        searchBtn_Deactivate.setVisible(b);
        deactivateBtn.setVisible(b);
        activateBtn.setVisible(b);
    }

    /**
     * This function will check if an item already exists in our menu item.
     * @param  name               item name we want to add to menu item
     * @return                    boolean determing whether an attempted menu item already exists or not
     */
    public boolean checkItemExit(String name) {
        for (int i = 0; i < nameList.size(); ++i) {
            if (name.equals(nameList.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function holds the action taken for each button clicked.
     * @param e  Type of ActionEvent taken. This is determined based on the button that was clicked.
     */
    public void actionPerformed(ActionEvent e) {
        for (int h = 0; h < itemList.size(); ++h) {
            if (e.getSource() == itemList.get(h)) {
                add_input_Display(false);
                action(h);
                btnDisplay(false);
            }
            ask_Name.setVisible(false);
        }

        for (int h = 0; h < deactivatedItemList.size(); ++h) {
            if (e.getSource() == deactivatedItemList.get(h)) {
                add_input_Display(false);
                deaction(h);
                activateBtn.setVisible(true);
                btnDisplay(false);
                activateBtn.setVisible(true);
            }
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

        } else if (e.getSource() == backToManager) {
            f.dispose();

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

        } else if (e.getSource() == searchBtn_Deactivate) {
            btnDisplay(false);
            deactivateBtn.setVisible(true);
            searchBtn_Deactivate.setVisible(false);
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
                searchBtn_Deactivate.setVisible(true);
                JOptionPane.showMessageDialog(null, "Item doesn't exist!");

            } else {
                info_display(true);
                add_input_Display(false);
                ask_Name.setVisible(false);

                itemName.setText(nameList.get(i));
                itemCost.setText(String.valueOf(costList.get(i)));
            }

        } else if (e.getSource() == updateBtn) {
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

        } else if (e.getSource() == deactivateItem) {
            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            addBtn.setVisible(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_Deactivate.setVisible(true);

        } else if (e.getSource() == deactivateBtn) {
            btnDisplay(false);

            deactivatedNameList.add(nameList.get(i));
            deactivatedCostList.add(costList.get(i));
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            deactivatedItemList.add(newItem);
            deactivatedMenu.add(newItem);

            try {
                delete_item(conn, nameList.get(i));
                JOptionPane.showMessageDialog(null, "Deactivate successful.");
            } catch (SQLException DeactivateException) {
                JOptionPane.showMessageDialog(null, "Deactivate unsuccessful.");
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
            searchBtn_Deactivate.setVisible(true);

        } else if (e.getSource() == activateBtn) {
            nameList.add(deactivatedNameList.get(i));
            costList.add(deactivatedCostList.get(i));

            JMenuItem newItem = new JMenuItem(deactivatedNameList.get(i));
            newItem.addActionListener(this);
            itemList.add(newItem);
            viewMenu.add(newItem);

            try {
                activate_item(conn, deactivatedNameList.get(i));
                JOptionPane.showMessageDialog(null, "Activate successful.");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Activate unsuccessful.");
            }

            deactivatedNameList.remove(i);
            deactivatedCostList.remove(i);
            deactivatedItemList.remove(i);
            deactivatedMenu.remove(i);

            // clear the screen
            activateBtn.setVisible(false);
            itemName.setVisible(false);
            itemCost.setVisible(false);
            info_display(false);
            clearBtn.setVisible(false);
        }
    }

    /**
     * This function will allow a manager to insert a new menu item.
     * @param  conn                       Connection to the database
     * @param  name                       menu item name
     * @param  cost                       the cost of the new menu item
     * @throws SQLException               error check if query is unsuccessful
     */
    public void add_item(Connection conn, String name, double cost) throws SQLException {
        PreparedStatement addStatement = conn.prepareStatement(
                "INSERT INTO menucost(menuitem, cost, is_selling) VALUES(?,?, true)");

        addStatement.setString(1, name);
        addStatement.setDouble(2, cost);

        addStatement.executeUpdate();
    }

    /**
     * This function will allow a manager to update a menu item's name or cost.
     * @param  conn                       Connection to the database
     * @param  menu_item                  menu item name
     * @param  index                      the location of the menu item information in list
     * @throws SQLException               error check if query is unsuccessful
     */
    public void update_item(Connection conn, String menu_item, int index) throws SQLException {
        PreparedStatement updateStat = conn.prepareStatement(
                "UPDATE menucost SET menuitem=(?), cost=(?) WHERE menuitem = (?)");

        updateStat.setString(1, nameList.get(index));
        updateStat.setDouble(2, costList.get(index));
        updateStat.setString(3, menu_item);
        updateStat.executeUpdate();
    }

  /**
   * This function will deactive a menu item, removing the ability of this menu item to be sold.
   * @param  conn                       Connection to the database
   * @param  menu_name                  menu item name
   * @throws SQLException               error check if query is unsuccessful
   */
    public void delete_item(Connection conn, String menu_name) throws SQLException {
        PreparedStatement delStatement = conn
                .prepareStatement("UPDATE menucost SET is_selling = false WHERE menuitem =(?)");
        delStatement.setString(1, menu_name);
        delStatement.executeUpdate();
    }

  /**
   * This function will reactivate an item that is currently deactivated.
   * @param  conn                       Connection to the database
   * @param  menu_name                  menu item name
   * @throws SQLException               error check if query is unsuccessful
   */
    public void activate_item(Connection conn, String menu_name) throws SQLException {
        PreparedStatement delStatement = conn
                .prepareStatement("UPDATE menucost SET is_selling = true WHERE menuitem =(?)");
        delStatement.setString(1, menu_name);
        delStatement.executeUpdate();
    }

    /**
     * This function will get all menu items that are currently being sold on the menu.
     * @param  conn                       Connection to the database
     * @return                            String arrayList of all active menu items by menu item id
     * @throws SQLException               error check if query is unsuccessful
     */
    public ArrayList<String> get_menu_item(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt
                .executeQuery("SELECT menuitem FROM menucost WHERE is_selling=true ORDER BY id ASC");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("menuitem"));

        }

        return temp;
    }

    /**
     * This function will get the cost of all menu items currently being sold on the menu.
     * @param  conn                       Connection to the database
     * @return                            array of cost of all active menu items by menu item id
     * @throws SQLException               error check if query is unsuccessful
     */
    public ArrayList<Double> get_cost(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM menucost WHERE is_selling=true ORDER BY id ASC");

        ArrayList<Double> temp = new ArrayList<Double>();

        while (findInventory.next()) {
            temp.add(findInventory.getDouble("cost"));
        }
        return temp;
    }

    /**
     * This function will get all menu items that are currently deactivated.
     * @param  conn                       Connection to the database
     * @return                            String arrayList of all deactive menu items by menu item id
     * @throws SQLException               error check if query is unsuccessful
     */
    public ArrayList<String> get_deactivate_menu_item(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt
                .executeQuery("SELECT menuitem FROM menucost WHERE is_selling=false ORDER BY id ASC");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("menuitem"));

        }

        return temp;
    }

    /**
     * This function will get the cost of all menu items currently being sold on the menu.
     * @param  conn                       Connection to the database
     * @return                            array of cost of all deactive menu items by menu item id
     * @throws SQLException               error check if query is unsuccessful
     */
    public ArrayList<Double> get_deactivate_menu_cost(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM menucost WHERE is_selling=false ORDER BY id ASC");

        ArrayList<Double> temp = new ArrayList<Double>();

        while (findInventory.next()) {
            temp.add(findInventory.getDouble("cost"));
        }
        return temp;
    }


    public static void main(String[] args) {
        new menuItemGUI(0);
    }

}
