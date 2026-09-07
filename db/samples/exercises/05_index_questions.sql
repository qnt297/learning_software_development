-- 05 index / EXPLAIN exercises (questions)

-- Q1. orders.customer_id での検索について EXPLAIN ANALYZE を実行し、
--     Index Scan / Seq Scan のどちらに近いか観察せよ。
--     SELECT * FROM orders WHERE customer_id = 1;

-- Q2. products.name に対する検索用インデックスを作成する DDL を書け。
--     （学習用。不要になったら DROP INDEX してよい）

-- Q3. （考察）stock_qty がほぼバラバラな列と、status のように値が少ない列では、
--     どちらに索引を貼る効果が出やすいか。理由をコメントで書け。
