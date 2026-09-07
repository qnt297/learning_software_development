public class SlackNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[SLACK] " + message);
    }
}
