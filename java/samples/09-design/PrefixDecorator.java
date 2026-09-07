public class PrefixDecorator extends NotifierDecorator {
    private final String prefix;

    public PrefixDecorator(Notifier delegate, String prefix) {
        super(delegate);
        this.prefix = prefix;
    }

    @Override
    public void send(String message) {
        super.send(prefix + message);
    }
}
