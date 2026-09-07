/**
 * PriceCalculator のユニットテスト。
 */
public class PriceCalculatorTest {
    private final PriceCalculator calculator = new PriceCalculator();

    public void withTax_addsTenPercent() {
        Assert.assertEquals(1100, calculator.withTax(1000, 0.1));
    }

    public void applyDiscount_reducesPrice() {
        Assert.assertEquals(800, calculator.applyDiscount(1000, 0.2));
    }

    public void withTax_rejectsNegativePrice() {
        try {
            calculator.withTax(-1, 0.1);
            throw new AssertionError("exception expected");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue(expected.getMessage().contains("price"), "message should mention price");
        }
    }
}
