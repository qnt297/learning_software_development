-- 03 aggregation / subquery exercises (answers)

-- Q1
SELECT cat.name AS category_name, COUNT(*) AS product_count
FROM products p
JOIN categories cat ON cat.id = p.category_id
GROUP BY cat.name
ORDER BY product_count DESC;

-- Q2
SELECT oi.order_id, SUM(oi.quantity * oi.unit_price) AS total_amount
FROM order_items oi
GROUP BY oi.order_id
HAVING SUM(oi.quantity * oi.unit_price) >= 4000
ORDER BY total_amount DESC;

-- Q3
SELECT name, unit_price
FROM products
WHERE unit_price > (SELECT AVG(unit_price) FROM products)
ORDER BY unit_price DESC;

-- Q4
SELECT name
FROM products
WHERE unit_price = (SELECT MAX(unit_price) FROM products);
