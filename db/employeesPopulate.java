package db;

import java.sql.*;
import java.util.List;
import java.nio.file.*;

public class employeesPopulate {

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
      String sqlStatement = "INSERT INTO employee (employeeid, hoursWorked, salary,employeename) VALUES (?, ?, ?, ?)";
      PreparedStatement statement = conn.prepareStatement(sqlStatement);
      int employeeId = 0;
      double hoursWorked = 0;
      double salary = 0;
      int managerId;
      String employeeName;

      List<String> lines = Files.readAllLines(Paths.get("manage_employee data.csv"));
      for (String line : lines) {
        String[] elements = line.split(",");
        employeeId = Integer.parseInt(elements[0]);
        hoursWorked = Double.parseDouble(elements[1]);
        salary = Double.parseDouble(elements[2]);
        managerId = Integer.parseInt(elements[3]);
        employeeName = elements[4];

        statement.setInt(1, employeeId);
        statement.setDouble(2, hoursWorked);
        statement.setDouble(3, salary);
        statement.setString(4, employeeName);
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
