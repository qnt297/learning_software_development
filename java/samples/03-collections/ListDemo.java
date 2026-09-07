import java.util.ArrayList;
import java.util.List;

/**
 * List の基本操作。
 */
public class ListDemo {
    public static void main(String[] args) {
        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("Kotlin");
        languages.add("Java"); // 重複可

        System.out.println("件数: " + languages.size());
        System.out.println("先頭: " + languages.get(0));

        languages.remove("Kotlin");
        System.out.println("削除後: " + languages);

        for (String language : languages) {
            System.out.println("- " + language);
        }

        List<String> immutable = List.of("Go", "Rust");
        System.out.println("不変リスト: " + immutable);
    }
}
