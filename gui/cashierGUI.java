import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

/**
 * Implements the cashier graphical user interface. This helps us submit an order to the ordering datatable.
 */
public class cashierGUI implements ActionListener {
    

    // Button declaration
    JButton logoutBtn;
    JButton checkoutBtn;
    JButton managerBtn;
    JButton RestartOrder;

    ArrayList<String> nameOccursList = new ArrayList<String>();

    // Item's index in buttonList
    int btnIndex = -1;

    // Item's index in the nameOccursList
    int nameOccursIndex = -1;

    // Store the item's index number of the nameList and quantityList
    ArrayList<Integer> indexList = new ArrayList<Integer>();

    // Store the amount of each selected items
    ArrayList<JLabel> amountLabelList = new ArrayList<JLabel>();

    // Store the click numbers to update the amount of each selected items
    ArrayList<Integer> clickList = new ArrayList<Integer>();

 
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

    // Global Var 
  
    int employeeid;
    boolean is_manager;
    int orderid;
    Connection conn;

    // Price of each menu item
    JLabel sale = new JLabel("0");
    double totalPrice = 0;

    ArrayList<Double> priceArr = new ArrayList<Double>();

    ArrayList<String> menuArr = new ArrayList<String>();
    ArrayList<JButton> menu_buttons = new ArrayList<JButton>();

    ArrayList<Integer> ordereditems = new ArrayList<Integer>();

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

    boolean checkoutTrue = false;

    /**
     * Sets up the cashier GUI.
     * @param id  The employeeid that is taking the order.
     */
    public cashierGUI(int id) {

        try {
            dbConnect c1= new dbConnect();
            conn = c1.connectionSet();
            orderid = getOrderId(conn);
            menuArr = get_menu(conn);
            employeeid = id;
            is_manager = is_manager(conn, employeeid);
            priceArr = get_price(conn);

            for (int i = 0; i < menuArr.size(); i++) {
                ordereditems.add(0);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database operations failed.");
        }

        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray); 

        ////////// Menu-items Area //////////
        JPanel itemsPanel = new JPanel();
        Color pink = new Color(244, 220, 245);
        itemsPanel.setBackground(pink);
        itemsPanel.setBounds((int) (width * 0.06), (int) (height * 0.09), (int) (width * 0.6), (int) (height * 0.7));
        

        f.add(itemsPanel);

        ////////// Buttons //////////

        for (int i = 0; i < menuArr.size(); i++) {
            JButton newBtn = new JButton(menuArr.get(i));
            newBtn.setPreferredSize(new Dimension(300, 150));
            newBtn.addActionListener(this);
            menu_buttons.add(newBtn);

            itemsPanel.add(newBtn);
            itemsPanel.validate();
        }

        // Button logout
        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setBounds((int) (width * 0.17), (int) (height * 0.8), 100, 80);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(this);

        // Button checkout
        checkoutBtn = new JButton("CHECKOUT");
        checkoutBtn.setBackground(Color.LIGHT_GRAY);
        checkoutBtn.addActionListener(this);

        // Button manager
        managerBtn = new JButton("MANAGER OPTIONS");
        managerBtn.setBounds((int) (width * 0.27), (int) (height * 0.8), 100, 80);
        managerBtn.setBackground(Color.LIGHT_GRAY);
        managerBtn.addActionListener(this);

        //button restart 
        RestartOrder = new JButton("RESTART ORDER");
        RestartOrder.setBounds((int) (width * 0.37), (int) (height * 0.8), 100, 80);
        RestartOrder.setBackground(Color.LIGHT_GRAY);
        RestartOrder.addActionListener(this);

        ////////// Welcome Area //////////

        String userName = get_employee_name(employeeid);
        JLabel weclomeTitle = new JLabel("Welcome " + userName);
        weclomeTitle.setBounds((int) (width * 0.29), (int) (height * 0.02), 400, 50);
        weclomeTitle.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(weclomeTitle);

        ////////// Logout Area //////////
        JPanel logoutPanel = new JPanel(new GridLayout(1, 3));
        logoutPanel.setBackground(pink);
        logoutPanel.setBounds((int) (width * 0.06), (int) ((height * 0.82)), (int) (width * 0.6), (int) (height * 0.1));
        logoutPanel.add(logoutBtn);

        logoutPanel.add(RestartOrder);
        if (is_manager) {
            logoutPanel.add(managerBtn);
        }
        f.add(logoutPanel);

        ////////// Receipt Area //////////
        Color blueCute = new Color(194, 194, 252);
        
        // Title
        receiptPanel_Top.setBackground(blueCute);
        receiptPanel_Top.setBounds((int) (width * 0.7), 0, (int) (width * 0.3), (int) (height * 0.1));
        receiptPanel_Top.setLayout(null);
        receiptPanel_Top.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel title = new JLabel("Total Items");
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
        JLabel quantityTitle = new JLabel("Quantity and Price");
        quantityTitle.setVerticalAlignment(JLabel.TOP);
        quantityTitle.setHorizontalAlignment(JLabel.CENTER);
        receiptPanel_Right.add(quantityTitle);

        receiptPanel_Down.setBackground(blueCute);
        receiptPanel_Down.setBounds((int) (width * 0.7), (int) (height * 0.8), (int) (width * 0.3),
                (int) (height * 0.3));
        receiptPanel_Down.add(checkoutBtn);
        checkoutBtn.addActionListener(this);

        f.add(receiptPanel_Top);
        f.add(receiptPanel_Left);
        f.add(receiptPanel_Right);
        f.add(receiptPanel_Down);

        
        f.setSize(screenSize.width, screenSize.height);
        f.setLayout(null);
        f.setVisible(true);
    }

