import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 * Implements the Inventory graphical user interface. This helps us see and edit inventory.
 */
public class inventoryGUI implements ActionListener {
    ////////// Declaration //////////
    
    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu viewMenu = new JMenu("Items");
    JMenu editMenu = new JMenu("Edit");
    JMenu deactivatedMenu = new JMenu("Deactivated");
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
   
    // Back to Manager
    JButton backToManager = new JButton("Back To Manager");
   
    // Store data
    ArrayList<Integer> idList = new ArrayList<Integer>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> quantityList = new ArrayList<Integer>();
    ArrayList<Date> expirationDateList = new ArrayList<Date>();
    ArrayList<Double> costList = new ArrayList<Double>();
    ArrayList<String> vendorList = new ArrayList<String>();
    ArrayList<String> restock_name_list = new ArrayList<String>();
    ArrayList<JButton> restock_name_btn = new ArrayList<JButton>();
    ArrayList<Integer> restock_quantity_list = new ArrayList<Integer>();
    ArrayList<JLabel> restock_quantity_label = new ArrayList<JLabel>();
    DateFormat dateFormat;
    
    // Add
    JButton addBtn = new JButton("Add");
    JTextField inputName = new JTextField("");
    JTextField inputQuantity = new JTextField("");
    JTextField inputCost = new JTextField("");
    JTextField inputDate = new JTextField("");
    JTextField inputVendor = new JTextField("");
    ArrayList<JTextField> inputList = new ArrayList<JTextField>();
    JMenuItem addItem = new JMenuItem("Add");
    
    // Update
    JLabel ask_Name;
    JMenuItem updateItem = new JMenuItem("Update");
    JButton updateBtn = new JButton("Update");
    JButton searchBtn_Update = new JButton("Search");
    JButton searchBtn_deactivate = new JButton("Search");
    
    // deactivate
    JMenuItem deactivateItem = new JMenuItem("Deactivate");
    JButton deactivateBtn = new JButton("Deactivate");
    
    // Deactivated-menu
    ArrayList<Integer> deactivated_IdList = new ArrayList<Integer>();
    ArrayList<String> deactivated_NameList = new ArrayList<String>();
    ArrayList<Double> deactivated_CostList = new ArrayList<Double>();
    ArrayList<Integer> deactivated_QuantityList = new ArrayList<Integer>();
    ArrayList<Date> deactivated_ExpirationList = new ArrayList<Date>();
    ArrayList<String> deactivated_VendorList = new ArrayList<String>();
    ArrayList<JMenuItem> deactivated_ItemList = new ArrayList<JMenuItem>();
    JButton activateBtn = new JButton("Activate");
    
    // Frame
    JFrame f = new JFrame();
    
    //////////// Restock Panel //////////
    JPanel itemsPanel = new JPanel();
    JPanel restockPanel_Top = new JPanel();
    JPanel restockPanel_Left = new JPanel();
    JPanel restockPanel_Right = new JPanel();
    JPanel restockPanel_Down = new JPanel();
    
    // Const Vars
    int i = 0;
    int restockIndex = -1;
    Connection conn;
    int employeeid;
    boolean isRestock = false;

