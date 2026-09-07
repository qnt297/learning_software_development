import java.util.Scanner;

/**
 * メソッド分割と標準入力のサンプル。
 */
public class MethodsAndInput {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("1つ目の整数: ");
            int a = scanner.nextInt();
            System.out.print("2つ目の整数: ");
            int b = scanner.nextInt();

            System.out.println("加算: " + add(a, b));
            System.out.println("大きい方: " + max(a, b));
            System.out.println("偶数か(" + a + "): " + isEven(a));
        }
    }

    static int add(int a, int b) {
        return a + b;
    }

    static int max(int a, int b) {
        return a >= b ? a : b;
    }

    static boolean isEven(int value) {
        return value % 2 == 0;
    }
}
