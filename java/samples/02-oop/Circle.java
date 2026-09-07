public class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        super("円");
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