    /**
     * Constructor to setup a Inventory Graphical User Interface.
     * @param id  Employee id. Has to be a manager to use this page.
     */
    inventoryGUI(int id) {
        employeeid = id;
        try {
            dbConnect c1= new dbConnect();
            conn = c1.connectionSet();

            nameList = get_inventory_name(conn);
            idList = get_id(conn);
            quantityList = get_quantity(conn);
            costList = get_cost(conn);
            expirationDateList = get_expiration_date(conn);
            vendorList = get_vendor(conn);



            deactivated_IdList = get_id_deactive(conn);
            deactivated_NameList =  get_inventory_name_deactive(conn);
            deactivated_CostList = get_cost_deactive(conn);
            deactivated_QuantityList = get_quantity_deactive(conn);
            deactivated_ExpirationList = get_expiration_date_deactive(conn);
            deactivated_VendorList = get_vendor_deactive(conn);



        } catch (SQLException e) {
        
            JOptionPane.showMessageDialog(null, "Failed database connection.");
        }
        
        // Add the inventory items to menu bar
        for (int i = 0; i < nameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            viewMenu.add(newItem);
            itemList.add(newItem);
        }
        
        // Add the deactivated items to the menu bar
        for (int i = 0; i < deactivated_NameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(deactivated_NameList.get(i));
            newItem.addActionListener(this);
            deactivatedMenu.add(newItem);
            deactivated_ItemList.add(newItem);
        }
        
        ////////// Background //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
       
        f.setLayout(null);
        f.setVisible(true);
        f.setJMenuBar(menuBar);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        backToManager.addActionListener(this);
        backToManager.setBounds((int) (screenWidth * 0.06), (int) (screenHeight * 0.8), (int) (screenWidth * 0.1),
                (int) (screenHeight * 0.05));
        f.add(backToManager);
        
        // ADD Items
        inputName.setBounds(730, 210, 160, 20);
        inputQuantity.setBounds(730, 240, 160, 20);
        inputCost.setBounds(730, 270, 160, 20);
        inputDate.setBounds(730, 300, 160, 20);
        inputVendor.setBounds(730, 330, 160, 20);
        inputName.setVisible(false);
        inputQuantity.setVisible(false);
        inputCost.setVisible(false);
        inputDate.setVisible(false);
        inputVendor.setVisible(false);
        addBtn.addActionListener(this);
        addBtn.setVisible(false);
        f.add(addBtn);
        f.add(inputName);
        f.add(inputQuantity);
        f.add(inputCost);
        f.add(inputDate);
        f.add(inputVendor);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        ///// Update /////
        ask_Name = new JLabel("Please enter the Item's name");
        ask_Name.setBounds(510, 210, 200, 20);
        f.add(ask_Name);
        f.add(searchBtn_Update);
        f.add(searchBtn_deactivate);
        f.add(updateBtn);
        ask_Name.setVisible(false);
        searchBtn_Update.addActionListener(this);
        updateBtn.addActionListener(this);
        searchBtn_Update.setBounds(910, 210, 100, 20);
        updateBtn.setBounds(910, 300, 100, 20);
        searchBtn_Update.setVisible(false);
        updateBtn.setVisible(false);
        
        // deactivate
        f.add(deactivateBtn);
        searchBtn_deactivate.addActionListener(this);
        searchBtn_deactivate.setBounds(910, 210, 100, 20);
        searchBtn_deactivate.setVisible(false);
        deactivateBtn.addActionListener(this);
        deactivateBtn.setBounds(910, 300, 100, 20);
        deactivateBtn.setVisible(false);
        activateBtn.addActionListener(this);
        activateBtn.setBounds(910, 210, 100, 20);
        f.add(deactivateBtn);
        f.add(activateBtn);
        activateBtn.setVisible(false);
        
        ////////// Receipt Area //////////
       
        Color blueCute = new Color(194, 194, 252);
        
        // Title
        restockPanel_Top.setBackground(blueCute);
        restockPanel_Top.setBounds((int) (screenWidth * 0.7), 0, (int) (screenWidth * 0.3), (int) (screenHeight * 0.1));
        restockPanel_Top.setLayout(null);
        restockPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("REORDER NOW");
       
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        restockPanel_Top.add(title);
        restockPanel_Left.setBackground(blueCute);
        restockPanel_Left.setBounds((int) (screenWidth * 0.7), (int) (screenHeight * 0.1), (int) (screenWidth * 0.15),
                (int) (screenHeight * 0.7));
        restockPanel_Left.setLayout(new GridLayout(25, 1, 10, 10));
       
        JLabel itemNameTitle = new JLabel("Item");
        itemNameTitle.setVerticalAlignment(JLabel.TOP);
        itemNameTitle.setHorizontalAlignment(JLabel.CENTER);
        restockPanel_Left.add(itemNameTitle);
        restockPanel_Right.setBackground(blueCute);
        restockPanel_Right.setBounds((int) (screenWidth * 0.85), (int) (screenHeight * 0.1), (int) (screenWidth * 0.15),
                (int) (screenHeight * 0.7));
        restockPanel_Right.setLayout(new GridLayout(25, 1, 10, 10));
       
        JLabel quantityTitle = new JLabel("Quantity");
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        restockPanel_Right.add(quantityTitle);
        restockPanel_Down.setBackground(blueCute);
        restockPanel_Down.setBounds((int) (screenWidth * 0.7), (int) (screenHeight * 0.8), (int) (screenWidth * 0.3),
                (int) (screenHeight * 0.3));
        
        f.add(restockPanel_Top);
        f.add(restockPanel_Left);
        f.add(restockPanel_Right);
        f.add(restockPanel_Down);

        // Add the restock items to the arraylist
        try {
            restock_name_list = get_inventory_name_restock(conn);
            restock_quantity_list = get_inventory_amount_restock(conn);
        } catch (SQLException e) {

        }

        // generate the items as buttons and add action
        for (int i = 0; i < restock_name_list.size(); i++) {
            JButton newBtn = new JButton(restock_name_list.get(i));
            newBtn.setForeground(Color.red);
            newBtn.addActionListener(this);
            restockPanel_Left.add(newBtn);
            restock_name_btn.add(newBtn);
            JLabel newLabel = new JLabel(String.valueOf(restock_quantity_list.get(i)));
            newLabel.setForeground(Color.RED);
            restockPanel_Right.add(newLabel);
            restock_quantity_label.add(newLabel);
            newLabel.setVerticalAlignment(JLabel.TOP);
            newLabel.setHorizontalAlignment(JLabel.CENTER);
        }
    }

