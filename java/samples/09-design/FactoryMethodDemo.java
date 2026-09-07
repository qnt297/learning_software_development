/**
 * Factory Method のデモ。
 */
public class FactoryMethodDemo {
    public static void main(String[] args) {
        Notifier email = NotifierFactory.create("email");
        Notifier slack = NotifierFactory.create("slack");

        email.send("注文を受け付けました");
        slack.send("デプロイが完了しました");
    }
}
