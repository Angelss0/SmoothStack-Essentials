package JavaBasics_2;

public class Triangle implements Shape {
    private int _base;
    private int _height;
    private int _area = 0;

    public Triangle(int base, int height) {
        _base = base;
        _height = height;
    }

    public int calculateArea() {
        return _area = (int)(0.5f * _base * _height);
    }

    public void display() {
        System.out.println("Triangle Area: " + _area);
    }
}