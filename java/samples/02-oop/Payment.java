/**
 * 支払い手段の契約。
 */
public interface Payment {
    String methodName();

    /** @return 実際に支払った金額 */
    int pay(int amount);
}
