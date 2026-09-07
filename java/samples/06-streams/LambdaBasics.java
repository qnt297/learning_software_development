import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ラムダ式とメソッド参照の基本。
 */
public class LambdaBasics {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Ada");
        names.add("Alan");
        names.add("Grace");

        Consumer<String> printer = name -> System.out.println("Hello, " + name);
        names.forEach(printer);

        System.out.println("--- メソッド参照 ---");
        names.forEach(System.out::println);

        Predicate<String> startsWithA = s -> s.startsWith("A");
        names.stream()
                .filter(startsWithA)
                .forEach(n -> System.out.println("Aで始まる: " + n));
    }
}
