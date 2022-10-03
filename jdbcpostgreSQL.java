import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.sql.Time;



public class jdbcpostgreSQL {

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  //MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE
  public static void main(String args[]) {

    //Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "53";
    String sectionNumber = "904";
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup myCredentials = new dbSetup(); 

    //Connecting to the database
    try {
        conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }

     System.out.println("Opened database successfully");

     try{
       //create a statement object
       Statement stmt = conn.createStatement();


       //Running a query
      
      //the starting variables for the data to insert it into the array
       int orderId = 220904003;
       Time time = new Time(2211696000000L);
       float amount = 0;
       int checkoutid = 3;
       int orderedgyro = 0;
       int orderedbowl =0;
       int orderedpitahummus= 0;
       int orderedfalafel=0;
       int orderedprotein = 0;
       int ordereddressing = 0;
       int ordereddrink =0;
       Integer [] inventory = new Integer[25]; 
       


       //String sqlStatement = "INSERT INTO teammembers (name, phonenumber, email) VALUES(" + str(name) + ")";

       PreparedStatement checkoutStatement = conn.prepareStatement("INSERT INTO checkout( checkoutid, amount) VALUES (?, ?)");
       checkoutStatement.setInt(1, checkoutid);
       //checkoutStatement.setInt(2, 1);
       checkoutStatement.setFloat(2, amount);

       checkoutStatement.executeUpdate();

       PreparedStatement statement = conn.prepareStatement("INSERT INTO ordering(orderid , timeoforder , amount , checkoutid , orderedgyro , orderedbowl , orderedpitahummus , orderedfalafel , orderedprotein , ordereddressing , ordereddrink , inventoryused ) VALUES (?,?,?, ?, ?, ?,?,?, ?, ?,?, ?)");
       //send statement to DBMS
       //This executeQuery command is useful for data retrieval

       Array inventoryused = conn.createArrayOf("INT", inventory);
       statement.setInt(1, orderId); 
       statement.setTime(2,time );
       statement.setFloat(3, amount);
       statement.setInt( 4, checkoutid);
       statement.setInt(5, orderedgyro);
       statement.setInt(6, orderedbowl); 
       statement.setInt(7, orderedpitahummus);
       statement.setInt(8, orderedfalafel); 
       statement.setInt(9, orderedprotein);
       statement.setInt(10, ordereddressing);
       statement.setInt(11 , ordereddrink);
       statement.setArray(12 , inventoryused);

       statement.executeUpdate();
       //OR
       //This executeUpdate command is useful for updating data
       //int result = stmt.executeUpdate(sqlStatement);

       //OUTPUT
       //You will need to output the results differently depeninding on which function you use
       System.out.println("--------------------Query Results--------------------");
       //while (result.next()) {
       //System.out.println(result.getString("column_name"));
       //}
       //OR
       //System.out.println(result);
   } catch (Exception e){
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }//end try catch
  }//end main
}//end Class
