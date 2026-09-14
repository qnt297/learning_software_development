/**
 * 依存性注入（コンストラクタ注入）の対比デモ。
 *
 * 使う側（CheckoutService）は ShippingFeePolicy の具象を new しない。
 * どの実装を渡すかは main（外側）が決める。
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        int itemPrice = 3000;
        int weightGram = 1200;

        // 外側で部品を作り、サービスへ注入する
        CheckoutService withFlat = new CheckoutService(new FlatShipping(500));
        CheckoutService withWeight = new CheckoutService(new WeightShipping(200));
        CheckoutService withZeroFee = new CheckoutService(weightGramIgnored -> 0); // テスト用の偽物も注入できる

        System.out.println("一律送料: " + withFlat.total(itemPrice, weightGram));
        System.out.println("重量送料: " + withWeight.total(itemPrice, weightGram));
        System.out.println("テスト用ゼロ送料: " + withZeroFee.total(itemPrice, weightGram));
    }
}
