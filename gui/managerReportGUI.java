import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.*;
import java.sql.Array;
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

public class managerReportGUI implements ActionListener {
    ///// Frame /////
    JFrame f = new JFrame("Manager Report");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ///// Panel /////
    // Sale Report
    JPanel itemNamePanel = new JPanel();
    JPanel salePanel = new JPanel();
    JPanel excessPanel = new JPanel();

    ///// Label /////
    JLabel title_item_Label = new JLabel("Item");
    JLabel title_sale_Label = new JLabel("Sale");

    ///// Button /////
    JButton excessBtn = new JButton("Excess Report");
    JButton saleBtn = new JButton("Sale Report");
    JButton submitBtn = new JButton("Submit");

    ///// Store values /////
    // We will use these variable to store the data from database
    // For sale report
    ArrayList<String> itemNameList = new ArrayList<String>();
    ArrayList<Integer> saleList = new ArrayList<Integer>();

    // For excess report
    ArrayList<String> excessItemList = new ArrayList<String>();

    // Dates
    Date date_from;
    Date date_end;

    ///// Input datas /////
    JTextField fromDateInput = new JTextField();
    JTextField endDateInput = new JTextField();
    JLabel fromDateLabel = new JLabel("From: (YYYY-MM-DD)");
    JLabel endDateLabel = new JLabel("End: (YYYY-MM-DD)");

    Connection conn;

    public managerReportGUI() {
        conn = connectionSet();

        ////////// Frame Setting //////////
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ///// Sale report Layout /////
        itemNamePanel.setBounds((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.25),
                (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.8));
        salePanel.setBounds((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.25),
                (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.8));

        itemNamePanel.setLayout(new GridLayout(30, 1, 10, 10));
        salePanel.setLayout(new GridLayout(30, 1, 10, 10));

        title_item_Label.setVerticalAlignment(JLabel.TOP);
        title_item_Label.setHorizontalAlignment(JLabel.CENTER);

        title_sale_Label.setVerticalAlignment(JLabel.TOP);
        title_sale_Label.setHorizontalAlignment(JLabel.CENTER);

        itemNamePanel.add(title_item_Label);
        salePanel.add(title_sale_Label);

        f.add(itemNamePanel);
        f.add(salePanel);

        itemNamePanel.setVisible(false);
        salePanel.setVisible(false);

        ///// Excess Report Layout /////
        excessPanel.setBounds((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.25),
                (int) (screenSize.width * 0.6), (int) (screenSize.height * 0.8));

        excessPanel.setLayout(new GridLayout(30, 1, 10, 10));
        excessPanel.setVisible(false);
        f.add(excessPanel);

        ///// Button /////
        saleBtn.setBounds((int) (screenSize.width * 0.4), (int) (screenSize.height * 0.2),
                (int) (screenSize.width * 0.1), (int) (screenSize.height * 0.05));
        excessBtn.setBounds((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.2),
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

    public void salePanelDisplay(boolean b) {
        itemNamePanel.setVisible(b);
        salePanel.setVisible(b);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saleBtn) {
            salePanelDisplay(true);
            excessPanel.setVisible(false);
            for (int i = 0; i < itemNameList.size(); ++i) {
                JLabel newItemLabel = new JLabel(itemNameList.get(i));
                newItemLabel.setVerticalAlignment(JLabel.TOP);
                newItemLabel.setHorizontalAlignment(JLabel.CENTER);
                itemNamePanel.add(newItemLabel);

                JLabel newSaleLabel = new JLabel(String.valueOf(saleList.get(i)));
                newSaleLabel.setVerticalAlignment(JLabel.TOP);
                newSaleLabel.setHorizontalAlignment(JLabel.CENTER);
                salePanel.add(newSaleLabel);
            }
        } else if (e.getSource() == excessBtn) {
            excessPanel.setVisible(true);
            salePanelDisplay(false);
            for (int i = 0; i < excessItemList.size(); ++i) {
                JLabel newSaleLabel = new JLabel(String.valueOf(excessItemList.get(i)));
                newSaleLabel.setVerticalAlignment(JLabel.TOP);
                newSaleLabel.setHorizontalAlignment(JLabel.CENTER);
                excessPanel.add(newSaleLabel);
            }
        } else if (e.getSource() == submitBtn) {
            itemNameList.clear();
            saleList.clear();
            excessItemList.clear();
            salePanelDisplay(false);
            excessPanel.setVisible(false);

            try {
                date_from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateInput.getText());
                date_end = new SimpleDateFormat("yyyy-MM-dd").parse(endDateInput.getText());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    // THIS FUNCTION WILL DETERMINE THE NUMBER OF MENU ITEMS
    public int get_menu_item_num(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM menucost");

        int count = 0;
        while (rs.next()) {
            count++;
        }

        return count;
    }

    // THIS FUNCTION WILL GET THE SALES FOR EACH MENU ITEM IN A GIVEN TIMEFRAME
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

    // THIS FUNCTION WILL RETURN ALL MENU ITEMS NAMES
    public ArrayList<String> get_sale_report_name(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT menuitem FROM menucost ORDER BY id ASC");

        ArrayList<String> temp = new ArrayList<String>();
        while (rs.next()) {
            temp.add(rs.getString("menuitem"));
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
            JOptionPane.showMessageDialog(null, "Database connection failed");
        }

        return conn;
    }

    public static void main(String[] args) {
        new managerReportGUI();
    }

}
