import java.awt.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument.LeafElement;
import java.sql.*;
import java.sql.DriverManager;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

/* Array Index 
 * 0 - GYRO
 * 1 - BOWL
 * 2 - 2 falafels 
 * 3 - Extra pita and humus 
 * 4 - Extra Chicken
 * 5 - Extra Harissa
 * 6 - Extra Sp Meatball
 * 7 - Extra Tzatziki Sauce
 * 8 - Extra Balsamic Vinegar
 * 9 - Fountain drink
 */

/* Prices Index 
 * 0 - GYRO
 * 1 - BOWL
 * 2 - Extra pita and humus 
 * 3 - 2 falafels 
 * 4 - extra dressing 
 * 5 - drink
 * 6 - extra protein
 */

public class cashierGUI implements ActionListener {
    private void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    // Button declaration
    // JButton btn1;
    // JButton btn2;
    // JButton btn3;
    // JButton btn4;
    // JButton btn5;
    // JButton btn6;
    // JButton btn7;
    // JButton btn8;
    // JButton btn9;
    // JButton btn10;
    JButton logoutBtn;
    JButton checkoutBtn;
    JButton inventoryBtn;
    JButton editBtn;
    //JButton btnArr[] = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, logoutBtn, checkoutBtn };

    ArrayList <String> nameOccursList = new ArrayList<String>();

    // Item's index in buttonList
    int btnIndex = -1;

    // Item's index in the nameOccursList
    int nameOccursIndex = -1;

    // Store the item's index number of the nameList and quantityList
    ArrayList <Integer> indexList = new ArrayList<Integer>();

    //Store the amount of each selected items
    ArrayList <JLabel> amountLabelList = new ArrayList<JLabel>();

    // Store the click numbers to update the amount of each selected items
    ArrayList <Integer> clickList = new ArrayList<Integer>();

    private JLabel labelEmployee; 
    // Label Declaration
    JLabel l1 = new JLabel("GYRO");
    JLabel l2 = new JLabel("BOWL");
    JLabel l3 = new JLabel("2 falafels ");
    JLabel l4 = new JLabel("Extra pita and humus");
    JLabel l5 = new JLabel("Extra Chicken");
    JLabel l6 = new JLabel("Extra Harissa");
    JLabel l7 = new JLabel("Extra Sp Meatball");
    JLabel l8 = new JLabel("Extra Tzatziki Sauce");
    JLabel l9 = new JLabel("Extra Balsamic Vinegar");
    JLabel l10 = new JLabel("Fountain drink");
    JLabel labelArr[] = { l1, l2, l3, l4, l5, l6, l7, l8, l9, l10 };

    // Quantity input Declaration
    JTextField input_1 = new JTextField("0");
    JTextField input_2 = new JTextField("0");
    JTextField input_3 = new JTextField("0");
    JTextField input_4 = new JTextField("0");
    JTextField input_5 = new JTextField("0");
    JTextField input_6 = new JTextField("0");
    JTextField input_7 = new JTextField("0");
    JTextField input_8 = new JTextField("0");
    JTextField input_9 = new JTextField("0");
    JTextField input_10 = new JTextField("0");
    JTextField inputArr[] = { input_1, input_2, input_3, input_4, input_5, input_6, input_7,
            input_8, input_9, input_10 };

    // Frame Declaraiton
    JFrame f = new JFrame("Cashier GUI");

    // Global Var --> Unused, hardcoded can change later
    int height_items;
    int employeeid;
    boolean is_manager;
    int orderid;
    Connection conn;
    

    // Price of each menu item
    JLabel sale = new JLabel("0");
    double totalPrice = 0;

    ArrayList <Double> priceArr = new ArrayList<Double>();

    ArrayList <String> menuArr = new ArrayList<String>();
    ArrayList <JButton> menu_buttons = new ArrayList<JButton>();

    // Increments
    int gyroClick = 0;
    int bowlClick = 0;
    int falafelClick = 0;
    int pitaAndHumusClick = 0;
    int extraChickenClick = 0;
    int extraMeatballClick = 0;
    int extraHarissaClick = 0;
    int extraTzatzikiSauceClick = 0;
    int extraVinegarClick = 0;
    int drinkClick = 0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    int width_item = 0;
    int item_width = (int) (width * 0.092);
    int quantity_width = (int) (width * 0.19);

    JPanel receiptPanel_Top = new JPanel();
    JPanel receiptPanel_Left = new JPanel();
    JPanel receiptPanel_Right = new JPanel();
    JPanel receiptPanel_Down = new JPanel();

    
    public cashierGUI(int id) {
        
        try{
            conn = connectionSet();
            menuArr = get_menu(conn);
            employeeid = id; 
            is_manager = is_manager( conn, employeeid);
            priceArr = get_price(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database operations failed.");
        } 

        
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray); // TODO: Fix background color

        ////////// Menu-items Area //////////
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.yellow);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        // itemsPanel.setLayout(new GridLayout(5, 2, 10, 10));

