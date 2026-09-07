-- 01 SELECT exercises (answers)

-- Q1
SELECT id, name, email
FROM customers
ORDER BY id;

-- Q2
SELECT name, unit_price
FROM products
WHERE unit_price >= 3000
ORDER BY unit_price DESC;

-- Q3
SELECT name
FROM customers
WHERE email LIKE '%@example.com';

-- Q4
SELECT name, stock_qty
FROM products
WHERE stock_qty < 10
ORDER BY stock_qty ASC
LIMIT 3;

-- Q5
SELECT COUNT(*) AS paid_order_count
FROM orders
WHERE status = 'PAID';
