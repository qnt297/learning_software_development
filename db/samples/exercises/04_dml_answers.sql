-- 04 DML exercises (answers)

-- Q1
INSERT INTO customers (name, email)
VALUES ('Dave', 'dave@example.com');

-- Q2
UPDATE products
SET stock_qty = 45
WHERE name = 'Notebook A5';

-- Q3
UPDATE orders
SET status = 'CANCELLED'
WHERE status = 'PENDING';

-- Q4
-- INSERT INTO products (category_id, name, unit_price, stock_qty)
-- VALUES (999, 'Broken Item', 100, 1);
-- → categories(id) への FOREIGN KEY 違反で失敗する（整合性が守られる）。
