/**
 * 送料ポリシーを注入して使うサービス（依存性逆転の入口）。
 */
public class CheckoutService {
    private final ShippingFeePolicy shippingFeePolicy;

    public CheckoutService(ShippingFeePolicy shippingFeePolicy) {
        this.shippingFeePolicy = shippingFeePolicy;
    }

    public int total(int itemPrice, int weightGram) {
        return itemPrice + shippingFeePolicy.calculate(weightGram);
    }
}
