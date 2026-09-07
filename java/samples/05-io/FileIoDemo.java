import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Files API による読み書き。
 */
public class FileIoDemo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("out");
        Files.createDirectories(dir);

        Path file = dir.resolve("memo.txt");
        String content = "Java I/O のサンプルです。\n二行目です。\n";
        Files.writeString(file, content, StandardCharsets.UTF_8);
        System.out.println("書き込み完了: " + file.toAbsolutePath());

        String loaded = Files.readString(file, StandardCharsets.UTF_8);
        System.out.println("--- 読み込み結果 ---");
        System.out.print(loaded);
    }
}
