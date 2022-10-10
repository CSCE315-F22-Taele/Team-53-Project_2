import java.awt.*;
import javax.swing.*;
import java.util.Date;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class inventoryGUI implements ActionListener{

    ////////// Declaration //////////

    // Menu Declaration
    JMenuBar menuBar = new JMenuBar();
    JMenu ingredientsMenu = new JMenu("Ingredient");
    JMenu miscellaneousMenu = new JMenu("Miscellaneous");

    JMenuItem riceItem = new JMenuItem("Rice");
    JMenuItem chickenItem = new JMenuItem("Chicken");
    JMenuItem meatballItem = new JMenuItem("Spicy meatball");
    JMenuItem falafelItem = new JMenuItem("FalafelItem");
    JMenuItem cucumbersItem = new JMenuItem("Cucumbers");
    JMenuItem cauliflowerItem = new JMenuItem("Cauliflower");
    JMenuItem tomatoItem = new JMenuItem("Tomato");
    JMenuItem spinachItem = new JMenuItem("Spinach");
    JMenuItem kaleItem = new JMenuItem("Kale");
    JMenuItem cabbageItem = new JMenuItem("Cabbage");
    JMenuItem romaineItem = new JMenuItem("Romaine");
    JMenuItem peppersItem = new JMenuItem("Banana peppers");
    JMenuItem cheeseItem = new JMenuItem("Feta cheese");
    JMenuItem harissaItem = new JMenuItem("Harissa");
    JMenuItem sauceItem = new JMenuItem("Tzatziki sauce");
    JMenuItem vinegarItem = new JMenuItem("Balsamic vinegar");
    JMenuItem pitaItem= new JMenuItem("Pita");
    JMenuItem hummusItem = new JMenuItem("Hummus");
    JMenuItem saltItem = new JMenuItem("Salt");
    JMenuItem bowlItem = new JMenuItem("Bowl");
    JMenuItem cupItem = new JMenuItem("Cup");
    JMenuItem lidItem = new JMenuItem("Lid");
    JMenuItem strawItem = new JMenuItem("Straw");
    JMenuItem napkinsItem = new JMenuItem("Napkins");

    JMenuItem itemArr[] = {riceItem, chickenItem, meatballItem, falafelItem, cucumbersItem,
                                cauliflowerItem, tomatoItem, spinachItem ,kaleItem, cabbageItem, 
                                romaineItem, peppersItem, cheeseItem,  harissaItem, sauceItem, 
                                vinegarItem, pitaItem, hummusItem,saltItem, bowlItem, cupItem, lidItem, strawItem, napkinsItem};


    // Inventory Item (Label)
    JLabel riceLabel = new JLabel("Rice");
    JLabel chickenLabel = new JLabel("Chicken");
    JLabel meatballsLabel = new JLabel("Sp Meatballs");
    JLabel falafelLabel = new JLabel("Falafel");
    JLabel cucumbersLabel = new JLabel("Cucumbers");
    JLabel cauliflowerLabel = new JLabel("Cauliflower");
    JLabel tomatoLabel = new JLabel("Tomato");
    JLabel spinachLabel = new JLabel("Spinach");
    JLabel kaleLabel = new JLabel("Kale");
    JLabel cabbageLabel = new JLabel("Cabbage");
    JLabel romaineLabel = new JLabel("Romaine");
    JLabel peppersLabel = new JLabel("Banana Peppers");
    JLabel cheesesLabel = new JLabel("Feta Cheese");
    JLabel harissaLabel = new JLabel("Harissa");
    JLabel sauceLabel = new JLabel("Tzatziki Sauce");
    JLabel vinegarLabel = new JLabel("Balsamic Vinegar");
    JLabel pitaLabel = new JLabel("Pita");
    JLabel hummusLabel = new JLabel("Hummus");
    JLabel saltLabel = new JLabel("Salt");
    JLabel bowlLabel = new JLabel("Bowl");
    JLabel cupLabel = new JLabel("Cup");
    JLabel lidLabel= new JLabel("Lid");
    JLabel strawLabel = new JLabel("Straw");
    JLabel napkinsLabel = new JLabel("Napkins");
    JLabel labelArr[] = {riceLabel, chickenLabel, meatballsLabel, falafelLabel, cucumbersLabel, cauliflowerLabel,
        tomatoLabel, spinachLabel, kaleLabel, cabbageLabel, romaineLabel, peppersLabel,cheesesLabel, harissaLabel, 
        sauceLabel, vinegarLabel, pitaLabel, hummusLabel,saltLabel, bowlLabel, cupLabel, lidLabel, strawLabel, napkinsLabel};


    // Input (For inventory item quantities)
    JTextField riceInput = new JTextField("0");
    JTextField chickenInput = new JTextField("0");
    JTextField meatballsInput = new JTextField("0");
    JTextField falafelInput = new JTextField("0");
    JTextField cucumbersInput = new JTextField("0");
    JTextField cauliflowerInput = new JTextField("0");
    JTextField tomatoInput = new JTextField("0");
    JTextField spinachInput = new JTextField("0");
    JTextField kaleInput = new JTextField("0");
    JTextField cabbageInput = new JTextField("0");
    JTextField romaineInput = new JTextField("0");
    JTextField peppersInput = new JTextField("0");
    JTextField cheesesInput = new JTextField("0");
    JTextField harissaInput = new JTextField("0");
    JTextField sauceInput = new JTextField("0");
    JTextField vinegarInput = new JTextField("0");
    
    JTextField pitaInput= new JTextField("0");
    JTextField hummusInput = new JTextField("0");
    JTextField saltInput = new JTextField("0");

    JTextField bowlInput = new JTextField("0");
    JTextField cupInput = new JTextField("0");
    JTextField lidInput= new JTextField("0");
    JTextField strawInput = new JTextField("0");
    JTextField napkinsInput= new JTextField("0");

    JTextField inputArr[] = {riceInput, chickenInput, meatballsInput,falafelInput, cucumbersInput, cauliflowerInput,
            tomatoInput, spinachInput, kaleInput, cabbageInput, romaineInput, peppersInput,cheesesInput, 
            harissaInput, sauceInput, vinegarInput,  pitaInput, hummusInput,saltInput, bowlInput, cupInput, 
            lidInput, strawInput, napkinsInput};


    
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
    
    JLabel actionItemsTitle = new JLabel("ACTION ITEMS");
    JTextArea out_of_stack = new JTextArea("");
    
    JLabel restockTitle = new JLabel("RESTOCK");
    JButton orderBtn = new JButton("ORDER NOW");

    // Logout 
    JButton logOutBtn = new JButton("LOG OUT");

    // Store data
    int quantityArr[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0};
    int restockQuantity[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0};
    Date expirationDataArr[] = {
        new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
        new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
        new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
        new Date(), new Date(), new Date(), new Date(), new Date(), new Date()
    };
    
 
    // Frame 
    JFrame f = new JFrame();

    // Const Vars
    int i = 0;
    int height_restock = 0;
    int height_action = 0;


    inventoryGUI(){
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
        for(int i = 0; i < 19; i++){
            ingredientsMenu.add(itemArr[i]);
            itemArr[i].addActionListener(this);
        }
        for(int j = 19; j < 24; ++j){
            miscellaneousMenu.add(itemArr[j]);
            itemArr[j].addActionListener(this);
        }
        menuBar.add(ingredientsMenu);
        menuBar.add(miscellaneousMenu);

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
            if(quantityArr[j] == 0){
                if(j % 5 == 0 && j != 0){
                    out_of_stack.append(labelArr[j].getText() + "\n");
                } else {
                    out_of_stack.append(labelArr[j].getText() + ", ");
                }
                
            }
        }

        f.add(actionItemsTitle);
        f.add(out_of_stack);

        f.add(restockTitle);
        f.add(orderBtn);
        
        
        for (int i = 0; i < 24; ++i) {
            f.add(labelArr[i]);
            f.add(inputArr[i]);
        }


        ////////// Logout //////////
        logOutBtn.addActionListener(this);
        logOutBtn.setBounds(300, 780, 200, 100);
        f.add(logOutBtn);

    }

    public void action(int k){
        itemName.setText(labelArr[i].getText());
        itemQuantity.setText(Integer.toString(quantityArr[k]));
        itemExpirationDate.setText(dateFormat.format(expirationDataArr[k]));
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
            labelArr[i].setBounds(1100, 360 + height_restock, 100, 30);
            inputArr[i].setBounds(1200, 360 + height_restock, 100, 30);
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
                restockQuantity[i] = Integer.parseInt(inputArr[i].getText());
            }
        } else if(e.getSource() == logOutBtn){
            // TODO: Implement

        }
        height_restock += 15;
    }

    public static void main(String[] args){
        new inventoryGUI();
    }

}
