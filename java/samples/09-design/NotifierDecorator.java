/**
 * Decorator の共通ベース（委譲先を保持）。
 */
public abstract class NotifierDecorator implements Notifier {
    private final Notifier delegate;

    protected NotifierDecorator(Notifier delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String message) {
        delegate.send(message);
    }
}
