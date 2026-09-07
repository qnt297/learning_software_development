-- 02 JOIN exercises (answers)

-- Q1
SELECT o.id AS order_id, c.name AS customer_name, o.ordered_at
FROM orders o
INNER JOIN customers c ON c.id = o.customer_id
ORDER BY o.ordered_at DESC;

-- Q2
SELECT p.name AS product_name, cat.name AS category_name
FROM products p
INNER JOIN categories cat ON cat.id = p.category_id
ORDER BY cat.name, p.name;

-- Q3
SELECT oi.order_id, p.name AS product_name, oi.quantity, oi.unit_price
FROM order_items oi
INNER JOIN products p ON p.id = oi.product_id
ORDER BY oi.order_id, p.name;

-- Q4
SELECT c.name AS customer_name, o.id AS order_id
FROM customers c
LEFT JOIN orders o ON o.customer_id = c.id
ORDER BY c.name, o.id;
