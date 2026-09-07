import java.util.Comparator;
import java.util.List;

/**
 * Stream パイプラインのデモ。
 */
public class StreamPipelineDemo {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("キーボード", 3500),
                new Product("マウス", 1800),
                new Product("USBハブ", 2200),
                new Product("シール", 300)
        );

        List<String> expensiveNames = products.stream()
                .filter(p -> p.price() >= 2000)
                .sorted(Comparator.comparingInt(Product::price).reversed())
                .map(Product::name)
                .toList();

        System.out.println("2000円以上: " + expensiveNames);

        int total = products.stream()
                .mapToInt(Product::price)
                .sum();
        System.out.println("合計金額: " + total);

        boolean hasCheapItem = products.stream()
                .anyMatch(p -> p.price() < 500);
        System.out.println("500円未満があるか: " + hasCheapItem);
    }

    record Product(String name, int price) {
    }
}
