/**
 * Decorator のデモ。責務をラップで重ねる。
 */
public class DecoratorDemo {
    public static void main(String[] args) {
        Notifier notifier = new EmailNotifier();
        notifier = new PrefixDecorator(notifier, "[INFO] ");
        notifier = new UpperCaseDecorator(notifier);

        notifier.send("hello decorator");
        // 出力例: [EMAIL] [INFO] HELLO DECORATOR
        // UpperCase が外側 → 先に大文字化 → Prefix → Email
    }
}
