/**
 * 基本型・参照型・演算のサンプル。
 */
public class VariablesAndTypes {
    public static void main(String[] args) {
        int quantity = 3;
        double unitPrice = 1200.5;
        boolean inStock = true;
        String productName = "ノートPC";

        double subtotal = quantity * unitPrice;
        double tax = subtotal * 0.1;
        double total = subtotal + tax;

        System.out.println("商品名: " + productName);
        System.out.println("在庫あり: " + inStock);
        System.out.println("小計: " + subtotal);
        System.out.println("税額: " + tax);
        System.out.println("合計: " + total);

        // 整数除算に注意
        System.out.println("5 / 2 (int) = " + (5 / 2));
        System.out.println("5 / 2.0 (double) = " + (5 / 2.0));
    }
}
