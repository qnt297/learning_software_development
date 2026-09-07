/**
 * 条件分岐とループのサンプル。
 */
public class ControlFlow {
    public static void main(String[] args) {
        int score = 82;
        if (score >= 80) {
            System.out.println("判定: 優");
        } else if (score >= 60) {
            System.out.println("判定: 良");
        } else {
            System.out.println("判定: 不可");
        }

        System.out.println("--- 偶数のみ ---");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 != 0) {
                continue;
            }
            System.out.println(i);
        }

        String grade = "B";
        String message = switch (grade) {
            case "A" -> "素晴らしい";
            case "B" -> "よくできました";
            case "C" -> "もう少し";
            default -> "不明な評価";
        };
        System.out.println("評価コメント: " + message);
    }
}
