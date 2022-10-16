import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.*;
import java.sql.*;
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

public class inventoryPerOrderGUI implements ActionListener {
    private void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    ////////// Frame Declaraiton //////////
    JFrame f = new JFrame("Inventory per Order GUI");

    ////////// Deactivate //////////
    JMenuBar menuBar = new JMenuBar();
    JMenu deactivatedMenu = new JMenu("Deactivated");
    ArrayList<String> deactivatedNameList = new ArrayList<String>();
    ArrayList<JMenuItem> deactivatedMenuItemList = new ArrayList<JMenuItem>();
    JLabel nameInfo = new JLabel("Name: ");
    JLabel itemName = new JLabel();
    JButton activateBtn = new JButton("Activate");

    ////////// Store value //////////
    /* Use these two arraylists to connect db */
    ArrayList<String> nameList = new ArrayList<String>();
    //ArrayList<Integer> quantityList = new ArrayList<Integer>();

    /* The rest of these arraylists are using for the front-end */
    // Store btns
    ArrayList<JButton> btnList = new ArrayList<JButton>();
    JButton submitBtn = new JButton("Submit");
    JButton backToCashier = new JButton("Back to Cashier");

    // Store the names which have already clicked by user to prevent show up
    // repeatly
    ArrayList<String> nameOccursList = new ArrayList<String>();

    // Store the item's index number of the nameList and quantityList
    ArrayList<Integer> indexList = new ArrayList<Integer>();

    // Store the amount of each selected items
    ArrayList<JLabel> amountLabelList = new ArrayList<JLabel>();
    
    // Store the click numbers to update the amount of each selected items
    ArrayList<Integer> clickList = new ArrayList<Integer>();
    ArrayList<Integer>  inventoryCounts= new ArrayList<Integer>();
    
    ////////// Global Vars //////////
    int userId = 0;
    JLabel title = new JLabel("Welcome User " + userId);
    Connection conn;
    int orderid; 

    // Item's index in buttonList
    int btnIndex = -1;
    int activateIndex = -1;

    // Item's index in the nameOccursList
    int nameOccursIndex = -1;

    //////////// Panel //////////
    JPanel itemsPanel = new JPanel();
    JPanel receiptPanel_Top = new JPanel();
    JPanel receiptPanel_Left = new JPanel();
    JPanel receiptPanel_Right = new JPanel();
    JPanel receiptPanel_Down = new JPanel();