    /**
     * Add value into active menu bar.
     * @param k  Index of the value to add into into the menu bar from the activated lists of values.
     */
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
        activateBtn.setVisible(false);
    }

    /**
     * Add value into deactive menu bar.
     * @param k  Index of the value to add into into the menu bar from the deactivated lists of values.
     */
    public void deaction(int k) {
        itemId.setText(String.valueOf(deactivated_IdList.get(k)));
        itemName.setText(deactivated_NameList.get(k));
        itemQuantity.setText(Integer.toString(deactivated_QuantityList.get(k)));
        itemCost.setText(String.valueOf(deactivated_CostList.get(k)));
        itemVendor.setText((deactivated_VendorList.get(k)));
        itemExpirationDate.setText(dateFormat.format(deactivated_ExpirationList.get(k)));
        i = k;
        info_display(true);
        clearBtn.setVisible(true);
    }

    /**
     * Function to make Labels appear or disappear.
     * @param b True/False value to make Jlabels appear or disappear.
     */
    public void info_display(Boolean b) {
        idInfo.setVisible(b);
        nameInfo.setVisible(b);
        quantityInfo.setVisible(b);
        costInfo.setVisible(b);
        expirationInfo.setVisible(b);
        vendorInfo.setVisible(b);
    }

    /**
     * Make text fields appear or disappear.
     * @param b  True/False value to make text fields appear or disappear.
     */
    public void add_input_Display(Boolean b) {
        inputName.setVisible(b);
        inputQuantity.setVisible(b);
        inputCost.setVisible(b);
        inputDate.setVisible(b);
        inputVendor.setVisible(b);
    }

    /**
     * Function to clear all labels when clear button pressed.
     */
    public void clearItemLabel() {
        itemId.setText("");
        itemName.setText("");
        itemQuantity.setText("");
        itemCost.setText("");
        itemExpirationDate.setText("");
        itemVendor.setText("");
    }

    /**
     * Function to clear all Input Text when clear button pressed.
     */
    public void clearInputText() {
        inputName.setText("");
        inputQuantity.setText("");
        inputCost.setText("");
        inputDate.setText("");
        inputVendor.setText("");
    }

    /**
     * Whether or not to display the button
     * @param b Boolean set to true if need to display button and false if not display.
     */
    public void btnDisplay(boolean b) {
        addBtn.setVisible(b);
        updateBtn.setVisible(b);
        searchBtn_Update.setVisible(b);
        searchBtn_deactivate.setVisible(b);
        deactivateBtn.setVisible(b);
        activateBtn.setVisible(b);
    }

    /**
     * Check if item exits
     * @param  name           Name to check if it exists.
     * @return    Index that name is at or -1 if does not exist.
     */
    public int checkItemExit(String name) {
        for (int i = 0; i < nameList.size(); ++i) {
            if (name.equals(nameList.get(i))) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Set update for item that needs to be restocked.
     * @param name  Name of the item that needs to be restocked.
     */
    public void getUpdate(String name) {
        btnDisplay(false);
        updateBtn.setVisible(true);
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
            itemId.setText(String.valueOf(idList.get(i)));
            inputName.setText(nameList.get(i));
            inputQuantity.setText(String.valueOf(quantityList.get(i)));
            inputCost.setText(String.valueOf(costList.get(i)));
            inputDate.setText(dateFormat.format(expirationDateList.get(i)));
            inputVendor.setText(vendorList.get(i));
        }
    }

    /**
     * Action that needs to occur based on the button/item that was clicked.
     * @param e Button clicked.
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
        for (int l = 0; l < restock_name_btn.size(); ++l) {
            if (e.getSource() == restock_name_btn.get(l)) {
                clearInputText();
                clearItemLabel();
                isRestock = true;
                restockIndex = l;
                getUpdate(restock_name_btn.get(l).getText());
            }
        }
        for (int h = 0; h < deactivated_ItemList.size(); ++h) {
            if (e.getSource() == deactivated_ItemList.get(h)) {
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
            idInfo.setVisible(false);
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
            int quantity = Integer.parseInt(inputQuantity.getText());
            Double cost = Double.parseDouble(inputCost.getText());
            Date expirationDate = null;
            String vendor = inputVendor.getText();

            try {
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate.getText());
            } catch (ParseException error) {
                JOptionPane.showMessageDialog(null, "Error. Date entered wrong. Use YYYY-MM-dd.");
            }
            if (checkItemExit(name) != -1) {
                JOptionPane.showMessageDialog(null, "Item already exists!");
            } else {

                try {
                    int id = add_item(conn, name, quantity, cost, expirationDate, vendor);
                    JOptionPane.showMessageDialog(null, "Item added to Database.");
                    idList.add(id);
                    nameList.add(name);
                    quantityList.add(quantity);
                    costList.add(cost);
                    expirationDateList.add(expirationDate);
                    vendorList.add(vendor);

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
            getUpdate(inputName.getText());
        } else if (e.getSource() == searchBtn_deactivate) {
            btnDisplay(false);
            deactivateBtn.setVisible(true);
            searchBtn_deactivate.setVisible(false);
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
                searchBtn_deactivate.setVisible(true);
                JOptionPane.showMessageDialog(null, "Item doesn't exist!");
            } else {
                info_display(true);
                add_input_Display(false);
                ask_Name.setVisible(false);
                itemId.setText(String.valueOf(idList.get(i)));
                itemName.setText(nameList.get(i));
                itemQuantity.setText(String.valueOf(quantityList.get(i)));
                itemCost.setText(String.valueOf(costList.get(i)));
                itemExpirationDate.setText(dateFormat.format(expirationDateList.get(i)));
                itemVendor.setText(vendorList.get(i));
            }
        } else if (e.getSource() == updateBtn) {
            btnDisplay(false);
            nameList.set(i, inputName.getText());
            quantityList.set(i, Integer.parseInt(inputQuantity.getText()));
            costList.set(i, Double.parseDouble(inputCost.getText()));
            Date expirationDate = null;
            try {
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate.getText());
            } catch (ParseException error) {
               
                JOptionPane.showMessageDialog(null, "Date wrong. Use format YYYY-MM-dd");
            }
            expirationDateList.set(i, expirationDate);
            vendorList.set(i, inputVendor.getText());
            itemList.get(i).setText(inputName.getText());
            if (isRestock) {
                restock_quantity_list.set(restockIndex, Integer.parseInt(inputQuantity.getText()));
                if (restock_quantity_list.get(restockIndex) >= 500) {
                    restockPanel_Left.remove(restock_name_btn.get(restockIndex));
                    restockPanel_Right.remove(restock_quantity_label.get(restockIndex));
                    restockPanel_Left.revalidate();
                    restockPanel_Left.repaint();
                    restockPanel_Right.revalidate();
                    restockPanel_Right.repaint();
                    restock_name_list.remove(restockIndex);
                    restock_name_btn.remove(restockIndex);
                    restock_quantity_label.remove(restockIndex);
                    restock_quantity_list.remove(restockIndex);
                }
                
                isRestock = false;
            }
            try {
                update_item(conn, i);
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
            searchBtn_deactivate.setVisible(true);
        } else if (e.getSource() == deactivateBtn) {
            btnDisplay(false);
            deactivated_IdList.add(idList.get(i));
            deactivated_NameList.add(nameList.get(i));
            deactivated_CostList.add(costList.get(i));
            deactivated_QuantityList.add(quantityList.get(i));
            deactivated_ExpirationList.add(expirationDateList.get(i));
            deactivated_VendorList.add(vendorList.get(i));
            JMenuItem newItem = new JMenuItem(nameList.get(i));
            newItem.addActionListener(this);
            deactivated_ItemList.add(newItem);
            deactivatedMenu.add(newItem);
            try {
                deactivate_item(conn, idList.get(i));
                JOptionPane.showMessageDialog(null, "Deactivate successful.");
            } catch (SQLException deactivateException) {
                JOptionPane.showMessageDialog(null, "Deactivate unsuccessful.");
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
            btnDisplay(false);
            ask_Name.setVisible(true);
            clearBtn.setVisible(true);
            clearInputText();
            clearItemLabel();
            info_display(false);
            addBtn.setVisible(false);
            add_input_Display(false);
            inputName.setVisible(true);
            searchBtn_deactivate.setVisible(true);
        } else if (e.getSource() == activateBtn) {
            try{
                activate_item(conn, deactivated_IdList.get(i));

            idList.add(deactivated_IdList.get(i));
            nameList.add(deactivated_NameList.get(i));
            quantityList.add(deactivated_QuantityList.get(i));
            costList.add(deactivated_CostList.get(i));
            expirationDateList.add(deactivated_ExpirationList.get(i));
            vendorList.add(deactivated_VendorList.get(i));
            JMenuItem newItem = new JMenuItem(deactivated_NameList.get(i));
            newItem.addActionListener(this);
            itemList.add(newItem);
            viewMenu.add(newItem);
            deactivated_IdList.remove(i);
            deactivated_NameList.remove(i);
            deactivated_CostList.remove(i);
            deactivated_QuantityList.remove(i);
            deactivated_ExpirationList.remove(i);
            deactivated_VendorList.remove(i);
            deactivated_ItemList.remove(i);
            deactivatedMenu.remove(i);
            JOptionPane.showMessageDialog(null, "Activate successful.");
            
            // clear the screen
            clearItemLabel();
            info_display(false);
            activateBtn.setVisible(false);
            clearBtn.setVisible(false);
            }
            catch (SQLException addE){
                JOptionPane.showMessageDialog(null, "Activate unsuccessful.");
            }
        }
    }

    /**
     * Funtion to add items to the inventory database.
     * @param  conn                         Connection to the database.
     * @param  name                         String holding the name of the item.
     * @param  quantity                     Int for the amount of the item in inventory.
     * @param  cost                         Double cost per unit used per order of the item in inventory.
     * @param  expirationDate               Expiration date of the item in the inventory.
     * @param  vendor                       String holding the vendor of the item.
     * @return                Return the id of the item to add to the database.
     * @throws SQLException   Database query is incorrect or unsuccessful.
     */
    public Integer add_item(Connection conn, String name, int quantity, double cost, Date expirationDate,
            String vendor) throws SQLException {
        PreparedStatement addStatement = conn.prepareStatement(
                "INSERT INTO inventory( itemname, amount, cost, expirationdate,vendor, is_using ) VALUES(?,?,?,?,?, ?)");
        addStatement.setString(1, name);
        addStatement.setInt(2, quantity);
        addStatement.setDouble(3, cost);
        java.sql.Date sqlDate = new java.sql.Date(expirationDate.getTime());
        addStatement.setDate(4, sqlDate);
        addStatement.setString(5, vendor);
        addStatement.setBoolean(6, true);
        addStatement.executeUpdate();

        PreparedStatement itemId = conn.prepareStatement("SELECT itemid FROM inventory WHERE itemname=(?)");
        itemId.setString(1, name);


        ResultSet inventoryId = itemId.executeQuery();
        int id = -1;

        while (inventoryId.next()) {
            
            id = (inventoryId.getInt("itemid"));
        }

        return id;

    }

    /**
     * Update an item based on user inputs.
     * @param  conn                        Connection to database.
     * @param  index The index in local arrays of the item to update that is active.
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public void update_item(Connection conn, int index) throws SQLException {
        PreparedStatement updateStat = conn.prepareStatement(
                "UPDATE inventory SET itemname=(?), amount=(?), cost=(?), expirationdate=(?),vendor=(?), is_using=true  WHERE itemid = (?)");
        updateStat.setString(1, nameList.get(index));
        updateStat.setInt(2, quantityList.get(index));
        updateStat.setDouble(3, costList.get(index));
        java.sql.Date sqlDate = new java.sql.Date(expirationDateList.get(index).getTime());
        updateStat.setDate(4, sqlDate);
        updateStat.setString(5, vendorList.get(index));
        updateStat.setInt(6, idList.get(index));
        updateStat.executeUpdate();
    }

    /**
     * Deactivate an item when the deactivate button is clicked.
     * @param  conn                       Connection to database.
     * @param  id                         Id of the item to activate into inventory.
     * @throws SQLException Database query is incorrect or unsuccessful.
     */
    public void deactivate_item(Connection conn, int id) throws SQLException {
        PreparedStatement delStatement = conn.prepareStatement("UPDATE inventory SET is_using=false WHERE itemid=(?)");
        delStatement.setInt(1, id);
        delStatement.executeUpdate();
    }

    /**
     * Activate an item when the activate button is clicked.
     * @param  conn                       Connection to database.
     * @param  id                         Id of the item to activate into inventory.
     * @throws SQLException Database query is incorrect or unsuccessful.
     */
    public void activate_item(Connection conn, int id) throws SQLException {
        PreparedStatement addStat = conn.prepareStatement("UPDATE inventory SET is_using=true WHERE itemid=(?)");
        addStat.setInt(1, id);
        addStat.executeUpdate();
    }

    /**
     * Get the id of the inventory that are NOT active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Integer that holds the ids of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Integer> get_id_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemid FROM inventory WHERE is_using = false ORDER BY itemid ASC");

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            
            temp.add(findInventory.getInt("itemid"));

        }

        return temp;
    }

    /**
     * Get the quantity of the inventory that are NOT active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Integer that holds the quantity of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Integer> get_quantity_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT amount FROM inventory WHERE is_using = false ORDER BY itemid ASC");
       

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            
            temp.add(findInventory.getInt("amount"));

        }

        return temp;
    }

    /**
     * Get the names of the inventory that are NOT active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of String that holds the names of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<String> get_inventory_name_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory WHERE is_using = false ORDER BY itemid ASC");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
           
            temp.add(findInventory.getString("itemname"));

        }

        return temp;
    }

    /**
     * Get the cost of the inventory that are NOT active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Double that holds the costs of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Double> get_cost_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM inventory WHERE is_using = false ORDER BY itemid ASC");

        ArrayList<Double> temp = new ArrayList<Double>();

        while (findInventory.next()) {
            temp.add(findInventory.getDouble("cost"));
        }
        return temp;
    }

    /**
     * Get the expiration date of the inventory that are NOT active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Dates that holds the expiration Date of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Date> get_expiration_date_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT expirationdate FROM inventory WHERE is_using = false ORDER BY itemid ASC");

        ArrayList<Date> temp = new ArrayList<Date>();

        while (findInventory.next()) {
            temp.add(findInventory.getDate("expirationdate"));
        }
        return temp;
    }

    /**
     * Get the vendors of the inventory that are not active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of String that holds the vendors of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<String> get_vendor_deactive(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT vendor FROM inventory WHERE is_using = false ORDER BY itemid ASC");
        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("vendor"));
        }
        return temp;
    }

    /**
     * Get the id of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Integer that holds the ids of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Integer> get_id(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT itemid FROM inventory WHERE is_using = true ORDER BY itemid ASC");

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            
            temp.add(findInventory.getInt("itemid"));
        }
        return temp;
    }

    /**
     * Get the quantity of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Integer that holds the quantity of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Integer> get_quantity(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT amount FROM inventory WHERE is_using = true ORDER BY itemid ASC");
        // int count = 0; // Increments inventory array

        ArrayList<Integer> temp = new ArrayList<Integer>();
        while (findInventory.next()) {
            temp.add(findInventory.getInt("amount"));
        }
        return temp;
    }

    /**
     * Get the names of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of String that holds the names of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */

    public ArrayList<String> get_inventory_name(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory WHERE is_using = true ORDER BY itemid ASC");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("itemname"));
        }
        return temp;
    }

    /**
     * Get the cost of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Double that holds the costs of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<Double> get_cost(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT cost FROM inventory WHERE is_using = true ORDER BY itemid ASC");

        ArrayList<Double> temp = new ArrayList<Double>();

        while (findInventory.next()) {
            temp.add(findInventory.getDouble("cost"));
        }
        return temp;
    }

    /**
     * Get the expiration date of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of Dates that holds the expiration Date of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */

    public ArrayList<Date> get_expiration_date(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT expirationdate FROM inventory WHERE is_using = true ORDER BY itemid ASC");

        ArrayList<Date> temp = new ArrayList<Date>();

        while (findInventory.next()) {
            temp.add(findInventory.getDate("expirationdate"));
        }
        return temp;
    }

    /**
     * Get the vendors of the inventory that are active ordered by their id.
     * @param  conn         Connection to database.
     * @return              ArrayList of String that holds the vendors of the items .
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<String> get_vendor(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT vendor FROM inventory WHERE is_using = true ORDER BY itemid ASC");
        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("vendor"));
        }
        return temp;
    }

    /**
     * Get the names of the inventory that are active and have too low a quanity (under 500).
     * @param  conn         Connection to database.
     * @return              ArrayList of String that holds the names of the items that need to be restocked.
     * @throws SQLException  Database query is incorrect or unsuccessful.
     */
    public ArrayList<String> get_inventory_name_restock(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory WHERE amount < 500 and is_using = true");

        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            temp.add(findInventory.getString("itemname"));
        }
        return temp;
    }

    /**
     * Get the amounts of the inventory that is active and when the quanity is too low (under 500).
     * @param  conn         Connection to database.
     * @return              ArrayList of Integers that holds the amounts of the items that need to be restocked.
     * @throws SQLException Database query is incorrect or unsuccessful.
     */
    public ArrayList<Integer> get_inventory_amount_restock(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        ResultSet findInventory = stmt.executeQuery("SELECT amount FROM inventory WHERE amount < 500 and is_using = true");

        ArrayList<Integer> temp = new ArrayList<Integer>();

        while (findInventory.next()) {
            temp.add(findInventory.getInt("amount"));
        }

        return temp;
    }

  
    /**
     * Inventory GUI main function.
     * @param args[]  main function.
     */
    public static void main(String[] args) {
        new inventoryGUI(0);
    }
}
