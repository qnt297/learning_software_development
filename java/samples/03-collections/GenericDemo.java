/**
 * GenericBox の利用例。
 */
public class GenericDemo {
    public static void main(String[] args) {
        GenericBox<String> messageBox = new GenericBox<>();
        messageBox.set("型安全な箱");
        System.out.println(messageBox.get());

        GenericBox<Integer> numberBox = new GenericBox<>();
        numberBox.set(42);
        System.out.println("値: " + numberBox.get() + ", empty=" + numberBox.isEmpty());
    }
}
