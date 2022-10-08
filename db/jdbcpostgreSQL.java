import java.sql.*;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class jdbcpostgreSQL {

  public static int getRandomValue(int Min, int Max) {
    // FIX ME: SEE IF WE CAN RANDOMIZE THIS SO THAT THE AMOUNT IS 0 50% OF THE TIME,
    // 1 30% AND MORE THAN 1 OTHER: 20%
    return ThreadLocalRandom
        .current()
        .nextInt(Min, Max + 1);
  }

  public static double getAmount(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {
    double ordertotal = (8.09 * orderedgyro) +
        (8.09 * orderedbowl) +
        (3.49 * orderedpitahummus) +
        (3.49 * orderedfalafel) +
        (1.99 * orderedprotein) +
        (0.39 * ordereddressing) +
        (2.45 * ordereddrink);
    return (double) ordertotal;
  }

  public static Integer[] getInventory(int orderedgyro, int orderedbowl, int orderedpitahummus, int orderedfalafel,
      int orderedprotein, int ordereddressing, int ordereddrink) {

    // FIX ME: MAKE THIS INTO 0 AS THE CONDITION. NOT NULL
    //
    Integer[] inventory = new Integer[24];

    for (int i = 0; i < inventory.length; i++) {
      inventory[i] = 0;
    }

    if (orderedgyro > 0) {
      inventory[16] = orderedgyro;
    }

    if (orderedbowl > 0) {
      inventory[19] = orderedbowl;
    }

    int bowlOrGyro = orderedgyro + orderedbowl;

    while (bowlOrGyro > 0) {
      // Rice
      inventory[0] += getRandomValue(1, 3);

      // Protein (chicken or sp meatball)
      int curProtein = getRandomValue(1, 2);
      switch (curProtein) {
        case 1:
          inventory[1] += 1;
          break;
        case 2:
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
          inventory[13] += 1;
          break;
        case 2: // Tzatziki sauce
          inventory[14] += 1;
          break;
        case 3: // balsamic vinegar
          inventory[15] += 1;
          break;
      }

      bowlOrGyro--;
    }

    if (orderedpitahummus > 0) {
      inventory[16] += orderedpitahummus; // pita
      inventory[17] = orderedpitahummus; // hummus
    }

    if (orderedfalafel > 0) {
      inventory[3] = orderedfalafel * 2;
    }

    while (orderedprotein > 0) {
      int curProtein = getRandomValue(1, 2);
      switch (curProtein) {
        case 1: // chicken
          inventory[1] += 1;
          break;
        case 2: // spicy meatball
          inventory[2] += 1;
          break;
      }
      orderedprotein--;
    }

    while (ordereddressing > 0) {
      int curDressing = getRandomValue(1, 3);
      switch (curDressing) {
        case 1: // harissa
          inventory[13] += 1;
          break;
        case 2: // tzatziki sauce
          inventory[14] += 1;
          break;
        case 3: // balsamic vinegar
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

  public static String getCardNumber(int paymentmethod) {
    int uin = 0;
    String cardnumber = "";
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

    return cardnumber;
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

      // CHANGE ME PER DATE.
      int[] orderIdArray = new int[] { 220904000, 220905000, 220906000, 220907000, 220908000, 220909000, 220910000,
          220911000, 220912000, 220913000, 220914000, 220915000, 220916000, 220917000,
          220918000, 220919000, 220920000, 220921000, 220922000, 220923000, 220924000, 220925000 };
      int[] numOrdersWantArray = new int[] { 81, 98, 113, 85, 124, 72, 242,
          102, 73, 96, 85, 94, 107, 256,
          89, 98, 111, 81, 78, 112, 98, 123 };

      for (int i = 0; i < numOrdersWantArray.length; i++) {
        if (i < 14) {
          numOrdersWantArray[i] += 30; // 30;
        } else {
          numOrdersWantArray[i] += 50; // 20+20+10
        }
      }

      int[] employeeidArray = new int[] { 0, 1, 2, 3, 4, 5, 1,
          2, 3, 4, 5, 0, 1, 2,
          3, 4, 5, 0, 0, 1, 2, 3 };

      int[] reorder = new int[24];

      for (int i = 0; i < reorder.length - 7; i++) {
        reorder[i] = 0;
      }

      // loops through all the 21 days/ 3 weeks
      for (int i = 0; i < orderIdArray.length - 7; i++) {

        int orderId = orderIdArray[i] + numOrdersWantArray[i];
        int numOrdersWant = numOrdersWantArray[i];
        int employeeid = employeeidArray[i];

        int orderCount = 0;
        // will change each iteration
        double amount = 0;
        int checkoutid = 0;
        int orderedgyro = 0;
        int orderedbowl = 0;
        int orderedpitahummus = 0;
        int orderedfalafel = 0;
        int orderedprotein = 0;
        int ordereddressing = 0;
        int ordereddrink = 0;
        Integer[] inventory = new Integer[24];

        long timeMS = 1662303600000L;
        for (int j = 0; j < reorder.length; j++) {

          if (reorder[j] == orderId) { // if the day where reorder comes

            PreparedStatement amountReorder = conn.prepareStatement("SELECT amount FROM inventory WHERE itemid=(?)");
            amountReorder.setInt(1, j);
            ResultSet ReorderInventory = amountReorder.executeQuery();

            while (ReorderInventory.next()) {
              int itemQuantity = ReorderInventory.getInt("amount") + 1000;
              PreparedStatement reorderInventory = conn
                  .prepareStatement("UPDATE inventory SET amount=(?) WHERE itemid=(?)");
              reorderInventory.setInt(1, itemQuantity);
              reorderInventory.setInt(2, j);

              reorderInventory.executeUpdate();
            }
            reorder[j] = 0;
            System.out.println("reordered");
          }
        }

        while (orderCount < numOrdersWantArray[i]) {

          orderId++;
          System.out.println(orderId);
          timeMS = timeMS + getRandomValue(5000, 480000);
          Time time = new Time(timeMS);
          orderCount += 1;
          orderedgyro = getRandomValue(0, 1);
          orderedbowl = getRandomValue(0, 1);

          orderedpitahummus = getRandomValue(0, 1);
          orderedfalafel = getRandomValue(0, 1);
          orderedprotein = getRandomValue(0, 1);
          ordereddressing = getRandomValue(0, 1);
          ordereddrink = getRandomValue(0, 2);
          amount = getAmount(orderedgyro, orderedbowl, orderedpitahummus, orderedfalafel,
              orderedprotein, ordereddressing, ordereddrink);

          inventory = getInventory(orderedgyro, orderedbowl, orderedpitahummus,
              orderedfalafel, orderedprotein, ordereddressing, ordereddrink);

          // need to insert into checkout to generate checkoutid foreign key before
          // Populate checkout attributes
          int paymentmethod = getRandomValue(0, 2);
          String cardnumber = getCardNumber(paymentmethod);
          while (cardnumber.charAt(0) == '-') {
            cardnumber = getCardNumber(paymentmethod);
          }

          PreparedStatement checkoutStatement = conn.prepareStatement(
              "INSERT INTO checkout(paymentmethod, amount, cardnumber, employeeid) VALUES (?, ?,?, ?)");
          // checkoutStatement.setInt(1, checkoutid)
          checkoutStatement.setDouble(2, amount);
          checkoutStatement.setInt(1, paymentmethod);
          checkoutStatement.setString(3, cardnumber);
          checkoutStatement.setInt(4, employeeid);

          checkoutStatement.executeUpdate();

          // get the checkoutid to use as foreign key
          String checkoutidStatement = "SELECT checkoutid FROM checkout WHERE checkoutid = (SELECT MAX(checkoutid) FROM checkout)";
          ResultSet checkoutResult = stmt.executeQuery(checkoutidStatement);
          while (checkoutResult.next()) {
            checkoutid = checkoutResult.getInt("checkoutid");
          }

          // now we are able to insert into ordering without errors
          PreparedStatement statement = conn.prepareStatement(
              "INSERT INTO ordering(orderid , timeoforder , amount , checkoutid , orderedgyro , orderedbowl , orderedpitahummus , orderedfalafel , orderedprotein , ordereddressing , ordereddrink , inventoryused ) VALUES (?,?,?, ?, ?, ?,?,?, ?, ?,?, ?)");

          // transform java data into proper SQL variables

          Array inventoryused = conn.createArrayOf("INT", inventory);
          statement.setInt(1, orderId);
          statement.setTime(2, time);
          statement.setDouble(3, amount);
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

          // Update inventory table for each order

          // look at the item and the amount
          ResultSet findInventory = stmt.executeQuery("SELECT itemid, amount FROM inventory");
          int tempInventoryArray = 0; // Increments inventory array

          while (findInventory.next()) {
            int itemQuantity = findInventory.getInt("amount");
            int curitemId = findInventory.getInt("itemid");
            itemQuantity = itemQuantity - inventory[tempInventoryArray];
            tempInventoryArray++;

            // update in inventory
            PreparedStatement updateInventory = conn
                .prepareStatement("UPDATE inventory SET amount=(?) WHERE itemid=(?)");
            updateInventory.setDouble(1, itemQuantity);
            updateInventory.setInt(2, curitemId);

            updateInventory.executeUpdate();
          }
          // OUTPUT
          System.out.println(orderCount);
        }

        // check if the amount is too little
        ResultSet checkInventory = stmt.executeQuery("SELECT itemid, amount FROM inventory");
        int curitemId = 0;
        int itemQuantity = 0;

        while (checkInventory.next()) {
          itemQuantity = checkInventory.getInt("amount");
          curitemId = checkInventory.getInt("itemid");
          System.out.println(itemQuantity);
          if (itemQuantity <= 400 && reorder[curitemId] == 0) {
            reorder[curitemId] = orderIdArray[i] + 2000; // get it to increment the quantity after 2 days
          }
        }

      }
      // System.out.println("--------------------Query Results--------------------");

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
