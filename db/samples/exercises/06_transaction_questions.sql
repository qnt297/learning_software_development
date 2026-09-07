-- 06 transaction exercises (questions)
-- psql では自動コミットが有効なことが多い。明示的に BEGIN すること。

-- Q1. 次を1トランザクションで行い、最後は ROLLBACK して結果が残らないことを確認せよ。
--     - 顧客 Eve を INSERT
--     - その顧客の注文を 1 件 INSERT（customer_id は currval や CTE で取得してよい）

-- Q2. 同様の操作を COMMIT する版を書け（確認後は seed 再実行で戻してよい）。

-- Q3. （考察）order_items だけ成功して orders が失敗する、という中途半端な状態を
--     トランザクションがどう防ぐか、自分の言葉でコメントせよ。
