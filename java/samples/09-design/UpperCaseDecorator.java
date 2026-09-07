public class UpperCaseDecorator extends NotifierDecorator {
    public UpperCaseDecorator(Notifier delegate) {
        super(delegate);
    }

    @Override
    public void send(String message) {
        super.send(message.toUpperCase());
    }
}
