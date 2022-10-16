# Pom & Honey Ordering System
This code is a project for CSCE-331 (Introduction to Software Engineering). This project is creating a POS system for Pom & Honey at the MSC at Texas A&M that will cater towards both a cashier and manager side. Cashiers are able to login, create orders and submit checkouts. Managers are able to track and update inventory and menu items, along with having cashier functionalities.

##### Group 53: Preksha Vaghela, Victoria Pham, Annie Ren, Hexin Hu

##### Features
- db: This includes all the scripts to populate the database.
- gui: This includes all the frontend & backend for deliverable 3.

##### To run:
- Clone this repo locally
- Go into the gui directory and compile with `javac *.java`
- To run on Windows: `java -cp ".;postgresql-42.2.8.jar" loginGUI`
- To run on Mac: `java -cp ".:postgresql-42.2.8.jar" loginGUI`


##### __DATABASE DESIGN:__


__ORDERING:__
orderid INT
timeoforder DATE/TIME
amount DOUBLE
ordereditems ARRAY
BASED ON MENU ITEMS
Every value is the value of the item in menu cost where:  index = menu id-1
Whenever it is discontinued. It is not removed. Just don’t use that index. 
inventory ARRAY
BASED ON INVENTORY ITEMS
Every value is the value of the item in INVENTORY where:  index = id-1
Whenever it is discontinued. It is not removed. Just don’t use that index.


__CHECKOUT:__
checkoutid INT
auto increment
paymentmethod INT
Enum. one of the 4 options
0: meal swipes
1: dining dollars
2: card
3: cash
amount DOUBLE 
From the order
cardnumber STRING
Randomly generated (comes from payment system)
employeeid INT
Whoever is running the order
orderid INT (foreign key)
Comes from the order. 

__MENUCOST:__
menuitem STRING
name
cost  DOUBLE
Cost of item
id INT (AUTO INCREMENTING)
Id which corresponds to the index of the item in the ordereditems array in ORDERING
Is_selling BOOLEAN
Never deletes. If this is false. Will not show up in cashier and won’t be allowed to buy. Only when they reactivate it will we be able to see it. 

__INVENTORY:__
itemid INT (AUTO INCREMENTING)
Id which corresponds to the index of the item in the inventory array in ORDERING
itemname STRING  
Name
amount INT
Quantity  
cost  DOUBLE
Cost of item
expirationdate DATE
vendor  STRING
is_using BOOLEAN
Never deletes. If this is false. Will not show up in inventory per order and won’t be allowed to buy. Only when they reactivate it will we be able to see it. 

__EMPLOYEE:__
employeeid INT 
hoursworked INT
salary DOUBLE
employeename STRING
managerid INT
ismanager BOOLEAN
If this is true: SHOW THE MANAGER OPTIONS OF PAGES. 
