import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testEquality() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testConversion() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(1000.0,
                v.convertTo(VolumeUnit.MILLILITRE).getValue(), EPS);
    }

    @Test
    void testAddition() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(2.0,
                v1.add(v2).getValue(), EPS);
    }

    @Test
    void testSubtraction() {
        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        assertEquals(4.5,
                v1.subtract(v2).getValue(), EPS);
    }

    @Test
    void testDivision() {
        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(5.0, VolumeUnit.LITRE);

        assertEquals(2.0,
                v1.divide(v2), EPS);
    }

    @Test
    void testDivideByZero() {
        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.0, VolumeUnit.LITRE);

        assertThrows(ArithmeticException.class, () -> v1.divide(v2));
    }
}