    /**
     * Checks that a name is in the nameOccursList
     * @param  name             String value holding the name we are checking for.
     * @return     index in the array or -1 if not in the array.
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
     * The actions that happen with the click of each button.
     * @param e  The button that was clicked.
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == checkoutBtn) {

            if (checkoutTrue == false) {
                insertOrder();
                new checkoutGUI(orderid, totalPrice, employeeid);
            }

            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == logoutBtn) {
            new loginGUI();
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        } else if (e.getSource() == managerBtn) {
            new managerGUI(employeeid);
            f.dispose();

        } else if (e.getSource() == RestartOrder) {
            new cashierGUI(employeeid);
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }

        sale.setText(String.valueOf(Math.round(totalPrice * 100.0) / 100.0));

        // update receipt
        for (int i = 0; i < menu_buttons.size(); ++i) {
            if (e.getSource() == menu_buttons.get(i)) {
                new inventoryPerOrderGUI(orderid);
                String name = menu_buttons.get(i).getText();
                String priceItem = String.valueOf(priceArr.get(i));
                btnIndex = i;
                nameOccursIndex = checkNameList(name);
                totalPrice += priceArr.get(i);

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
                    JLabel amountLabel = new JLabel("1" + "  :  " + priceItem);
                    amountLabel.setVerticalAlignment(JLabel.TOP);
                    amountLabel.setHorizontalAlignment(JLabel.CENTER);
                    receiptPanel_Right.add(amountLabel);
                    amountLabelList.add(amountLabel);
                    receiptPanel_Right.validate();

                } else {
                    // Update Item's amount by using click
                    clickList.set(nameOccursIndex, clickList.get(nameOccursIndex) + 1);
                    String newAmount = String.valueOf(clickList.get(nameOccursIndex) * priceArr.get(i));
                    String count = String.valueOf(clickList.get(nameOccursIndex));
                    
                    // find the corresponding label of the item
                    JLabel l = amountLabelList.get(nameOccursIndex);
                    l.setText(count + "  :  " + newAmount);

                    // update the amountLabelList
                    amountLabelList.set(nameOccursIndex, l);
                }
                ordereditems.set(i, ordereditems.get(i) + 1);
            }
        }

    }

    /**
     * Get the date starting for the orderid
     * @return The orderid starting number (not including the count of the order)
     */
    public int getDateforId() {
        
        // transform java data into proper SQL variables
        DateFormat formatDate = new SimpleDateFormat("yyMMdd");
        Date date = Calendar.getInstance().getTime();
        String strDate = formatDate.format(date);

        int value = Integer.valueOf(strDate);

        return value * 1000;
    }

