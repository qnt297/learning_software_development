/**
 * 送料計算の戦略。
 */
public interface ShippingFeePolicy {
    int calculate(int weightGram);
}
