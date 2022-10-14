# Pom & Honey Ordering System
This code is a project for CSCE-331 (Introduction to Software Engineering). This project is creating a POS system for Pom & Honey at the MSC at Texas A&M that will cater towards both a cashier and manager side. Cashiers are able to login, create orders and submit checkouts. Managers are able to track and update inventory and menu items, along with having cashier functionalities.

##### Group 53: Preksha Vagehla, Victoria Pham, Annie Ren, Hexin Hu

##### Features
- db: This includes all the scripts to populate the database.
- gui: This includes all the frontend & backend for deliverable 3.

##### To run:
- Clone this repo locally
- Go into the gui directory and compile with `javac *.java`
- To run on Windows: `java -cp ".;postgresql-42.2.8.jar" loginGUI`
- To run on Mac: `java -cp ".:postgresql-42.2.8.jar" loginGUI`
