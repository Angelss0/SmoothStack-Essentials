package JavaBasics_4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class LineTest {

    @Test()
    public void checkGetSlope() {
        // Arrange
        Line line0 = new Line(0, 0, 1, 1);
        Line line1 = new Line(0, 0, 2, 1);
        Line line2 = new Line(0, 0, 3, 1);
        Line line3 = new Line(0, 0, 4, 1);
        Line line4 = new Line(1, 2, 2, 3);
        Line line5 = new Line(1, 2, 2, 4);
        Line line6 = new Line(1, 2, 2, 5);
        Line line7 = new Line(1, 2, 2, 6);
        Line line8 = new Line(10, 11, 10, 12);

        // Assert
        assertEquals(1.00000, line0.getSlope(), 0.0001);
        assertEquals(0.50000, line1.getSlope(), 0.0001);
        assertEquals(0.33333, line2.getSlope(), 0.0001);
        assertEquals(0.25000, line3.getSlope(), 0.0001);
        assertEquals(1, line4.getSlope(), 0.0001);
        assertEquals(2, line5.getSlope(), 0.0001);
        assertEquals(3, line6.getSlope(), 0.0001);
        assertEquals(4, line7.getSlope(), 0.0001);

        try {
            line8.getSlope();
            fail();
        } catch (ArithmeticException e) {}
    }

    @Test
    public void checkGetDistance() {
        // Arrange
        Line line0 = new Line(0, 0, 1, 1);
        Line line1 = new Line(0, 0, 2, 1);
        Line line2 = new Line(0, 0, 3, 1);
        Line line3 = new Line(0, 0, 4, 1);
        Line line4 = new Line(1, 2, 2, 3);
        Line line5 = new Line(1, 2, 2, 4);
        Line line6 = new Line(1, 2, 2, 5);
        Line line7 = new Line(1, 2, 2, 6);
        Line line8 = new Line(10, 11, 10, 12);

        // Assert
        assertEquals(1.4142135623730950488016887242097, line0.getDistance(), 0.0001);
        assertEquals(2.2360679774997896964091736687313, line1.getDistance(), 0.0001);
        assertEquals(3.1622776601683793319988935444327, line2.getDistance(), 0.0001);
        assertEquals(4.1231056256176605498214098559741, line3.getDistance(), 0.0001);
        assertEquals(1.4142135623730950488016887242097, line4.getDistance(), 0.0001);
        assertEquals(2.2360679774997896964091736687313, line5.getDistance(), 0.0001);
        assertEquals(3.1622776601683793319988935444327, line6.getDistance(), 0.0001);
        assertEquals(4.1231056256176605498214098559741, line7.getDistance(), 0.0001);
        assertEquals(1.0, line8.getDistance(), 0.0001);
    }

    @Test
    public void checkParallelTo() {
        Line line0 = new Line(0, 0, 1, 1);
        Line line1 = new Line(1, 1, 2, 2);
        Line line2 = new Line(0, 0, 3, 1);

        assertTrue("The two lines should be parallel!", line0.parallelTo(line1));
        assertFalse("The two lines should not be parallel!", line1.parallelTo(line2));
    }
}
