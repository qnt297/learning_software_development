-- 05 index / EXPLAIN exercises (answers)

-- Q1
EXPLAIN ANALYZE
SELECT * FROM orders WHERE customer_id = 1;
-- seed 程度の行数では Seq Scan になることもある。行数が増えると索引が選ばれやすい。

-- Q2
CREATE INDEX idx_products_name ON products (name);

-- 片付け例:
-- DROP INDEX IF EXISTS idx_products_name;

-- Q3
-- 一般に、選択性（カーディナリティ）が高い列の方が索引の効果が出やすい。
-- status のように値が数種類しか無い列は、条件によってはほとんど全件に近くなり、
-- Seq Scan の方が速いと判断されることが多い。
