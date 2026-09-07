/**
 * 簡易テストランナー。
 * 実行: javac *.java && java TestRunner
 */
public class TestRunner {
    public static void main(String[] args) {
        PriceCalculatorTest test = new PriceCalculatorTest();
        int passed = 0;
        int failed = 0;

        failed += run("withTax_addsTenPercent", test::withTax_addsTenPercent);
        failed += run("applyDiscount_reducesPrice", test::applyDiscount_reducesPrice);
        failed += run("withTax_rejectsNegativePrice", test::withTax_rejectsNegativePrice);

        passed = 3 - failed;
        System.out.println("----");
        System.out.println("passed=" + passed + ", failed=" + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static int run(String name, Runnable testCase) {
        try {
            testCase.run();
            System.out.println("[PASS] " + name);
            return 0;
        } catch (AssertionError | RuntimeException e) {
            System.out.println("[FAIL] " + name + " -> " + e.getMessage());
            return 1;
        }
    }
}
