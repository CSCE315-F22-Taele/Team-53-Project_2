import javax.swing.*;
import java.sql.*;
import java.sql.DriverManager;


/**
 * Implements the database connection
 */
public class dbConnect {

    /**
     * Variable to create connection to database.
     */
    public Connection conn =  connectionSet();

    /**
     * Creates connection with database
     * @return Connection to Pom and Honey database.
     */
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
}
