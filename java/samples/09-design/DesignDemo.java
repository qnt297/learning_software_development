/**
 * Strategy パターンのデモ。
 */
public class DesignDemo {
    public static void main(String[] args) {
        CheckoutService flat = new CheckoutService(new FlatShipping(500));
        CheckoutService weight = new CheckoutService(new WeightShipping(200));

        int itemPrice = 3000;
        int weightGram = 1200;

        System.out.println("一律送料の合計: " + flat.total(itemPrice, weightGram));
        System.out.println("重量送料の合計: " + weight.total(itemPrice, weightGram));
    }
}
