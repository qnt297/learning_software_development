/**
 * 図形の抽象親クラス。
 */
public abstract class Shape {
    private final String name;

    protected Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double area();

    public void printInfo() {
        System.out.println(name + " の面積: " + area());
    }
}
