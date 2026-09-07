-- 06 transaction exercises (answers)

-- Q1
BEGIN;

WITH new_customer AS (
    INSERT INTO customers (name, email)
    VALUES ('Eve', 'eve@example.com')
    RETURNING id
)
INSERT INTO orders (customer_id, status)
SELECT id, 'PENDING' FROM new_customer;

ROLLBACK;

-- 確認: Eve が居ないこと
-- SELECT * FROM customers WHERE email = 'eve@example.com';

-- Q2
BEGIN;

WITH new_customer AS (
    INSERT INTO customers (name, email)
    VALUES ('Eve', 'eve@example.com')
    RETURNING id
)
INSERT INTO orders (customer_id, status)
SELECT id, 'PENDING' FROM new_customer;

COMMIT;

-- Q3
-- BEGIN〜COMMIT で複数 DML を原子的に扱うと、途中で失敗した場合に
-- 全体を ROLLBACK でき、親なし明細や明細なし注文のような不整合を防げる。
