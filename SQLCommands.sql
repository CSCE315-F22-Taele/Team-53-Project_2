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
SELECT * FROM inventory ORDER BY expirationdate;


/* group inventory by vendor */