    /**
     * Get the orderid number based on the sequential order.
     * @param  conn                       Connection with the database.
     * @return              order id number
     * @throws SQLException if the database SQL query did not work.
     */
    public int getOrderId(Connection conn) throws SQLException {

        int lastRecord = 0;
        int date = getDateforId();

        try {
            PreparedStatement lastOrder = conn.prepareStatement(
                    "SELECT orderid FROM ordering ORDER BY orderid DESC LIMIT 1");

            ResultSet orderInfo = lastOrder.executeQuery();

            while (orderInfo.next()) {
                lastRecord = orderInfo.getInt("orderid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Order id not retrieved.");
        }

        if (lastRecord / 1000 == date / 1000) {
            return lastRecord + 1;
        }

        return date + 1;

    }

    /**
     * Checks if the employee is manager
     * @param  conn                   Connection with the database.
     * @param  passCode               employee id that was inputted as the login passcode.
     * @return          Returns if it employee is a manager.
     */
    public boolean is_manager(Connection conn, int passCode) {
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

    /**
     * Insert order that has been inputted by the cashier into the database.
     */
    public void insertOrder() {

        

        try {



            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            String stringVal = formatTime.format(calendar.getTime());
            Time time = java.sql.Time.valueOf(stringVal);

            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE ordering SET timeoforder=(?), amount=(?), ordereditems=(?) WHERE orderid=(?)");

            double amount = totalPrice;

            Object[] orderedArr = ordereditems.toArray();
            Array ordered = conn.createArrayOf("INT", orderedArr);
            statement.setInt(4, orderid);
            statement.setTime(1, time);
            statement.setDouble(2, amount);
            statement.setArray(3, ordered);

            statement.executeUpdate();
        } catch (Exception e) {
            // e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Order insertion not done. ");
        }

    }

    /**
     * Get the name of the cashier/employee that is taking the order.
     * @param  employeeid   The employee id that is taking the order
     * @return  The employee name.
     */
    public String get_employee_name(int employeeid) {

        String name = "Name not fetched.";

        try {

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

   
    /**
     * Get Arraylist of the prices of all active menu items.
     * @param  conn         Connection with the database.
     * @return              A double ArrayList with the prices of the items selling in order of the menuid.
     * @throws SQLException If the database SQL query did not work.
     */
    public ArrayList<Double> get_price(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ArrayList<Double> items = new ArrayList<Double>();

        try {

            ResultSet findCost = stmt.executeQuery("SELECT cost FROM menucost WHERE is_selling = true ORDER BY id ASC");

            while (findCost.next()) {
                items.add(findCost.getDouble("cost"));
            }
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price ");
        }

        return items;
    }

    /**
     * Get Arraylist the menu items that are being sold in order of menu id.
     * @param  conn    Connection with the database.
     * @return           A String ArrayList with the names of the items selling in order of the menuid.
     * @throws SQLException If the database SQL query did not work.
     */
    public ArrayList<String> get_menu(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ArrayList<String> temp = new ArrayList<String>();

        try {

            ResultSet findMenu = stmt
                    .executeQuery("SELECT menuitem FROM menucost WHERE is_selling = true ORDER BY id ASC");

            while (findMenu.next()) {
                temp.add(findMenu.getString("menuitem"));
            }
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Menu items not retrieved from databases.");
        }

        return temp;
    }

    /**
     * Cashier GUI main function.
     * @param args[]  main function. 
     */
    public static void main(String args[]) {
        new cashierGUI(0);
    }
}
