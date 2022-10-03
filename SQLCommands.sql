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