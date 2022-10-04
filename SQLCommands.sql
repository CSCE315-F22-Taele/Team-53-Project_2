/* total sales */

SELECT 
    SUM(amount) total sales,
FROM
    checkout;


/* number of orders */

SELECT
    COUNT(orderid),
FROM
    ordering;


/* cost of inventory */
SELECT 
    itemname,
    amount * cost AS total cost
FROM 
    inventory;


/* min, max, avg salaries */
SELECT 
    Max(salary), 
    Min(salary), 
    AVG(salary) 
FROM employee;


/* order inventory by expiration */
SELECT itemname FROM inventory ORDER BY expirationdate;


/* group inventory by vendor */

SELECT itemname FROM inventory, GROUP BY vendor


/* see managerID and employeeID */

SELECT CONCAT(employeeid, managerid) as bothids
FROM employee;


/* see employees working more than 15 hours */

SELECT employeeid FROM employee 
WHERE hoursworked > 15;


/* group employees by manager */

SELECT employeename FROM employee, GROUP BY managerid


/* see sales made by each employee */

SELECT employeeid, 
SUM (amount) 
FROM checkout
GROUP BY employeeid;


/* see average amount per sale */

SELECT AVG(amount) FROM checkout;


/* total menu items ordered */

SELECT 
    SUM(orderedgyro),
    SUM(orderedbowl),
    SUM(orderedpitahummus),
    SUM(orderedfalafel),
    SUM(orderedprotein),
    SUM(ordereddressing),
    SUM(ordereddrink)
FROM
    ordering;


/* count number of managers */

SELECT COUNT(managerid) FROM manager;


/* order inventory by most amount */

SELECT * FROM inventory ORDER BY amount desc;


/* display managers identification  */

SELECT * FROM manager;


