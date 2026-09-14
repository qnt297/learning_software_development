import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ラムダ構文が、匿名クラスのどの部分に対応し、どこで実行されるかのデモ。
 */
public class LambdaAnatomyDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Ada");
        names.add("Al");
        names.add("Grace");

        System.out.println("=== 匿名クラス（ラムダの展開形） ===");
        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println("accept が呼ばれた: " + name);
            }
        });

        System.out.println("=== 同じ処理のラムダ ===");
        names.forEach(name -> System.out.println("ラムダ本体: " + name));

        System.out.println("=== filter: Predicate.test 相当 ===");
        Predicate<String> longEnough = n -> {
            boolean ok = n.length() >= 3;
            System.out.println("test(" + n + ") -> " + ok);
            return ok;
        };
        names.stream()
                .filter(longEnough)
                .forEach(n -> System.out.println("残った: " + n));
    }
}