        f.add(itemsPanel);
        
        ////////// Buttons //////////

        for (int i = 0; i < menuArr.size(); i++){
            JButton newBtn = new JButton(menuArr.get(i));
            newBtn.setPreferredSize(new Dimension(200, 60));
            newBtn.addActionListener(this);
            menu_buttons.add(newBtn);
            menu_buttons.get(i).setBackground(Color.RED);
            itemsPanel.add(newBtn);
            itemsPanel.validate();
        }
        
        // Button 11
        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setBounds((int) (width * 0.17), (int) (height * 0.8), 100, 80);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(this);

        // Button 12
        checkoutBtn = new JButton("CHECKOUT");
        checkoutBtn.setBackground(Color.LIGHT_GRAY);
        checkoutBtn.addActionListener(this);

        // Button 13
        inventoryBtn = new JButton("INVENTORY");
        inventoryBtn.setBounds((int) (width * 0.27), (int) (height * 0.8), 100, 80);
        inventoryBtn.setBackground(Color.LIGHT_GRAY);
        inventoryBtn.addActionListener(this);

        // Button 14
        editBtn = new JButton("EDIT MENU");
        editBtn.setBounds((int) (width * 0.37), (int) (height * 0.8), 100, 80);
        editBtn.setBackground(Color.LIGHT_GRAY);
        editBtn.addActionListener(this);

        ////////// Welcome Area //////////
        
        
        String userName = get_employee_name(employeeid);
        JLabel weclomeTitle = new JLabel("Welcome " + userName);
        weclomeTitle.setBounds((int) (width * 0.29), (int) (height * 0.02), 400, 50);
        weclomeTitle.setFont(new Font("Arial", Font.PLAIN, 30));
        f.add(weclomeTitle);

        ////////// Logout Area //////////
        JPanel logoutPanel = new JPanel(new GridLayout(1,3));
        logoutPanel.setBackground(Color.pink);
        logoutPanel.setBounds((int) (width * 0.06), (int) ((height * 0.82)), (int) (width * 0.6), (int) (height * 0.1));
        // logoutPanel.setLayout(new BorderLayout());
        // logoutBtn.setVerticalAlignment(JButton.CENTER);
        // logoutBtn.setHorizontalAlignment(JButton.CENTER);
        logoutPanel.add(logoutBtn);

        if( is_manager){
            logoutPanel.add(inventoryBtn);
            logoutPanel.add(editBtn);
        }
        f.add(logoutPanel);

    
   ////////// Receipt Area //////////

        // Title
        receiptPanel_Top.setBackground(Color.green);
        receiptPanel_Top.setBounds((int)(width * 0.7),0 , (int)(width * 0.3),(int)(height*0.1));
        receiptPanel_Top.setLayout(null);
        receiptPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("Total Items");
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
        JLabel quantityTitle = new JLabel("Quantity and Price");
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Right.add(quantityTitle);

        receiptPanel_Down.setBackground(Color.MAGENTA);
        receiptPanel_Down.setBounds((int)(width * 0.7),(int)(height*0.8) , (int)(width * 0.3),(int)(height*0.3));
        receiptPanel_Down.add(checkoutBtn);
        checkoutBtn.addActionListener(this);

        f.add(receiptPanel_Top);
        f.add(receiptPanel_Left);
        f.add(receiptPanel_Right);
        f.add(receiptPanel_Down);


        f.setSize(1400, 1600);
        f.setLayout(null);
        f.setVisible(true);
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
 
