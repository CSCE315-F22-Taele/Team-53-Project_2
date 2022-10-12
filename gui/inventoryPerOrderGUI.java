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



    ////////// Store value //////////
    /* Use these two arraylists to connect db */
    ArrayList <String> nameList = new ArrayList<String>();         
    ArrayList <Integer> quantityList = new ArrayList<Integer>();    

    /*  The rest of these arraylists are using for the front-end */
    // Store btns 
    ArrayList <JButton> btnList = new ArrayList<JButton>();   
    JButton submitBtn = new JButton("Submit");
    JButton backToCashier = new JButton("Back to Cashier");
    
    // Store the names which have already clicked by user to prevent show up repeatly 
    ArrayList <String> nameOccursList = new ArrayList<String>();

    // Store the item's index number of the nameList and quantityList
    ArrayList <Integer> indexList = new ArrayList<Integer>();

    //Store the amount of each selected items
    ArrayList <JLabel> amountLabelList = new ArrayList<JLabel>();

    // Store the click numbers to update the amount of each selected items
    ArrayList <Integer> clickList = new ArrayList<Integer>();


    
    ////////// Global Vars //////////
    int userId = 0;
    JLabel title = new JLabel("Welcome User " + userId);
    Connection conn;


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



    inventoryPerOrderGUI(){

        try {
            conn = connectionSet();
            nameList = get_inventory_name(conn);
            quantityList = get_quantity(conn);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ////////// Frame setting //////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        title.setBounds(30, 5, 200, 60);

        ////////// Items Area //////////
        itemsPanel.setBackground(Color.yellow);
        itemsPanel.setBounds((int)(width * 0.06), (int)(height * 0.09), (int)(width * 0.6),(int)(height * 0.7));
        itemsPanel.setLayout(new FlowLayout());

        f.add(itemsPanel);

        ////////// generate btns //////////
        for(int i = 0; i < nameList.size(); ++i){
            JButton newBtn = new JButton(nameList.get(i));
            newBtn.setPreferredSize(new Dimension(200, 60));
            newBtn.addActionListener(this);
            btnList.add(newBtn);
            itemsPanel.add(newBtn);
            itemsPanel.validate();
        }

        ////////// Receipt Area //////////

        // Title
        receiptPanel_Top.setBackground(Color.green);
        receiptPanel_Top.setBounds((int)(width * 0.7),0 , (int)(width * 0.3),(int)(height*0.1));
        receiptPanel_Top.setLayout(null);
        receiptPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("Bowl/Gyro");
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Top.add(title);

        receiptPanel_Left.setBackground(Color.CYAN);
        receiptPanel_Left.setBounds((int)(width * 0.7),(int)(height*0.1) , (int)(width * 0.15),(int)(height*0.7));
        receiptPanel_Left.setLayout(new GridLayout(25, 1, 10, 10));
        JLabel itemNameTitle = new JLabel("Item");
        itemNameTitle.setVerticalAlignment(JLabel.TOP);
        itemNameTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Left.add(itemNameTitle);

        receiptPanel_Right.setBackground(Color.CYAN);
        receiptPanel_Right.setBounds((int)(width * 0.85),(int)(height*0.1) , (int)(width * 0.15),(int)(height*0.7));
        receiptPanel_Right.setLayout(new GridLayout(25, 1, 10, 10));
        JLabel quantityTitle = new JLabel("Quantity");
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Right.add(quantityTitle);

        receiptPanel_Down.setBackground(Color.MAGENTA);
        receiptPanel_Down.setBounds((int)(width * 0.7),(int)(height*0.8) , (int)(width * 0.3),(int)(height*0.3));
        receiptPanel_Down.add(submitBtn);
        submitBtn.addActionListener(this);

        f.add(receiptPanel_Top);
        f.add(receiptPanel_Left);
        f.add(receiptPanel_Right);
        f.add(receiptPanel_Down);

        ///// Back to Cashier /////
        backToCashier.addActionListener(this);
        backToCashier.setBounds((int)(width * 0.06), (int)(height* 0.8), (int)(width * 0.1), (int)(height * 0.05));
        f.add(backToCashier);

    }

    public int checkNameList(String name){
        for(int j = 0; j < nameOccursList.size(); ++j){
            if(name.equals(nameOccursList.get(j))){
                return j;
            }
        }
        return -1;
    }

    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < btnList.size(); ++i){
            if(e.getSource() == btnList.get(i)){
                String name = btnList.get(i).getText();
                btnIndex = i;
                nameOccursIndex = checkNameList(name);
                
                if(nameOccursIndex == -1){
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
            }
        }

        if(e.getSource() == submitBtn){
            for(int k = 0; k < clickList.size(); ++k){
                int outItem = clickList.get(k);
                int item_amount = quantityList.get(indexList.get(k)) - outItem;
                quantityList.set(indexList.get(k), item_amount);
                try {
                    update_item(conn, indexList.get(k));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else if(e.getSource() == backToCashier){
            // FIX ME: Implement
        }
    }

    ///// Backend /////
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

    public void update_item(Connection conn, int index) throws SQLException {
        PreparedStatement updateStat = conn.prepareStatement(
                "UPDATE inventory SET amount=(?) WHERE itemname=(?)");

        updateStat.setString(2, nameList.get(index));
        updateStat.setInt(1, quantityList.get(index));

        updateStat.executeUpdate();
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


    public static void main(String[] args){
        new inventoryPerOrderGUI();
    }
}
