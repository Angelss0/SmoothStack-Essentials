package JavaBasics_2;

public class assignment3 {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(5, 5);
        rect.calculateArea();
        rect.display();

        Triangle tri = new Triangle(5, 5);
        tri.calculateArea();
        tri.display();

        Circle circle = new Circle(5);
        circle.calculateArea();
        circle.display();
    }
}
