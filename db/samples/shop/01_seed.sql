-- Seed data for learning_shop
-- Run after 00_schema.sql

TRUNCATE order_items, orders, products, categories, customers RESTART IDENTITY CASCADE;

INSERT INTO customers (name, email) VALUES
    ('Alice', 'alice@example.com'),
    ('Bob',   'bob@example.com'),
    ('Carol', 'carol@example.com');

INSERT INTO categories (name) VALUES
    ('Books'),
    ('Gadgets'),
    ('Stationery');

INSERT INTO products (category_id, name, unit_price, stock_qty) VALUES
    (1, 'Clean Code',           3300.00, 12),
    (1, 'Design Patterns',      4200.00,  8),
    (2, 'USB-C Hub',            2800.00, 20),
    (2, 'Mechanical Keyboard', 12800.00,  5),
    (3, 'Notebook A5',           450.00, 50),
    (3, 'Ballpoint Pen',         120.00, 200);

INSERT INTO orders (customer_id, status, ordered_at) VALUES
    (1, 'PAID',    '2026-01-10 10:00:00+09'),
    (1, 'SHIPPED', '2026-01-15 11:30:00+09'),
    (2, 'PENDING', '2026-02-01 09:00:00+09'),
    (3, 'PAID',    '2026-02-20 16:45:00+09');

INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
    (1, 1, 1, 3300.00),
    (1, 5, 2,  450.00),
    (2, 2, 1, 4200.00),
    (2, 6, 3,  120.00),
    (3, 3, 1, 2800.00),
    (4, 4, 1, 12800.00),
    (4, 5, 1,   450.00);
