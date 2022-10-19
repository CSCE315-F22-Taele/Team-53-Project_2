# Pom & Honey Ordering System
This code is a project for CSCE-331 (Introduction to Software Engineering). This project is creating a POS system for Pom & Honey at the MSC at Texas A&M that will cater towards both a cashier and manager side. Cashiers are able to login, create orders and submit checkouts. Managers are able to track and update inventory and menu items and view sale and excess reports in a given time period, along with having cashier functionalities.

##### Group 53: Preksha Vaghela, Victoria Pham, Annie Ren, Hexin Hu

---
##### To run:
- Clone this repo locally
- Go into the gui directory and compile with `javac *.java`
- To run on Windows: `java -cp ".;postgresql-42.2.8.jar" loginGUI`
- To run on Mac: `java -cp ".:postgresql-42.2.8.jar" loginGUI`
---
##### Features
- db: This includes all the scripts to populate the database.
- gui: This includes all the frontend & backend work.

##### Functionalities
###### Cashier:
- Log in and out of an employee account.
- Create orders, view total recepit and check out a customers in real-time.

##### Manager:
- Include all cashier functionalities.
- Insert, update or activate/deactive menu items currently sold.
- Insert, update or activate/deactive inventory items.
- View sale report of all menu items in a given time frame.
- View excess report of all inventory items sold below a 10% threshold in a given time frame.
