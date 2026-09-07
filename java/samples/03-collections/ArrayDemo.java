/**
 * 一次元配列・多次元配列と、安全な要素取り出しの例。
 */
public class ArrayDemo {
    public static void main(String[] args) {
        int[] scores = {80, 90, 75};
        System.out.println("一次元: length=" + scores.length);
        for (int i = 0; i < scores.length; i++) {
            System.out.println("scores[" + i + "]=" + scores[i]);
        }

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("--- 二次元（矩形） ---");
        printMatrix(matrix);

        int[][] jagged = new int[3][];
        jagged[0] = new int[] {1};
        jagged[1] = new int[] {2, 3};
        jagged[2] = new int[] {4, 5, 6};
        System.out.println("--- ジャグ配列 ---");
        printMatrix(jagged);

        System.out.println("matrix[1][2]=" + matrix[1][2]); // 6
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
