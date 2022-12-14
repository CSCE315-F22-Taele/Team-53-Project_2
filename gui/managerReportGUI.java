import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Implement the Manager Report (sales) Graphical User Interface. Accessed by
 * managers, a manager can view a sales and/or excess report. A sales report
 * includes the amount each menu item was sold in a given time period. An excess
 * report includes the amount of inventory items used 10% or less in a given
 * time period.
 */
public class managerReportGUI implements ActionListener {
    ///// Frame /////
    JFrame f = new JFrame("Manager Report");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ///// Panel /////
    // Sale Report
    JPanel itemNamePanel = new JPanel();
    JPanel excessPanel = new JPanel();
    JPanel excessTitlePanel = new JPanel();

    ///// Label /////
    JLabel title_item_Label = new JLabel("Item");
    JLabel title_sale_Label = new JLabel("Sale");

    ///// Button /////
    JButton excessBtn = new JButton("Excess Report");
    JButton saleBtn = new JButton("Sale Report");
    JButton submitBtn = new JButton("Submit");
    JButton backToManager = new JButton("Back To Manager");

    ///// Store values /////
    // We will use these variable to store the data from database For sale report
    ArrayList<String> itemNameList = new ArrayList<String>();
    ArrayList<Integer> saleList = new ArrayList<Integer>();
    String[][] data;
    boolean saleChecked = false;
    boolean excessChecked = false;
    JTable sale_table;
    String[] column_names = { "Item", "Sale" };
    JScrollPane sale_panel;
    boolean tableCheck = false;

    // For excess report
    ArrayList<String> excessItemList = new ArrayList<String>();
    JLabel excessReport_title = new JLabel("only sold less than 10%");

    // Dates
    Date date_from;
    Date date_end;

    ///// Input datas /////
    JTextField fromDateInput = new JTextField();
    JTextField endDateInput = new JTextField();
    JLabel fromDateLabel = new JLabel("From: (YYYY-MM-DD)");
    JLabel endDateLabel = new JLabel("End: (YYYY-MM-DD)");

    Connection conn;

