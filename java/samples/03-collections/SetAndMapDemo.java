import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Set と Map の使い分け。
 */
public class SetAndMapDemo {
    public static void main(String[] args) {
        Set<String> tags = new HashSet<>();
        tags.add("java");
        tags.add("backend");
        tags.add("java"); // 無視される
        System.out.println("タグ: " + tags);

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 90);
        scores.put("Bob", 75);
        scores.put("Carol", 88);

        System.out.println("Bobの点数: " + scores.get("Bob"));
        System.out.println("Daveがいるか: " + scores.containsKey("Dave"));

        int sum = 0;
        for (int score : scores.values()) {
            sum += score;
        }
        double average = (double) sum / scores.size();
        System.out.println("平均点: " + average);
    }
}
