/**
 * Factory Method 風に、チャネル名から Notifier を生成する。
 */
public final class NotifierFactory {
    private NotifierFactory() {
    }

    public static Notifier create(String channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel is required");
        }
        return switch (channel.toLowerCase()) {
            case "email" -> new EmailNotifier();
            case "slack" -> new SlackNotifier();
            default -> throw new IllegalArgumentException("unknown channel: " + channel);
        };
    }
}
