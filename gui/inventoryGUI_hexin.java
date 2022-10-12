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

public class inventoryGUI_hexin implements ActionListener {

    ////////// Declaration //////////

    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu viewMenu = new JMenu("View");
    JMenu editMenu = new JMenu("Edit");

    ArrayList<JMenuItem> itemList = new ArrayList<JMenuItem>();
    Integer inventory_id[];
    String inventory_names[];

    // View
    JLabel idInfo = new JLabel("Item ID: ");
    JLabel itemId = new JLabel("");

    JLabel nameInfo = new JLabel("Item Name: ");
    JLabel itemName = new JLabel("");

    JLabel quantityInfo = new JLabel("Quantity: ");
    JLabel itemQuantity = new JLabel("");

    JLabel costInfo = new JLabel("Cost: ");
    JLabel itemCost = new JLabel("");

    JLabel expirationInfo = new JLabel("Expiration Date: ");
    JLabel itemExpirationDate = new JLabel("");

    JLabel vendorInfo = new JLabel("Vendor: ");
    JLabel itemVendor = new JLabel("");

    JButton clearBtn = new JButton("Clear");

    // Logout
    JButton logOutBtn = new JButton("LOG OUT");

    // Store data
    ArrayList<Integer> idList = new ArrayList<Integer>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> quantityList = new ArrayList<Integer>();
    ArrayList<Date> expirationDateList = new ArrayList<Date>();
    ArrayList<Double> costList = new ArrayList<Double>();
    ArrayList<String> vendorList = new ArrayList<String>();

    DateFormat dateFormat;

    // Add
    JButton addBtn = new JButton("Add");
    JTextField inputId = new JTextField("");
    JTextField inputName = new JTextField("");
    JTextField inputQuantity = new JTextField("");
    JTextField inputCost = new JTextField("");
    JTextField inputDate = new JTextField("");
    JTextField inputVendor = new JTextField("");
    ArrayList<JTextField> inputList = new ArrayList<JTextField>();
    JMenuItem addItem = new JMenuItem("Add");

    // Update
    JLabel ask_Id;
    JMenuItem updateItem = new JMenuItem("Update");
    JButton updateBtn = new JButton("Update");
    JButton searchBtn = new JButton("Search");

    // Delete
    JMenuItem deleteItem = new JMenuItem("Delete");
    JButton deleteBtn = new JButton("Delete");

    // Frame
    JFrame f = new JFrame();

    // Const Vars
    int i = 0;
    Connection conn;

