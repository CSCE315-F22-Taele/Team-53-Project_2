import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JOptionPane;

/**
 * Implements the ordering items graphical user interface. This helps us submit an order with proper inventory changes to the ordering data table.
 */
public class inventoryPerOrderGUI implements ActionListener {
    private void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    ////////// Frame Declaraiton //////////
    JFrame f = new JFrame("Inventory per Order GUI");

    ////////// Store value //////////
    /* Use arraylist to connect db */
    ArrayList<String> nameList = new ArrayList<String>();
  

    /* The rest of these arraylists are using for the front-end */
    ArrayList<JButton> btnList = new ArrayList<JButton>();
    JButton submitBtn = new JButton("Submit");
    JButton backToCashier = new JButton("Back to Cashier");

    // Store the names which have already clicked by user to prevent show up
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

    /**
     * Setup nventory Per Order GUI constructor.
     * @param id  Orderid from the cashier page. Must use this to add to the inventory database.
     */
    inventoryPerOrderGUI(int id) {

        orderid = id;
        try {
            dbConnect c1= new dbConnect();
            conn = c1.connectionSet();
            nameList = get_inventory_name(conn);
            
            for( int i=0; i<  nameList.size(); i++){
                inventoryCounts.add(0);
            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Database operations unsuccessful.");
        }

        Color pink = new Color(244, 220, 245);
        Color blueCute = new Color(194, 194, 252);
        
        ////////// Frame setting //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        f.setBackground(Color.gray);
        f.setSize(width, height);
        f.setLayout(null);
        f.setVisible(true);
        title.setBounds(30, 5, 200, 60);

        ////////// Items Area //////////
        itemsPanel.setBackground(pink);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        itemsPanel.setLayout(new FlowLayout());

        f.add(itemsPanel);

        ////////// generate btns //////////
        for (int i = 0; i < nameList.size(); ++i) {
            JButton newBtn = new JButton(nameList.get(i));
            newBtn.setPreferredSize(new Dimension((int) width / 10, (int) height / 12));
            newBtn.setFont(new Font("Serif", Font.PLAIN, 20));
            newBtn.addActionListener(this);
            btnList.add(newBtn);
            itemsPanel.add(newBtn);
            itemsPanel.validate();
        }

        ////////// Receipt Area //////////

        // Title
        receiptPanel_Top.setBackground(blueCute);
        receiptPanel_Top.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) (height * 0.05));
        receiptPanel_Top.setLayout(null);
        receiptPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("Inventory Used:");
        title.setFont(new Font("Serif", Font.BOLD, 32));

        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Top.add(title);

        receiptPanel_Left.setBackground(blueCute);
        receiptPanel_Left.setBounds((int) (width * 0.7), (int) (height * 0.05), (int) (width * 0.15),
                (int) (height * 0.8));
        receiptPanel_Left.setLayout(new GridLayout(25, 1, 10, 2));
        JLabel itemNameTitle = new JLabel("Item");
        itemNameTitle.setFont(new Font("Serif", Font.BOLD, 18));
        itemNameTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        itemNameTitle.setVerticalAlignment(JLabel.TOP);
        itemNameTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Left.add(itemNameTitle);

        receiptPanel_Right.setBackground(blueCute);
        receiptPanel_Right.setBounds((int) (width * 0.85), (int) (height * 0.05), (int) (width * 0.15),
                (int) (height * 0.8));
        receiptPanel_Right.setLayout(new GridLayout(25, 1, 10, 2));
        JLabel quantityTitle = new JLabel("Quantity");
        quantityTitle.setFont(new Font("Serif", Font.BOLD, 18));
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Right.add(quantityTitle);

        receiptPanel_Down.setBackground(pink);
        receiptPanel_Down.setBounds((int) (width * 0.7), (int) (height * 0.85), (int) (width * 0.3),
                (int) (height * 0.15));
        receiptPanel_Down.add(submitBtn);
        submitBtn.setPreferredSize(new Dimension(300, (int) (height * 0.05)));
        submitBtn.setFont(new Font("Serif", Font.PLAIN, 20));

        submitBtn.addActionListener(this);

        f.add(receiptPanel_Top);
        f.add(receiptPanel_Left);
        f.add(receiptPanel_Right);
        f.add(receiptPanel_Down);

        ///// Back to Cashier /////
        backToCashier.addActionListener(this);
        backToCashier.setFont(new Font("Serif", Font.PLAIN, 20));
        backToCashier.setBounds((int) (width * 0.06), (int) (height * 0.8), (int) (width * 0.1), (int) (height * 0.05));
        f.add(backToCashier);

    }

    /**
     * Check if name is in the inventory
     * @param  name  Item name
     * @return     The index of the value in the local array or -1 if does not exist.
     */
    public int checkNameList(String name) {
        for (int j = 0; j < nameOccursList.size(); ++j) {
            if (name.equals(nameOccursList.get(j))) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Allows for action given a button/click from the user.
     * @param e The button clicked or the action that has to occur.
     */
    public void actionPerformed(ActionEvent e) {
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
                    ItemNameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
                    ItemNameLabel.setVerticalAlignment(JLabel.TOP);
                    ItemNameLabel.setHorizontalAlignment(JLabel.CENTER);
                    receiptPanel_Left.add(ItemNameLabel);
                    nameOccursList.add(name);
                    receiptPanel_Left.validate();

                    // Shows the item's amount
                    JLabel amountLabel = new JLabel("1");
                    amountLabel.setFont(new Font("Serif", Font.PLAIN, 15));
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
                   

                    JOptionPane.showMessageDialog(null, "Update of item inventory used failed. ");
                }


            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == backToCashier) {
           
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }
    }

    ///// Backend /////
    /**
     * Get the inventory name from the database in order of the item id.
     * @param  conn         Connection to the database.
     * @return              ArrayList of Strings that holds the names of the inventory that are active.
     * @throws SQLException if the database SQL query did not work.
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
     * Add the inventory used for an item into ordering database.
     * @param  conn                       Connection to the database.
     * @param  inventory   ArrayList of Integers that holds all the values of the inventory used/clicked through.
     * @throws SQLException if the database SQL query did not work.
     */
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



        while (inventoryInfo.next()) {
            Array temp = inventoryInfo.getArray("inventory");
            currInventory = (Integer[])temp.getArray();
        }

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

    /**
     * Inventory Per Order GUI main function.
     * @param args[]  main function.
     */
    public static void main(String[] args) {
        new inventoryPerOrderGUI(221015030);
    }
}
