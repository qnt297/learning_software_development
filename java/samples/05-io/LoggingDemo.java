import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * System.out と java.util.logging の使い分けデモ。
 *
 * - ユーザー向け表示: System.out
 * - 運用・調査向け: Logger
 */
public class LoggingDemo {
    private static final Logger logger = Logger.getLogger(LoggingDemo.class.getName());

    public static void main(String[] args) {
        System.out.println("=== タスク処理を開始します ==="); // ユーザー向け

        logger.info("アプリケーション起動");
        process("report.csv");

        try {
            process(null);
        } catch (IllegalArgumentException e) {
            // ユーザー向けは短く、ログには詳細とスタックトレースを残す
            System.err.println("エラー: 入力が不正です");
            logger.log(Level.SEVERE, "処理に失敗しました", e);
        }

        logger.info("アプリケーション終了");
        System.out.println("=== 終了 ===");
    }

    static void process(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("fileName must not be blank");
        }
        logger.fine("詳細: fileName=" + fileName); // デフォルト設定では出ないことが多い
        logger.info("ファイルを処理します: " + fileName);
        System.out.println("処理中: " + fileName); // ユーザー向け進捗
    }
}
