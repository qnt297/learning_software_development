-- 07 view exercises (answers)

-- Q1
CREATE OR REPLACE VIEW order_summaries AS
SELECT
    o.id AS order_id,
    c.name AS customer_name,
    o.status,
    o.ordered_at
FROM orders o
JOIN customers c ON c.id = o.customer_id;

-- Q2
SELECT *
FROM order_summaries
WHERE status = 'PAID'
ORDER BY ordered_at;

-- Q3
CREATE OR REPLACE VIEW order_totals AS
SELECT
    oi.order_id,
    SUM(oi.quantity * oi.unit_price) AS total_amount
FROM order_items oi
GROUP BY oi.order_id;

-- 片付け例:
-- DROP VIEW IF EXISTS order_totals;
-- DROP VIEW IF EXISTS order_summaries;
