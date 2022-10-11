import java.awt.*;
import javax.swing.*;
import java.util.Date;
import java.awt.event.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 




public class inventoryGUI implements ActionListener{

    ////////// Declaration //////////

    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu inventoryMenu = new JMenu("Inventory");
    
    // Data Output Area (for each item)
    JLabel nameInfo = new JLabel("Item Name: ");
    JLabel itemName = new JLabel("");
    JLabel quantityInfo = new JLabel("Quantity: ");
    JLabel itemQuantity = new JLabel("");
    JLabel expirationInfo = new JLabel("Expiration Date: ");
    JLabel itemExpirationDate = new JLabel("");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd"); 
    JLabel isRestock = new JLabel("Do you want restock? ");
    JButton restockBtn = new JButton("Yes");
    JButton clearBtn = new JButton("Clear");

    // Action Items & Restock Areas
    String inventory_names[]; 

    JLabel actionItemsTitle = new JLabel("ACTION ITEMS");
    JTextArea out_of_stack = new JTextArea("");
    
    JLabel restockTitle = new JLabel("RESTOCK");
    JButton orderBtn = new JButton("ORDER NOW");

    // Logout 
    JButton logOutBtn = new JButton("LOG OUT");
 
    // Frame 
    JFrame f = new JFrame();

    // Const Vars
    int i = 0;
    int height_restock = 0;
    int height_action = 0;


