package db;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import gui.dbSetup;

import java.nio.file.*;
import java.text.SimpleDateFormat;

public class inventoryPopulate {

  // Commands to run this script
  // This will compile all java files in this directory
  // javac *.java
  // This command tells the file where to find the postgres jar which it needs to
  // execute postgres commands, then executes the code
  // Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  // Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  // MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE
  public static void main(String args[]) {

    // Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "53";
    String sectionNumber = "904";
    String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup myCredentials = new dbSetup();

    // Connecting to the database
    try {
      conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    System.out.println("Opened database successfully");

    try {
      // create a statement object
      Statement stmt = conn.createStatement();

      String sqlStatement = "UPDATE inventory SET cost = ? WHERE itemid = ?";
      PreparedStatement statement = conn.prepareStatement(sqlStatement);
      int itemId = 0;
      String itemName;
      double amount;
      double cost;
      Date expirationData;
      String vendor;

      List<String> lines = Files.readAllLines(Paths.get("inventory_Data.csv"));
      for (String line : lines) {
        String[] elements = line.split(",");
        itemId = Integer.parseInt(elements[0]);
        // itemName = elements[1];
        amount = Double.parseDouble(elements[2]);
        cost = Double.parseDouble(elements[3]);
        vendor = elements[5];

        statement.setInt(2, itemId);
        // statement.setString(2, itemName);
        // statement.setDouble(1, amount);
        statement.setDouble(1, cost);
        // statement.setString(1, vendor);
        statement.executeUpdate();
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

    // closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch (Exception e) {
      System.out.println("Connection NOT Closed.");
    } // end try catch
  }// end main
}// end Class
