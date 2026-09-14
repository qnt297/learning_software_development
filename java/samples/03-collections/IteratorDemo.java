import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Iterator と、レガシーな Enumeration のデモ。
 */
public class IteratorDemo {
    public static void main(String[] args) {
        iterateList();
        iterateArrayViaList();
        removeWhileIterating();
        iterateEnumeration();
    }

    static void iterateList() {
        System.out.println("=== Iterator (List) ===");
        List<String> names = new ArrayList<>();
        names.add("Ada");
        names.add("Alan");
        names.add("Grace");

        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    static void iterateArrayViaList() {
        System.out.println("=== 参照型配列を Iterator で辿る ===");
        String[] words = {"red", "green", "blue"};
        Iterator<String> it = Arrays.asList(words).iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    static void removeWhileIterating() {
        System.out.println("=== Iterator.remove ===");
        List<String> names = new ArrayList<>();
        names.add("Ada");
        names.add("Bob");
        names.add("Alan");

        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            if (it.next().startsWith("A")) {
                it.remove();
            }
        }
        System.out.println("残: " + names);
    }

    static void iterateEnumeration() {
        System.out.println("=== Enumeration (Vector / レガシー) ===");
        Vector<String> vector = new Vector<>();
        vector.add("legacy-1");
        vector.add("legacy-2");

        Enumeration<String> enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
    }
}