    inventoryGUI(){
        
        //ANNIE
        Connection conn = connectionSet();
        inventory_names = get_inventory_name(conn);

        // Menu Declatation
        // I moved it here bc need to access inventory_names
        ArrayList <JMenuItem> itemArr = new ArrayList <JMenuItem>();

        for (int index = 0; index < inventory_names.length; index++){
            itemArr.add(new JMenuItem(inventory_names[index]));
        }

        // Inventory Item (Label)
        ArrayList <JLabel> labelArr = new ArrayList <JLabel>();
        
        for (int index = 0; index < inventory_names.length; index++){
            labelArr.add(new JLabel(inventory_names[index]));
        }

        // Input (For inventory item quantities)
        ArrayList <JTextField> inputArr = new ArrayList <JTextField>();

        for (int index = 0; index < inventory_names.length; index++){
            inputArr.add(new JTextField("0"));
        }

        // Store data
        ArrayList <Integer> quantityArr = new ArrayList<Integer>();
        ArrayList <Integer> restockQuantity = new ArrayList<Integer>();
        ArrayList <Date> expirationDataArr = new ArrayList<Date>();

        for (int index = 0; index < inventory_names.length; index++){
            quantityArr.add(0);
            restockQuantity.add(0);
            expirationDataArr.add(new Date());
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

        // Add each menu-items to the menu bar and addActionListener

        for (int i = 0; i < inventory_names.length; i++){
            inventoryMenu.add(itemArr.get(i));
            itemArr.get(i).addActionListener(this);
        }

        menuBar.add(inventoryMenu);

        ////////// Data Output Area for each inventory item //////////

        nameInfo.setBounds(620, 200, 200, 200);
        itemName.setBounds(730, 200, 200, 200);
        
        quantityInfo.setBounds(620, 220, 200, 200);
        itemQuantity.setBounds(730, 220, 200, 200);

        expirationInfo.setBounds(620, 240, 200, 200);
        itemExpirationDate.setBounds(730, 240, 200, 200);

        // Ask mananger whether wants to restock the inventory item
        isRestock.setBounds(600, 380, 150, 20);
        isRestock.setVisible(false);

        // If so, click the button and it would negative to the restock area
        restockBtn.setBounds(750, 380, 50, 20);
        restockBtn.addActionListener(this);
        restockBtn.setVisible(false);

        // Clear the Data Output Area
        clearBtn.setBounds(670, 460, 120, 60);
        clearBtn.addActionListener(this);
        clearBtn.setVisible(false);

        f.add(itemName);
        f.add(nameInfo);
        f.add(quantityInfo);
        f.add(itemQuantity);
        f.add(itemExpirationDate);
        f.add(expirationInfo);
        f.add(restockBtn);
        f.add(clearBtn);
        f.add(isRestock);


        ////////// Action & Restock Area //////////

        // The Action area looks awful, I will modifiy it later

        actionItemsTitle.setBounds(1145, 10, 100, 100);
        out_of_stack.setBounds(1000, 80, 400, 250);

        // if the quantities of a item is 0, then display the item name as red color
        out_of_stack.setForeground(Color.RED);

        restockTitle.setBounds(1160, 340, 100, 30);
        orderBtn.addActionListener(this);
        orderBtn.setBounds(1150, 800, 100, 80);
        
        for(int j = 0; j < 24; ++j){
            if(quantityArr.get(j) == 0){
                if(j % 5 == 0 && j != 0){
                    out_of_stack.append(labelArr.get(j).getText() + "\n");
                } else {
                    out_of_stack.append(labelArr.get(j).getText() + ", ");
                }
                
            }
        }

        f.add(actionItemsTitle);
        f.add(out_of_stack);

        f.add(restockTitle);
        f.add(orderBtn);
        
        
        // for (int i = 0; i < 24; ++i) {
        //     f.add(labelArr[i]);
        //     f.add(inputArr[i]);
        // }

        for (int i = 0; i < labelArr.size(); i++){
            f.add(labelArr.get(i));
            f.add(inputArr.get(i));
        }


        ////////// Logout //////////
        logOutBtn.addActionListener(this);
        logOutBtn.setBounds(300, 780, 200, 100);
        f.add(logOutBtn);

    }

    public void action(int k){
        itemName.setText(labelArr.get(i).getText());
        itemQuantity.setText(Integer.toString(quantityArr.get(k)));
        itemExpirationDate.setText(dateFormat.format(expirationDataArr.get(k)));
        i = k;
        restockBtn.setVisible(true);
        isRestock.setVisible(true);
        clearBtn.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == riceItem){
            i = 0;
            action(i);
        } else if(e.getSource() == chickenItem){
            i = 1;
            action(i);
        } else if(e.getSource() == meatballItem){
            i = 2;
            action(i);
        } else if(e.getSource() == falafelItem){
            i = 3;
            action(i);
        } else if(e.getSource() == cucumbersItem){
            i = 4;
            action(i);
        } else if(e.getSource() == cauliflowerItem){
            i = 5;
            action(i);
        } else if(e.getSource() == tomatoItem){
            i = 6;
            action(i);
        } else if(e.getSource() == spinachItem){
            i = 7;
            action(i);
        } else if(e.getSource() == kaleItem){
            i = 8;
            action(i);
        } else if(e.getSource() == cabbageItem){
            i = 9;
            action(i);
        } else if(e.getSource() == romaineItem){
            i = 10;
            action(i);
        } else if(e.getSource() == peppersItem){
            i = 11;
            action(i);
        } else if(e.getSource() == cheeseItem){
            i = 12;
            action(i);
        } else if(e.getSource() == harissaItem){
            i = 13;
            action(i);
        } else if(e.getSource() == sauceItem){
            i = 14;
            action(i);
        } else if(e.getSource() == vinegarItem){
            i = 15;
            action(i);
        } else if(e.getSource() == pitaItem){
            i = 16;
            action(i);
        } else if(e.getSource() == hummusItem){
            i = 17;
            action(i);
        } else if(e.getSource() == saltItem){
            i = 18;
            action(i);
        } else if(e.getSource() == bowlItem){
            i = 19;
            action(i);
        } else if(e.getSource() == cupItem){
            i = 20;
            action(i);
        } else if(e.getSource() == lidItem){
            i = 21;
            action(i);
        } else if(e.getSource() == strawItem){
            i = 22;
            action(i);
        } else if(e.getSource() == napkinsItem){
            i = 23;
            action(i);
        } else if(e.getSource() == restockBtn){
            labelArr.get(i).setBounds(1100, 360 + height_restock, 100, 30);
            inputArr.get(i).setBounds(1200, 360 + height_restock, 100, 30);


        } else if(e.getSource() == clearBtn){
            itemName.setText("");
            itemQuantity.setText("");
            itemExpirationDate.setText("");
            restockBtn.setVisible(false);
            isRestock.setVisible(false);
            clearBtn.setVisible(false);
            height_restock -= 15;
        } else if(e.getSource() == orderBtn){
            for(int i = 0; i < 24; ++i){
                restockQuantity.get(i)= Integer.parseInt(inputArr.get(i).getText());
            }
        } else if(e.getSource() == logOutBtn){
            // FIX ME: TODO: Implement

        }
        height_restock += 15;
    }

    public String[] get_inventory_name(Connection conn){
        
       
        Statement stmt = conn.createStatement();
        ResultSet findInventory = stmt.executeQuery("SELECT itemname FROM inventory");
        int count = 0; // Increments inventory array

        while (findInventory.next()) {
            inventory_names.push_back( findInventory.getString("itemname") );
            count++;
        }

    }

    public Connection connectionSet(){
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
        new inventoryGUI();
    }

}
