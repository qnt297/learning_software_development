/**
 * 税込価格を計算するドメインロジック（テスト対象）。
 */
public class PriceCalculator {
    public int withTax(int priceExcludingTax, double taxRate) {
        if (priceExcludingTax < 0) {
            throw new IllegalArgumentException("price must be >= 0");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("taxRate must be >= 0");
        }
        return (int) Math.round(priceExcludingTax * (1 + taxRate));
    }

    public int applyDiscount(int price, double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("discountRate must be between 0 and 1");
        }
        return (int) Math.round(price * (1 - discountRate));
    }
}
