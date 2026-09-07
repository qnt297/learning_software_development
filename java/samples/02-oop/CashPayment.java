public class CashPayment implements Payment {
    @Override
    public String methodName() {
        return "現金";
    }

    @Override
    public int pay(int amount) {
        System.out.println("現金で " + amount + " 円を受け取りました");
        return amount;
    }
}
