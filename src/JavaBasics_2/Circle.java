package JavaBasics_2;

public class Circle implements Shape {
    private int _radius;
    private int _area;

    public Circle(int radius) {
        _radius = radius;
    }

    public int calculateArea() {
        return _area = (int)(_radius * _radius * Math.PI);
    }

    public void display() {
        System.out.println("Circle Area: " + _area);
    }
}