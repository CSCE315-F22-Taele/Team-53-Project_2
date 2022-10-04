import java.sql.*;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class jdbcpostgreSQL {

  // Return a randomized order in string format
  public static String getOrderEntity(int orderId, Time time, float amount, int orderedgyro, int orderedbowl,
      int orderedpitahummus, int orderedfalafel, int orderedprotein, int ordereddressing, int ordereddrink,
      Integer[] inventory) {

    String values = "VALUES ('";
    orderId = 220904002; // TODO: Determime how to update orderId
    time = new Time(2211696000000L); // TODO: Determine how to update time
    orderedgyro = getRandomValue(0, 1);
    orderedbowl = getRandomValue(0, 1);
    orderedpitahummus = getRandomValue(0, 1);
    orderedfalafel = getRandomValue(0, 1);
    orderedprotein = getRandomValue(0, 1);
    ordereddressing = getRandomValue(0, 2);
    ordereddrink = getRandomValue(0, 2);
    amount = getAmount(orderedgyro, orderedbowl, orderedpitahummus, orderedfalafel, orderedprotein, ordereddressing,
        ordereddrink);
    inventory = getInventory(orderedgyro, orderedbowl, orderedpitahummus,
        orderedfalafel, orderedprotein, ordereddressing, ordereddrink);

    values = values + orderId + "', '" + time + "', '" + amount + "', '" + orderedgyro + "', '" + orderedbowl + "', '"
        + orderedpitahummus + "', '" + orderedfalafel + "', '" + orderedprotein + "', '" + ordereddressing + "', '"
        + ordereddrink + "', '";

    String inventoryArray = "(";
    for (int i = 0; i < inventory.length; i++) {
      inventoryArray += inventory[i];
      if (i < inventory.length - 1) {
        inventoryArray += ',';
      }
    }
    inventoryArray += ')';

    values += inventoryArray + "');";
    return values;
  }

  public static int getRandomValue(int Min, int Max) {
    return ThreadLocalRandom
        .current()
        .nextInt(Min, Max + 1);
  }

  public static float getAmount(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {
    double ordertotal = (8.09 * orderedgyro) +
        (8.09 * orderedbowl) +
        (3.49 * orderedpitahummus) +
        (3.49 * orderedfalafel) +
        (1.99 * orderedprotein) +
        (0.39 * ordereddressing) +
        (2.45 * ordereddrink);
    return (float) ordertotal;
  }

  public static Integer[] getInventory(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {
    Integer[] inventory = new Integer[24];

    if (orderedgyro > 0) {
      inventory[16] = orderedgyro;
    }

    if (orderedbowl > 0) {
      inventory[19] = orderedbowl;
    }

    int bowlOrGyro = orderedgyro + orderedbowl;
    inventory[4] = 0;
    inventory[5] = 0;
    inventory[6] = 0;
    inventory[7] = 0;
    inventory[8] = 0;
    inventory[9] = 0;
    inventory[10] = 0;
    inventory[11] = 0;
    inventory[12] = 0;
    while (bowlOrGyro > 0) {
      // Rice
      if (inventory[0] == null) {
        inventory[0] = 0;
      }
      inventory[0] += getRandomValue(1, 3);

      // Protein (chicken or sp meatball)
      int curProtein = getRandomValue(1, 2);
      switch (curProtein) {
        case 1:
          if (inventory[1] == null) {
            inventory[1] = 0;
          }
          inventory[1] += 1;
          break;
        case 2:
          if (inventory[2] == null) {
            inventory[2] = 0;
          }
          inventory[2] += 1;
          break;
      }

      // Toppings - there's prob a better way to write this??
      inventory[4] += getRandomValue(0, 2);
      inventory[5] += getRandomValue(0, 2);
      inventory[6] += getRandomValue(0, 2);
      inventory[7] += getRandomValue(0, 2);
      inventory[8] += getRandomValue(0, 2);
      inventory[9] += getRandomValue(0, 2);
      inventory[10] += getRandomValue(0, 2);
      inventory[11] += getRandomValue(0, 2);
      inventory[12] += getRandomValue(0, 2);

      // Dressing
      int curDressing = getRandomValue(1, 3);
      switch (curDressing) {
        case 1: // Harissa
          if (inventory[13] == null) {
            inventory[13] = 0;
          }
          inventory[13] += 1;
          break;
        case 2: // Tzatziki sauce
          if (inventory[14] == null) {
            inventory[14] = 0;
          }
          inventory[14] += 1;
          break;
        case 3: // balsamic vinegar
          if (inventory[15] == null) {
            inventory[15] = 0;
          }
          inventory[15] += 1;
          break;
      }

      bowlOrGyro--;
    }

    // return topping values to null, if not included in order
    for (int i = 4; i < 12; i++) {
      if (inventory[i] == 0) {
        inventory[i] = null;
      }
    }

    if (orderedpitahummus > 0) {
      if (inventory[16] == null) { // pita
        inventory[16] = orderedpitahummus;
      } else {
        inventory[16] = orderedpitahummus;
      }
      inventory[17] = orderedpitahummus; // hummus
    }

    if (orderedfalafel > 0) {
      inventory[3] = orderedfalafel * 2;
    }

    while (orderedprotein > 0) {
      int curProtein = getRandomValue(1, 2);
      switch (curProtein) {
        case 1: // chicken
          if (inventory[1] == null) {
            inventory[1] = 0;
          }
          inventory[1] += 1;
          break;
        case 2: // spicy meatball
          if (inventory[2] == null) {
            inventory[2] = 0;
          }
          inventory[2] += 1;
          break;
      }
      orderedprotein--;
    }

    while (ordereddressing > 0) {
      int curDressing = getRandomValue(1, 3);
      switch (curDressing) {
        case 1: // harissa
          if (inventory[13] == null) {
            inventory[13] = 0;
          }
          inventory[13] += 1;
          break;
        case 2: // tzatziki sauce
          if (inventory[14] == null) {
            inventory[14] = 0;
          }
          inventory[14] += 1;
          break;
        case 3: // balsamic vinegar
          if (inventory[15] == null) {
            inventory[15] = 0;
          }
          inventory[15] += 1;
          break;
      }
      ordereddressing--;
    }

    if (ordereddrink > 0) {
      inventory[20] = ordereddrink; // cup
      inventory[21] = ordereddrink; // lid
      inventory[22] = ordereddrink; // straw
    }

    inventory[23] = getRandomValue(1, 10); // Napkins

    return inventory;
  }

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

      // the starting variables for the data to insert it into the array
      int orderId = 220904004;
      Time time = new Time(2211696000000L);
      float amount = 0;
      int checkoutid = 4; // we want to change this so that itll autoincrement.
      int orderedgyro = 0;
      int orderedbowl = 0;
      int orderedpitahummus = 0;
      int orderedfalafel = 0;
      int orderedprotein = 0;
      int ordereddressing = 0;
      int ordereddrink = 0;
      Integer[] inventory = new Integer[25];

      // TODO: loop through for different quantities, inventory items, times. order
      // ids and amounts throughout the day. RANDOMIZE
      // LINE 270-295: For better understanding of how randomizing works. DELETE!
      orderedgyro = getRandomValue(0, 1);
      orderedbowl = getRandomValue(0, 1);
      orderedpitahummus = getRandomValue(0, 1);
      orderedfalafel = getRandomValue(0, 1);
      orderedprotein = getRandomValue(0, 1);
      ordereddressing = getRandomValue(0, 1);
      ordereddrink = getRandomValue(1, 2);
      amount = getAmount(orderedgyro, orderedbowl, orderedpitahummus, orderedfalafel,
          orderedprotein, ordereddressing, ordereddrink);
      inventory = getInventory(orderedgyro, orderedbowl, orderedpitahummus,
          orderedfalafel, orderedprotein, ordereddressing, ordereddrink);

      System.out.println("Ordered Gyro:" + orderedgyro);
      System.out.println("Ordered Bowl:" + orderedbowl);
      System.out.println("Ordered pita & hummus:" + orderedpitahummus);
      System.out.println("Ordered 2 Falafel:" + orderedfalafel);
      System.out.println("Ordered Protein:" + orderedprotein);
      System.out.println("Ordered Dressing:" + ordereddressing);
      System.out.println("Ordered Drink:" + ordereddrink);
      System.out.println(amount);
      for (int i = 0; i < inventory.length; i++) {
        System.out.print(inventory[i] + ",");
      }
      System.out.println();
      System.out.println();

      // LINE 270-295 placed into getOrderEntity function
      String oneOrder = getOrderEntity(orderId, time, amount, orderedgyro, orderedbowl,
          orderedpitahummus, orderedfalafel, orderedprotein, ordereddressing, ordereddrink, inventory);

      System.out.println(oneOrder);

      // need to insert into checkout to generate checkoutid foreign key before
      // ordering insert
      int paymentmethod = 0;
      String cardnumber = "";
      int employeeid = 0;

      paymentmethod = getRandomValue(0, 2);
      int uin = 0;
      // Determine a card
      switch (paymentmethod) {
        case 0: // meal swipes --> uin
          uin = getRandomValue(100000000, 900000000);
          cardnumber = String.format("%d", uin);
          break;
        case 1: // dining dollars
          uin = getRandomValue(100000000, 900000000);
          cardnumber = String.format("%d", uin);
          break;
        case 2:
          Random rd = new Random();
          cardnumber = Long.toString(rd.nextLong() / 1000);
          break;
      }
      System.out.println("");
      // checkoutid, paymentmethod, amount, cardnumber, employeeid

      PreparedStatement checkoutStatement = conn
          .prepareStatement("INSERT INTO checkout( checkoutid, amount) VALUES (?, ?)");
      checkoutStatement.setInt(1, checkoutid);
      checkoutStatement.setFloat(2, amount);

      checkoutStatement.executeUpdate();

      // now we are able to insert into ordering without errors
      PreparedStatement statement = conn.prepareStatement(
          "INSERT INTO ordering(orderid , timeoforder , amount , checkoutid , orderedgyro , orderedbowl , orderedpitahummus , orderedfalafel , orderedprotein , ordereddressing , ordereddrink , inventoryused ) VALUES (?,?,?, ?, ?, ?,?,?, ?, ?,?, ?)");

      // transform java data into proper SQL variables
      Array inventoryused = conn.createArrayOf("INT", inventory);
      statement.setInt(1, orderId);
      statement.setTime(2, time);
      statement.setFloat(3, amount);
      statement.setInt(4, checkoutid);
      statement.setInt(5, orderedgyro);
      statement.setInt(6, orderedbowl);
      statement.setInt(7, orderedpitahummus);
      statement.setInt(8, orderedfalafel);
      statement.setInt(9, orderedprotein);
      statement.setInt(10, ordereddressing);
      statement.setInt(11, ordereddrink);
      statement.setArray(12, inventoryused);

      statement.executeUpdate();

      // OUTPUT

      System.out.println("--------------------Query Results--------------------");

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
