package JavaBasics_2;

public class Rectangle implements Shape {
    private int _w;
    private int _h;
    private int _area;

    public Rectangle(int w, int h) {
        _w = w;
        _h = h;
    }

    public int calculateArea() {
        return _area = _h *_w;
    }

    public void display() {
        System.out.println("Rectangle Area: " + _area);
    }
}