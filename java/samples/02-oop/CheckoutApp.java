/**
 * インターフェイス経由で実装を切り替えるデモ。
 */
public class CheckoutApp {
    public static void main(String[] args) {
        checkout(new CashPayment(), 1500);
        checkout(new CardPayment("4242"), 3200);
    }

    static void checkout(Payment payment, int amount) {
        System.out.println("支払い方法: " + payment.methodName());
        payment.pay(amount);
        System.out.println("---");
    }
}