    inventoryGUI_hexin() {

        try {
            conn = connectionSet();
            // int size = get_inventory_size(conn);
            System.out.println("enters");
            nameList = get_inventory_name(conn);
            idList = get_id(conn);
            System.out.println("enters");
            quantityList = get_quantity(conn);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Add the inventory items to menu bar
        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(nameList.get(i));
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            viewMenu.add(newItem);
            itemList.add(newItem);
        }

        ////////// Background //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setJMenuBar(menuBar);
        f.setVisible(true);

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

        idInfo.setBounds(620, 180, 80, 20);
        itemId.setBounds(730, 180, 160, 20);

        nameInfo.setBounds(620, 210, 80, 20);
        itemName.setBounds(730, 210, 160, 20);

        quantityInfo.setBounds(620, 240, 80, 20);
        itemQuantity.setBounds(730, 240, 80, 20);

        costInfo.setBounds(620, 270, 80, 20);
        itemCost.setBounds(730, 270, 80, 20);

        expirationInfo.setBounds(620, 300, 160, 20);
        itemExpirationDate.setBounds(730, 300, 160, 20);

        vendorInfo.setBounds(620, 330, 80, 20);
        itemVendor.setBounds(730, 330, 160, 20);

        // Hide before user clicks view
        info_display(false);

        // Clear the Data Output Area
        clearBtn.setBounds(780, 420, 115, 40);
        addBtn.setBounds(670, 420, 115, 40);

        clearBtn.addActionListener(this);
        clearBtn.setVisible(false);

        f.add(idInfo);
        f.add(itemId);
        f.add(itemName);
        f.add(nameInfo);
        f.add(quantityInfo);
        f.add(itemQuantity);
        f.add(costInfo);
        f.add(itemCost);
        f.add(itemExpirationDate);
        f.add(expirationInfo);
        f.add(vendorInfo);
        f.add(itemVendor);
        f.add(clearBtn);

        ////////// Logout //////////
        logOutBtn.addActionListener(this);
        logOutBtn.setBounds(300, 780, 200, 100);
        f.add(logOutBtn);

        // ADD Items
        inputId.setBounds(730, 180, 160, 20);
        inputName.setBounds(730, 210, 160, 20);
        inputQuantity.setBounds(730, 240, 160, 20);
        inputCost.setBounds(730, 270, 160, 20);
        inputDate.setBounds(730, 300, 160, 20);
        inputVendor.setBounds(730, 330, 160, 20);

        inputId.setVisible(false);
        inputName.setVisible(false);
        inputQuantity.setVisible(false);
        inputCost.setVisible(false);
        inputDate.setVisible(false);
        inputVendor.setVisible(false);

        addBtn.addActionListener(this);
        addBtn.setVisible(false);
        f.add(addBtn);
        f.add(inputId);
        f.add(inputName);
        f.add(inputQuantity);
        f.add(inputCost);
        f.add(inputDate);
        f.add(inputVendor);

        dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        ///// Update /////
        ask_Id = new JLabel("Please enter the Item's ID");
        ask_Id.setBounds(530, 180, 160, 20);
        f.add(ask_Id);
        f.add(searchBtn);
        f.add(updateBtn);

        ask_Id.setVisible(false);
        searchBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        searchBtn.setBounds(910, 180, 100, 20);
        updateBtn.setBounds(910, 300, 100, 20);
        searchBtn.setVisible(false);
        updateBtn.setVisible(false);

        // Delete
        f.add(deleteBtn);
        deleteBtn.addActionListener(this);
        deleteBtn.setBounds(910, 300, 100, 20);
        deleteBtn.setVisible(false);

    }

    public void action(int k) {
        itemId.setText(String.valueOf(idList.get(k)));
        itemName.setText(nameList.get(k));
        itemQuantity.setText(Integer.toString(quantityList.get(k)));
        itemCost.setText(String.valueOf(costList.get(k)));
        itemVendor.setText(vendorList.get(k));
        itemExpirationDate.setText(dateFormat.format(expirationDateList.get(k)));
        i = k;

        info_display(true);
        clearBtn.setVisible(true);
    }

    public void info_display(Boolean b) {
        idInfo.setVisible(b);
        nameInfo.setVisible(b);
        quantityInfo.setVisible(b);
        costInfo.setVisible(b);
        expirationInfo.setVisible(b);
        vendorInfo.setVisible(b);
    }

    public void add_input_Display(Boolean b) {
        inputId.setVisible(b);
        inputName.setVisible(b);
        inputQuantity.setVisible(b);
        inputCost.setVisible(b);
        inputDate.setVisible(b);
        inputVendor.setVisible(b);
    }

    public void clearItemLabel() {
        itemId.setText("");
        itemName.setText("");
        itemQuantity.setText("");
        itemCost.setText("");
        itemExpirationDate.setText("");
        itemVendor.setText("");
    }

