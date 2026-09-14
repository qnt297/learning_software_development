/**
 * if / for / while / switch / break / continue の記法サンプル。
 */
public class ControlFlow {
    public static void main(String[] args) {
        demoIf();
        demoFor();
        demoWhile();
        demoSwitchStatement();
        demoSwitchExpression();
        demoBreakAndContinue();
        demoLabeledBreak();
    }

    static void demoIf() {
        System.out.println("=== if / else if / else ===");
        int score = 82;

        if (score >= 80) {
            System.out.println("判定: 優");
        } else if (score >= 60) {
            System.out.println("判定: 良");
        } else {
            System.out.println("判定: 不可");
        }

        boolean passing = score >= 60;
        if (passing) {
            System.out.println("合格です");
        }
    }

    static void demoFor() {
        System.out.println("=== for（カウンタ） ===");
        for (int i = 1; i <= 3; i++) {
            System.out.println("i=" + i);
        }

        System.out.println("=== 拡張 for（配列） ===");
        int[] numbers = {10, 20, 30};
        for (int number : numbers) {
            System.out.println("number=" + number);
        }
    }

    static void demoWhile() {
        System.out.println("=== while ===");
        int n = 3;
        while (n > 0) {
            System.out.println("n=" + n);
            n--;
        }

        System.out.println("=== do-while（最低1回は実行） ===");
        int m = 0;
        do {
            System.out.println("m=" + m);
            m++;
        } while (m < 2);
    }

    static void demoSwitchStatement() {
        System.out.println("=== switch 文（伝統的・break 必須） ===");
        int day = 2;
        switch (day) {
            case 1:
                System.out.println("月");
                break;
            case 2:
                System.out.println("火");
                break;
            case 3:
                System.out.println("水");
                break;
            default:
                System.out.println("その他");
                break;
        }
        // break を忘れると次の case へ落ちる（フォールスルー）。レガシーコードでよく見る。
    }

    static void demoSwitchExpression() {
        System.out.println("=== switch 式（Java 14+） ===");
        String grade = "B";
        String message = switch (grade) {
            case "A" -> "素晴らしい";
            case "B" -> "よくできました";
            case "C" -> "もう少し";
            default -> "不明な評価";
        };
        System.out.println("評価コメント: " + message);
    }

    static void demoBreakAndContinue() {
        System.out.println("=== continue（偶数だけ表示） ===");
        for (int i = 1; i <= 6; i++) {
            if (i % 2 != 0) {
                continue; // 以降をスキップして次の周へ
            }
            System.out.println(i);
        }

        System.out.println("=== break（5 で打ち切り） ===");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                break; // ループ自体を終了
            }
            System.out.println(i);
        }
    }

    static void demoLabeledBreak() {
        System.out.println("=== ラベル付き break（二重ループをまとめて抜ける） ===");
        outer:
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (row == 1 && col == 1) {
                    break outer;
                }
                System.out.println("row=" + row + ", col=" + col);
            }
        }
    }
}