    /**
     * Constructor to setup the Manager Report Graphical User Interface.
     */
    public managerReportGUI() {
        dbConnect c1 = new dbConnect();
        conn = c1.connectionSet();

        ////////// Frame Setting //////////
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ///// Sale report area /////
        backToManager.addActionListener(this);
        backToManager.setBounds((int) (screenSize.width * 0.06), (int) (screenSize.height * 0.8),
                (int) (screenSize.width * 0.1),
                (int) (screenSize.height * 0.05));

        f.add(backToManager);

        ///// Excess Report Layout /////
        excessTitlePanel.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.22),
                (int) (screenSize.width * 0.6), (int) (screenSize.height * 0.05));
        excessTitlePanel.setLayout(new GridLayout(1, 1, 1, 1));
        excessTitlePanel.add(excessReport_title);
        excessReport_title.setHorizontalAlignment(JLabel.CENTER);
        excessReport_title.setVerticalAlignment(JLabel.TOP);
        excessReport_title.setForeground(Color.RED);

        excessPanel.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.27),
                (int) (screenSize.width * 0.6), (int) (screenSize.height * 0.7));

        excessPanel.setLayout(new GridLayout(20, 5, 10, 10));
        excessPanel.setVisible(false);
        excessTitlePanel.setVisible(false);

        f.add(excessTitlePanel);
        f.add(excessPanel);

        ///// Button /////
        saleBtn.setBounds((int) (screenSize.width * 0.4), (int) (screenSize.height * 0.15),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        excessBtn.setBounds((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.15),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));

        saleBtn.addActionListener(this);
        excessBtn.addActionListener(this);
        f.add(saleBtn);
        f.add(excessBtn);

        ///// Input date /////
        fromDateInput.setBounds((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.05),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        endDateInput.setBounds((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.1),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));

        fromDateLabel.setBounds((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.05),
                (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.05));
        endDateLabel.setBounds((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.1),
                (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.05));

        submitBtn.setBounds((int) (screenSize.width * 0.65), (int) (screenSize.height * 0.075),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        submitBtn.addActionListener(this);

        f.add(fromDateInput);
        f.add(endDateInput);
        f.add(fromDateLabel);
        f.add(endDateLabel);
        f.add(submitBtn);
    }

    /**
     * This function will determine whether to display the sale panel on screen.
     * 
     * @param b boolean that determines whether to display or hide the panel
     */
    public void salePanelDisplay(boolean b) {
        itemNamePanel.setVisible(b);

    }

    public void getSaleReport() {
        tableCheck = true;
        sale_table = new JTable(data, column_names);

        for (int j = 0; j < itemNameList.size(); ++j) {
            data[j][0] = itemNameList.get(j);
            data[j][1] = String.valueOf(saleList.get(j));
        }

        sale_panel = new JScrollPane(sale_table);
        sale_panel.setBounds((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.22),
                (int) (screenSize.width * 0.4), (int) (screenSize.height * 0.8));
        sale_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sale_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        sale_panel.setBackground(Color.RED);
        sale_panel.setVisible(true);
        f.getContentPane().add(sale_panel);
        f.validate();
        f.repaint();

        saleChecked = true;

    }

    /**
     * This function holds the action taken for each button clicked.
     * 
     * @param e Type of ActionEvent taken. This is determined based on the button
     *          that was clicked.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saleBtn) {
            excessPanel.setVisible(false);
            excessTitlePanel.setVisible(false);

            if (!saleChecked) {
                getSaleReport();
                sale_panel.setVisible(true);
            }
            sale_panel.setVisible(true);

        } else if (e.getSource() == excessBtn) {
            excessPanel.setVisible(true);
            excessTitlePanel.setVisible(true);
            if (tableCheck) {
                sale_panel.setVisible(false);
            }
            if (!excessChecked) {
                for (int i = 0; i < excessItemList.size(); ++i) {
                    JLabel newSaleLabel = new JLabel(String.valueOf(excessItemList.get(i)));
                    newSaleLabel.setVerticalAlignment(JLabel.TOP);
                    newSaleLabel.setHorizontalAlignment(JLabel.CENTER);
                    excessPanel.add(newSaleLabel);
                }
                excessChecked = true;
            }

        } else if (e.getSource() == submitBtn) {

            salePanelDisplay(false);
            excessPanel.setVisible(false);
            excessTitlePanel.setVisible(false);
            if (tableCheck) {
                sale_panel.setVisible(false);
            }

            itemNameList.clear();
            saleList.clear();
            excessItemList.clear();

            try {
                date_from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateInput.getText());
                date_end = new SimpleDateFormat("yyyy-MM-dd").parse(endDateInput.getText());
            } catch (ParseException e1) {

            }

            try {
                itemNameList = get_sale_report_name(conn);
                saleList = get_sale_report_amount(conn, date_from, date_end);
                excessItemList = get_excess_report(conn, date_from, date_end);
                saleChecked = false;
                excessChecked = false;
            } catch (SQLException e1) {

            }
            data = new String[itemNameList.size()][2];
        }

        else if (e.getSource() == backToManager) {
            f.dispose();
        }

    }

    /**
     * This function will determine the number of menu items in database.
     * 
     * @param conn Connection to database
     * @return amount of menu items
     * @throws SQLException error check if query is unsuccessful
     */
    public int get_menu_item_num(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM menucost");

        int count = 0;
        while (rs.next()) {
            count++;
        }

        return count;
    }

    /**
     * This function will determine the number of menu items sold in a given time
     * period.
     * 
     * @param conn  Connection to database
     * @param start start Date
     * @param end   end Date
     * @return array of quantity of each menu items sold in a time period
     * @throws SQLException error check if query is unsuccessful
     */
    public ArrayList<Integer> get_sale_report_amount(Connection conn, Date start, Date end) throws SQLException {

        // Convert Date into orderid
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String start_string = dateFormat.format(start) + "000";
        String end_string = dateFormat.format(end) + "000";

        // Remove the first two digits of year
        start_string = start_string.substring(2);
        end_string = end_string.substring(2);

        int start_int = Integer.parseInt(start_string);
        int end_int = Integer.parseInt(end_string);

        Integer[] temp = new Integer[get_menu_item_num(conn)];

        // Set starting menu item bought
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;
        }

        String prep_statement = "SELECT ordereditems FROM ordering WHERE orderid > " + start_int + " AND orderid < "
                + end_int + " AND ordereditems IS NOT NULL";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(prep_statement);

        ArrayList<Integer[]> all_sales = new ArrayList<Integer[]>();
        while (rs.next()) {
            Array cur_query = rs.getArray("ordereditems");
            Integer[] cur_arr = (Integer[]) cur_query.getArray();
            all_sales.add(cur_arr);
        }

        // Calculate total number of each menu item sold per order
        for (int i = 0; i < all_sales.size(); i++) {
            for (int j = 0; j < all_sales.get(i).length; j++) {
                temp[j] = temp[j] + all_sales.get(i)[j];
            }
        }

        // Convert int array into ArrayList for consistent implementation
        ArrayList<Integer> convert_temp = new ArrayList<Integer>();

        for (int i : temp) {
            convert_temp.add(i);
        }

        return convert_temp;
    }

    /**
     * This function will return a list of all menu items in the menu by id.
     * 
     * @param conn Connection to database
     * @return list of all menu
     * @throws SQLException error check if query is unsuccessful
     */
    public ArrayList<String> get_sale_report_name(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT menuitem FROM menucost ORDER BY id ASC");

        ArrayList<String> temp = new ArrayList<String>();
        while (rs.next()) {
            temp.add(rs.getString("menuitem"));
        }

        return temp;
    }

    /**
     * This function will determine the number of inventory items in database.
     * 
     * @param conn Connection to database
     * @return number of inventory items
     * @throws SQLException error check if query is unsuccessful
     */
    public int get_inventory_num(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT itemid FROM inventory");

        int count = 0;
        while (rs.next()) {
            count++;
        }

        return count;
    }

    /**
     * This function will determine the number of each inventory item use in a given
     * time period.
     * 
     * @param conn  Connection to database
     * @param start start date
     * @param end   end date
     * @return array of total number of each inventory item used in a time period
     * @throws SQLException error check if query is unsuccessful
     */
    public ArrayList<Integer> get_inventory_use(Connection conn, Date start, Date end) throws SQLException {
        // Convert Date into orderid
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String start_string = dateFormat.format(start) + "000";
        String end_string = dateFormat.format(end) + "000";

        // Remove the first two digits of year
        start_string = start_string.substring(2);
        end_string = end_string.substring(2);

        int start_int = Integer.parseInt(start_string);
        int end_int = Integer.parseInt(end_string);

        Integer[] temp = new Integer[get_inventory_num(conn)];

        // Set starting menu item bought
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;
        }

        String prep_statement = "SELECT inventory FROM ordering WHERE orderid > " + start_int + " AND orderid < "
                + end_int;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(prep_statement);

        ArrayList<Integer[]> all_inventory = new ArrayList<Integer[]>();
        while (rs.next()) {
            Array cur_query = rs.getArray("inventory");
            Integer[] cur_arr = (Integer[]) cur_query.getArray();
            all_inventory.add(cur_arr);
        }

        // Calculate total number of each inventory item sold per order
        for (int i = 0; i < all_inventory.size(); i++) {
            for (int j = 0; j < all_inventory.get(i).length; j++) {
                temp[j] = temp[j] + all_inventory.get(i)[j];
            }
        }

        // Convert int array into ArrayList for consistent implementation
        ArrayList<Integer> convert_temp = new ArrayList<Integer>();

        for (int i : temp) {
            convert_temp.add(i);
        }

        return convert_temp;
    }

    /**
     * This function will get an array of the current inventory amounts for each
     * inventory item.
     * 
     * @param conn Connection to the database
     * @return array of current amount of each inventory item
     * @throws SQLException error check if query is unsuccessful
     */
    public ArrayList<Integer> get_inventory(Connection conn) throws SQLException {
        String prep_statement = "SELECT amount FROM inventory ORDER BY itemid ASC";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(prep_statement);

        ArrayList<Integer> temp = new ArrayList<Integer>();
        while (rs.next()) {
            temp.add(rs.getInt("amount"));
        }

        return temp;
    }

    /**
     * This function will determine the percentage of an inventory item is sold.
     * 
     * @param used_inventory   array of total amount of inventory used in a given
     *                         time period
     * @param remain_inventory array of current total inventory and used inventory
     * @return array based on inventory item index, with 1 as item sold less than
     *         10%
     */
    public ArrayList<Integer> calculate_inventory_use(ArrayList<Integer> used_inventory,
            ArrayList<Integer> remain_inventory) {
        ArrayList<Integer> no_sale = new ArrayList<Integer>();
        for (int i = 0; i < remain_inventory.size(); i++) {
            int cur = used_inventory.get(i) / (used_inventory.get(i) + remain_inventory.get(i)) * 100;

            // Determine if inventory item is used more than 10%
            // If less than 10% --> 1; else --> 0
            if (cur < 10) {
                no_sale.add(1);
            } else {
                no_sale.add(0);
            }
        }

        return no_sale;
    }

    /**
     * This function will create a list of inventory items that are used less that
     * 10% in a given time frame.
     * 
     * @param conn  Connection to database
     * @param start start date
     * @param end   end date
     * @return array of inventory item names that are used less than 10%
     * @throws SQLException error check if query is unsuccessful
     */
    public ArrayList<String> get_excess_report(Connection conn, Date start, Date end) throws SQLException {
        String prep_statement = "SELECT itemname FROM inventory ORDER BY itemid ASC";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(prep_statement);

        ArrayList<String> temp = new ArrayList<String>();
        while (rs.next()) {
            temp.add(rs.getString("itemname"));
        }

        // Helper functions used
        ArrayList<Integer> no_sale = calculate_inventory_use(get_inventory_use(conn, start, end),
                get_inventory(conn));

        ArrayList<String> no_sale_name = new ArrayList<String>();

        for (int i = 0; i < no_sale.size(); i++) {
            if (no_sale.get(i) == 1) {
                no_sale_name.add(temp.get(i));
            }
        }

        return temp;
    }

    public static void main(String[] args) {
        new managerReportGUI();
    }

}
