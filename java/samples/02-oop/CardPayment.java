public class CardPayment implements Payment {
    private final String lastFourDigits;

    public CardPayment(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    @Override
    public String methodName() {
        return "カード(****" + lastFourDigits + ")";
    }

    @Override
    public int pay(int amount) {
        System.out.println(methodName() + " で " + amount + " 円を決済しました");
        return amount;
    }
}
