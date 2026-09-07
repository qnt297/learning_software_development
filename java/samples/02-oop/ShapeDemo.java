/**
 * 継承とポリモーフィズムのデモ。
 */
public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle(2.0),
                new Rectangle(3.0, 4.0)
        };

        for (Shape shape : shapes) {
            shape.printInfo();
        }
    }
}