    public void clearInputText() {
        inputId.setText("");
        inputName.setText("");
        inputQuantity.setText("");
        inputCost.setText("");
        inputDate.setText("");
        inputVendor.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        for (int h = 0; h < itemList.size(); ++h) {
            if (e.getSource() == itemList.get(h)) {
                add_input_Display(false);
                action(h);
                System.out.println(itemList.get(h).getText());
            }
            searchBtn.setVisible(false);
            ask_Id.setVisible(false);
            addBtn.setVisible(false);
        }
        if (e.getSource() == addItem) {
            addBtn.setVisible(true);
            clearBtn.setVisible(true);

            ask_Id.setVisible(false);
            searchBtn.setVisible(false);
            updateBtn.setVisible(false);

            clearInputText();
            clearItemLabel();
            add_input_Display(true);
            info_display(true);

        } else if (e.getSource() == clearBtn) {
            clearInputText();
            clearItemLabel();
            info_display(false);
            add_input_Display(false);
            addBtn.setVisible(false);
            clearBtn.setVisible(false);
            searchBtn.setVisible(false);
            updateBtn.setVisible(false);
            ask_Id.setVisible(false);

        } else if (e.getSource() == logOutBtn) {
            // FIX ME: TODO: Implement

        } else if (e.getSource() == addBtn) {
            int id = Integer.parseInt(inputId.getText());
            String name = inputName.getText();
            int quantity = Integer.parseInt(inputQuantity.getText());
            double cost = Double.parseDouble(inputCost.getText());
            Date expirationDate = null;
            String vendor = inputVendor.getText();
            try {
                expirationDate = new SimpleDateFormat("yyyy-mm-dd").parse(inputDate.getText());
            } catch (ParseException error) {
                // TODO Auto-generated catch block
                error.printStackTrace();
            }

            // idList.add(id);
            idList.add(id);
            nameList.add(name);
            quantityList.add(quantity);
            costList.add(cost);
            expirationDateList.add(expirationDate);
            vendorList.add(vendor);

            try {
                add_item(conn, id, name, quantity, cost, expirationDate, vendor);
                JOptionPane.showMessageDialog(null, "Item added to Database.");
            } catch (SQLException addException) {
                JOptionPane.showMessageDialog(null, "Adding of item unsuccessful.");
            }
            JMenuItem newItem = new JMenuItem(name);
            newItem.addActionListener(this);
            viewMenu.add(newItem);
            itemList.add(newItem);
            clearInputText();

        } else if (e.getSource() == updateItem) {
            ask_Id.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            idInfo.setVisible(false);
            addBtn.setVisible(false);
            add_input_Display(true);
            searchBtn.setVisible(true);
            updateBtn.setVisible(true);

        } else if (e.getSource() == searchBtn) {
            int id = Integer.parseInt(inputId.getText());
            i = -1;
            for (int h = 0; h < idList.size(); ++h) {
                if (idList.get(h) == id) {
                    i = h;
                    break;
                }
            }
            addBtn.setVisible(false);
            clearInputText();
            info_display(true);
            ask_Id.setVisible(false);
            inputId.setText(String.valueOf(idList.get(i)));
            inputName.setText(nameList.get(i));
            inputQuantity.setText(String.valueOf(quantityList.get(i)));
            inputCost.setText(String.valueOf(costList.get(i)));

            inputDate.setText(dateFormat.format(expirationDateList.get(i)));
            inputVendor.setText(vendorList.get(i));

        } else if (e.getSource() == updateBtn) {
            idList.set(i, Integer.parseInt(inputId.getText()));
            nameList.set(i, inputName.getText());
            quantityList.set(i, Integer.parseInt(inputQuantity.getText()));
            costList.set(i, Double.parseDouble(inputCost.getText()));
            Date expirationDate = null;
            try {
                expirationDate = new SimpleDateFormat("yyyy-mm-dd").parse(inputDate.getText());
            } catch (ParseException error) {
                // TODO Auto-generated catch block
                error.printStackTrace();
            }

            expirationDateList.set(i, expirationDate);
            vendorList.set(i, inputVendor.getText());
            itemList.get(i).setText(inputName.getText());
            // try {
            // update_item(conn, i);
            // JOptionPane.showMessageDialog(null, "Updated item.");
            // } catch (SQLException errorUpdate) {
            // PRINT OUT. UPDATE UNSUCCESSFUL.
            // JOptionPane.showMessageDialog(null, "Update unsuccessful.");
            // }
            addBtn.setVisible(false);
            clearInputText();
            updateBtn.setVisible(false);

        } else if (e.getSource() == deleteItem) {
            addBtn.setVisible(false);
            searchBtn.setVisible(true);
            ask_Id.setVisible(true);
            clearInputText();
            clearItemLabel();
            add_input_Display(true);
            info_display(true);
            idInfo.setVisible(false);
            deleteBtn.setVisible(true);

        } else if (e.getSource() == deleteBtn) {
            System.out.println(idList.get(i));
            try {
                delete_item(conn, idList.get(i));
                JOptionPane.showMessageDialog(null, "Delete successful.");
            } catch (SQLException deleteException) {
                JOptionPane.showMessageDialog(null, "Delete unsuccessful.");
            }
            idList.remove(i);
            nameList.remove(i);
            quantityList.remove(i);
            costList.remove(i);
            expirationDateList.remove(i);
            vendorList.remove(i);
            itemList.remove(i);
            viewMenu.remove(i);
            add_input_Display(false);
            deleteBtn.setVisible(false);
            // FIX ME: MAYBE WE CAN ADD A DELETED MESSAGE.
        }
    }