    ////////// Size //////////
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    Integer [] currInventory;
    inventoryPerOrderGUI(int id) {

        Color blueCute = new Color(194, 194, 252);
        ////////// Frame setting //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(1400, 1600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setJMenuBar(menuBar);

        menuBar.add(deactivatedMenu);

        title.setBounds(30, 5, 200, 60);

        orderid = id; 
        try {
            conn = connectionSet();
            nameList = get_inventory_name(conn);
            //quantityList = get_quantity(conn);
            for( int i=0; i<  nameList.size(); i++){
                inventoryCounts.add(0);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
           
            JOptionPane.showMessageDialog(null, "Database operations unsuccessful.");
        }


        ////////// Deactivate //////////

        // TODO: get data from db 
        // HARDCODE FOR TESTING ONLY, DELETE ONCE DB IS DONE
        deactivatedNameList.add("soy milk");
        deactivatedNameList.add("yogurt");

        // Add the deactivated items to the menu bar
        for (int i = 0; i < deactivatedNameList.size(); i++) {
            JMenuItem newItem = new JMenuItem(deactivatedNameList.get(i));
            newItem.addActionListener(this);
            deactivatedMenu.add(newItem);
            deactivatedMenuItemList.add(newItem);
        }

        nameInfo.setBounds(520, 210, 80, 20);
        itemName.setBounds(630, 210, 160, 20);
        activateBtn.setBounds(560, 245, 90, 25);
        nameInfo.setVisible(false);
        itemName.setVisible(false);
        activateBtn.setVisible(false);

        activateBtn.addActionListener(this);
        f.add(nameInfo);
        f.add(itemName);
        f.add(activateBtn);

        Color pink = new Color(244, 220, 245);


        ////////// Items Area //////////
        itemsPanel.setBackground(pink);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new FlowLayout());

        f.add(itemsPanel);

        ////////// generate btns //////////
        for (int i = 0; i < nameList.size(); ++i) {
            JButton newBtn = new JButton(nameList.get(i));
            newBtn.setPreferredSize(new Dimension(200, 60));
            newBtn.addActionListener(this);
            btnList.add(newBtn);
            itemsPanel.add(newBtn);
            itemsPanel.validate();
        }

        ////////// Receipt Area //////////

        // Title
        receiptPanel_Top.setBackground(blueCute);
        receiptPanel_Top.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) (height * 0.1));
        receiptPanel_Top.setLayout(null);
        receiptPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("Bowl/Gyro");
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Top.add(title);

        receiptPanel_Left.setBackground(blueCute);
        receiptPanel_Left.setBounds((int) (width * 0.7), (int) (height * 0.1), (int) (width * 0.15),
                (int) (height * 0.7));
        receiptPanel_Left.setLayout(new GridLayout(25, 1, 10, 10));
        JLabel itemNameTitle = new JLabel("Item");
        itemNameTitle.setVerticalAlignment(JLabel.TOP);
        itemNameTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Left.add(itemNameTitle);

        receiptPanel_Right.setBackground(blueCute);
        receiptPanel_Right.setBounds((int) (width * 0.85), (int) (height * 0.1), (int) (width * 0.15),
                (int) (height * 0.7));
        receiptPanel_Right.setLayout(new GridLayout(25, 1, 10, 10));
        JLabel quantityTitle = new JLabel("Quantity");
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Right.add(quantityTitle);

        receiptPanel_Down.setBackground(blueCute);
        receiptPanel_Down.setBounds((int) (width * 0.7), (int) (height * 0.8), (int) (width * 0.3),
                (int) (height * 0.3));
        receiptPanel_Down.add(submitBtn);
        submitBtn.addActionListener(this);

        f.add(receiptPanel_Top);
        f.add(receiptPanel_Left);
        f.add(receiptPanel_Right);
        f.add(receiptPanel_Down);

        ///// Back to Cashier /////
        backToCashier.addActionListener(this);
        backToCashier.setBounds((int) (width * 0.06), (int) (height * 0.8), (int) (width * 0.1), (int) (height * 0.05));
        f.add(backToCashier);

    }

    public int checkNameList(String name) {
        for (int j = 0; j < nameOccursList.size(); ++j) {
            if (name.equals(nameOccursList.get(j))) {
                return j;
            }
        }
        return -1;
    }

    public void activate(int i){
        JButton newBtn = new JButton(deactivatedNameList.get(i));
        newBtn.setPreferredSize(new Dimension(200, 60));
        newBtn.addActionListener(this);
        nameList.add(deactivatedNameList.get(i));
        btnList.add(newBtn);
        itemsPanel.add(newBtn);
        itemsPanel.validate();
        // remove the item from deactiveate menu and list
        deactivatedNameList.remove(i);
        deactivatedMenuItemList.remove(i);
        deactivatedMenu.remove(i);
        
        JOptionPane.showMessageDialog(null, "Activate successful.");
    }

    public void actionPerformed(ActionEvent e) {
        for (int h = 0; h < deactivatedMenuItemList.size(); ++h) {
            if(e.getSource() == deactivatedMenuItemList.get(h)){
                activateIndex = h;
                itemsPanel.setVisible(false);
                nameInfo.setVisible(true);
                itemName.setVisible(true);
                itemName.setText(deactivatedMenuItemList.get(h).getText());
                activateBtn.setVisible(true);
            }
        }
        for (int i = 0; i < btnList.size(); ++i) {
            if (e.getSource() == btnList.get(i)) {
                String name = btnList.get(i).getText();
                btnIndex = i;
                nameOccursIndex = checkNameList(name);

                if (nameOccursIndex == -1) {
                    indexList.add(btnIndex);
                    clickList.add(1);

                    // Shows the item's name
                    JLabel ItemNameLabel = new JLabel(name);
                    ItemNameLabel.setVerticalAlignment(JLabel.TOP);
                    ItemNameLabel.setHorizontalAlignment(JLabel.CENTER);
                    receiptPanel_Left.add(ItemNameLabel);
                    nameOccursList.add(name);
                    receiptPanel_Left.validate();

                    // Shows the item's amount
                    JLabel amountLabel = new JLabel("1");
                    amountLabel.setVerticalAlignment(JLabel.TOP);
                    amountLabel.setHorizontalAlignment(JLabel.CENTER);
                    receiptPanel_Right.add(amountLabel);
                    amountLabelList.add(amountLabel);
                    receiptPanel_Right.validate();


                } else {
                    // Update Item's amount by using click
                    clickList.set(nameOccursIndex, clickList.get(nameOccursIndex) + 1);
                    String newAmount = String.valueOf(clickList.get(nameOccursIndex));

                    // find the corresponding label of the item
                    JLabel l = amountLabelList.get(nameOccursIndex);
                    l.setText(newAmount);

                    // update the amountLabelList
                    amountLabelList.set(nameOccursIndex, l);
                
                }
                inventoryCounts.set(i, inventoryCounts.get(i) + 1);
                
            }
        }

        if (e.getSource() == submitBtn) {
            
                try {
                    update_item(conn, inventoryCounts);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    
                    JOptionPane.showMessageDialog(null, "Update of item inventory used failed. ");
                }
            

            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == backToCashier) {
            // FIX ME: Implement
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        } else if(e.getSource() == activateBtn){
            activate(activateIndex);
            JOptionPane.showMessageDialog(null, "Activate successful.");

            nameInfo.setVisible(false);
            itemName.setVisible(false);
            activateBtn.setVisible(false);
            itemsPanel.setVisible(true);

        }
    }

    ///// Backend /////
    public ArrayList<String> get_inventory_name(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory WHERE is_using = true ORDER BY itemid ASC");
        
        ArrayList<String> temp = new ArrayList<String>();

        while (findInventory.next()) {
            // inventory_names.push_back(findInventory.getString("itemname"));
            temp.add(findInventory.getString("itemname"));

        }
       
        return temp;
    }

    
    public void update_item(Connection conn,ArrayList <Integer> inventory) throws SQLException {
       // PreparedStatement updateStat = conn.prepareStatement("UPDATE inventory SET amount=(?) WHERE itemname=(?)");
    //    Statement stmt = conn.createStatement();
        boolean orderId_exists = false;
       PreparedStatement order_exists = conn.prepareStatement("SELECT EXISTS( SELECT 1 FROM ordering WHERE orderid =(?) )");
       order_exists.setInt(1, orderid);
        
       ResultSet orderInfo = order_exists.executeQuery();

       
          while (orderInfo.next()) {
            orderId_exists = orderInfo.getBoolean("exists");
          }
        
       
    
       if(orderId_exists){
        
        PreparedStatement get_inventory= conn.prepareStatement("SELECT inventory FROM ordering WHERE orderid=(?)");
        get_inventory.setInt(1, orderid);
         
        ResultSet inventoryInfo = get_inventory.executeQuery();
        
        // Object[] inventoryArray = inventory.toArray();
        // Array inventoryArr = conn.createArrayOf("INT",inventoryArray );

        
        while (inventoryInfo.next()) {
            Array temp = inventoryInfo.getArray("inventory");
            currInventory = (Integer[])temp.getArray();
        }
        
        //FIX ME: make this work
        //List vals = Arrays.asList(currInventory);
        
        for( int i=0; i<inventory.size(); i++){
            inventory.set(i, inventory.get(i) + (int) currInventory[i] );
        }

        Object[] inventoryArray = inventory.toArray();
        Array inventoryArr = conn.createArrayOf("INT",inventoryArray );

        

        PreparedStatement updateStat = conn.prepareStatement("UPDATE ordering SET inventory =(?) WHERE orderid=(?)");
       
        updateStat.setArray(1, inventoryArr);
        updateStat.setInt(2, orderid);

        updateStat.executeUpdate();
       }
       else{
       
       PreparedStatement updateStat = conn.prepareStatement("INSERT INTO ordering(orderid, inventory) VALUES(?,?)");
        Object[] inventoryArray = inventory.toArray();
        
        Array inventoryArr = conn.createArrayOf("INT",inventoryArray );
        updateStat.setArray(2, inventoryArr);
        updateStat.setInt(1, orderid);

        updateStat.executeUpdate();
       }
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
            JOptionPane.showMessageDialog(null, "Database connection failed.");
        }

        return conn;
    }

    public static void main(String[] args) {
        new inventoryPerOrderGUI(221015030);
    }
}
