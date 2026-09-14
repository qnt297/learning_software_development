/**
 * 送料計算の戦略（メソッドが1つなのでラムダでも実装できる）。
 */
@FunctionalInterface
public interface ShippingFeePolicy {
    int calculate(int weightGram);
}