    public void add_item(Connection conn, int id, String name, int quantity, double cost, Date expirationDate,
            String vendor) throws SQLException {
        PreparedStatement addStatement = conn.prepareStatement(
                "INSERT INTO inventory(itemid, itemname, amount, cost, expirationdate,vendor) VALUES(?,?,?,?,? ?)");
        addStatement.setInt(1, id);
        addStatement.setString(2, name);
        addStatement.setInt(3, quantity);
        addStatement.setDouble(4, cost);
        addStatement.setDate(5, (java.sql.Date) expirationDate);
        addStatement.setString(6, vendor);

        addStatement.executeUpdate();
    }

    public void update_item(Connection conn, int index) throws SQLException{
        PreparedStatement updateStat = conn.prepareStatement("DELETE FROM inventory SET itemname=(?), amount=(?), cost=(?), expirationdate=(?),vendor=(?) WHERE itemid = (?)");
       
        updateStat.setString(1, nameList.get(index));
        updateStat.setInt(2, quantityList.get(index));
        updateStat.setDouble(3, costList.get(index));
        //FIX ME: RIGHT DATE FORMAT
        updateStat.setDate(4, (java.sql.Date) expirationDateList.get(index));
        updateStat.setString(5, vendorList.get(index));
        updateStat.setInt(6, idList.get(index));
        updateStat.executeUpdate();
    }   


    public void delete_item(Connection conn, int id) throws SQLException {
        PreparedStatement delStatement = conn.prepareStatement("DELETE FROM inventory WHERE itemid=(?)");
        delStatement.setInt(1, id);
        delStatement.executeUpdate();
    }

    public ArrayList<Integer> get_id(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemid FROM inventory");

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            // inventory_names.push_back(findInventory.getString("itemname"));
            temp.add(findInventory.getInt("itemid"));

        }

        return temp;
    }

    public ArrayList<Integer> get_quantity(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT amount FROM inventory");
        // int count = 0; // Increments inventory array

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            // inventory_names.push_back(findInventory.getString("itemname"));
            temp.add(findInventory.getInt("amount"));

        }

        return temp;
    }

    public ArrayList<String> get_inventory_name(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            // inventory_names.push_back(findInventory.getString("itemname"));
            temp.add(findInventory.getString("itemname"));

        }

        return temp;
    }

    public ArrayList<Float> get_cost(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM inventory");

        ArrayList<Float> temp = new ArrayList<Float>();

        while (findInventory.next()) {
            temp.add(findInventory.getFloat("cost"));
        }
        return temp;
    }

    public ArrayList<Date> get_expiration_date(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT expirationdate FROM inventory");

        ArrayList<Date> temp = new ArrayList<Date>();

        while (findInventory.next()) {
            temp.add(findInventory.getDate("expirationdate"));
        }
        return temp;
    }

    public ArrayList<String> get_vendor(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT vendor FROM inventory");
        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("vendor"));
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
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return conn;
    }

    public static void main(String[] args) {
        new inventoryGUI_hexin();
    }

}