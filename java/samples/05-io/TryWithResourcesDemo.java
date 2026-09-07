import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * try-with-resources のデモ。
 */
public class TryWithResourcesDemo {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("out", "memo.txt");
        if (!Files.exists(file)) {
            Files.createDirectories(file.getParent());
            Files.writeString(file, "alpha\nbeta\ngamma\n", StandardCharsets.UTF_8);
        }

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
        }
    }
}