        if (e.getSource() == checkoutBtn) {
            insertOrder(); 
            new checkoutGUI(orderid, totalPrice, employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
           
        } else if (e.getSource() == logoutBtn) {
            new loginGUI(); 
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            
        }
        else if(e.getSource() == inventoryBtn){
            new inventoryGUI(employeeid); 
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource() == editBtn){
            new menuItemGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }


        sale.setText(String.valueOf(Math.round(totalPrice * 100.0) / 100.0));


        // update receipt
        for(int i = 0; i < menu_buttons.size(); ++i){
            if(e.getSource() == menu_buttons.get(i)){
                new inventoryPerOrderGUI(orderid);
                String name = menu_buttons.get(i).getText();
                String priceItem = String.valueOf(priceArr.get(i));
                btnIndex = i;
                nameOccursIndex = checkNameList(name);
                totalPrice += priceArr.get(i);

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
                    JLabel amountLabel = new JLabel("1" + "  :  " + priceItem);    
                    amountLabel.setVerticalAlignment(JLabel.TOP);
                    amountLabel.setHorizontalAlignment(JLabel.CENTER);
                    receiptPanel_Right.add(amountLabel);
                    amountLabelList.add(amountLabel);
                    receiptPanel_Right.validate();
                   

                } else {
                    // Update Item's amount by using click
                    clickList.set(nameOccursIndex, clickList.get(nameOccursIndex) + 1);
                    String newAmount = String.valueOf( clickList.get(nameOccursIndex) * priceArr.get(i));
                    String count = String.valueOf(clickList.get(nameOccursIndex) );
                    // find the corresponding label of the item
                    JLabel l = amountLabelList.get(nameOccursIndex);
                    l.setText(count + "  :  " + newAmount);

                    // update the amountLabelList
                    amountLabelList.set(nameOccursIndex, l);
                }
            }
        }

    }

    public int getDateforId(){
        // transform java data into proper SQL variables
        DateFormat formatDate= new SimpleDateFormat("yyMMdd");
        Date date = Calendar.getInstance().getTime();
        String strDate = formatDate.format(date);
        

        int value = Integer.valueOf( strDate);

        return value*1000;
    }

    public int getOrderId(Connection conn) throws SQLException{

        int lastRecord = 0;
        int date = getDateforId();

        try{
            PreparedStatement lastOrder = conn.prepareStatement(
                "SELECT orderid FROM ordering ORDER BY orderid DESC LIMIT 1");

            ResultSet orderInfo = lastOrder.executeQuery();
            
            while (orderInfo.next()) {
                lastRecord = orderInfo.getInt("orderid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Order id not retrieved.");
        }

        if( lastRecord / 1000 == date  / 1000){
            return lastRecord +1; 
        }
      
        return date + 1; 
        
    }

    public boolean is_manager(Connection conn, int passCode){
        boolean value = false;
        try {
            PreparedStatement employeeCheck = conn
                    .prepareStatement("SELECT ismanager FROM employee WHERE employeeid =(?)");
            employeeCheck.setInt(1, passCode);
            ResultSet employees = employeeCheck.executeQuery();

            while (employees.next()) {
                value = employees.getBoolean("ismanager");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
        return value; 
    }


    public void insertOrder(){

        Connection conn = connectionSet();

        try{
        orderid = getOrderId(conn); 
        

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        String stringVal = formatTime.format(calendar.getTime());
        Time time = java.sql.Time.valueOf( stringVal ); 
        

        PreparedStatement statement = conn.prepareStatement(
              "INSERT INTO ordering(orderid , timeoforder , amount ) VALUES (?,?,?)");

          
          double amount = totalPrice; 

          statement.setInt(1, orderid);
          statement.setTime(2, time);
          statement.setDouble(3, amount);
        //   statement.setInt(4, gyroClick);
        //   statement.setInt(5, bowlClick);
        //   statement.setInt(6, pitaAndHumusClick);
        //   statement.setInt(7, falafelClick);
        //   statement.setInt(8, protein);
        //   statement.setInt(9, dressing);
        //   statement.setInt(10, drinkClick);

        
          statement.executeUpdate();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Order insertion not done. ");
        } 

          
    }
    
    public String get_employee_name( int employeeid){
        
        String name = "Name not fetched.";

        try{
            
            PreparedStatement nameStat = conn
                .prepareStatement("SELECT employeename FROM employee WHERE employeeid=(?)");
            nameStat.setInt(1, employeeid);

            ResultSet findName = nameStat.executeQuery();

            while (findName.next()) {
                name = findName.getString("employeename");
            }
        }

        catch (Exception e) {
            name = "Name not fetched.";
        }

        return name;
    }
    public double getAmount(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {
        
        //FIX ME: MUST GET THE AMOUNTS FROM THE AMOUNTS TABLE 
        double ordertotal = (8.09 * orderedgyro) +
        (8.09 * orderedbowl) +
        (3.49 * orderedpitahummus) +
        (3.49 * orderedfalafel) +
        (1.99 * orderedprotein) +
        (0.39 * ordereddressing) +
        (2.45 * ordereddrink);

        return (double) ordertotal;
    }


    // dynamically get price
    public ArrayList<Double> get_price(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ArrayList <Double> items = new ArrayList <Double>();

        try{

            ResultSet findCost = stmt.executeQuery("SELECT cost FROM menucost");

            while (findCost.next()) {
                items.add(findCost.getDouble("cost"));
            }
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price ");
        }

        return items;
    }   

    public ArrayList<String> get_menu(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ArrayList<String> temp = new ArrayList<String>();

        try{

            ResultSet findMenu = stmt.executeQuery("SELECT menuitem FROM menucost");

            while (findMenu.next()) {
                temp.add(findMenu.getString("menuitem"));
            }
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Menu items not retrieved from databases.");
        }

        return temp;
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
            JOptionPane.showMessageDialog(null, "Database connection failed");
        } 

        return conn;
    }

    public static void main(String args[]) {
        new cashierGUI(0);
    }
}
