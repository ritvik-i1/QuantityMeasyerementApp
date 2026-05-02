import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    // ---------------- EQUALITY ----------------

    @Test
    void testEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_GallonToLitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(3.78541, VolumeUnit.LITRE);

        assertTrue(v1.equals(v2));
    }

    // ---------------- CONVERSION ----------------

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);

        // ✅ FIX: use getValue()
        assertEquals(1000.0,
                v.convertTo(VolumeUnit.MILLILITRE).getValue(),
                EPS);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.GALLON);

        assertEquals(3.78541,
                v.convertTo(VolumeUnit.LITRE).getValue(),
                1e-4);
    }

    // ---------------- ADDITION ----------------

    @Test
    void testAddition_LitrePlusMillilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = v1.add(v2, VolumeUnit.LITRE);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_GallonPlusLitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = v1.add(v2, VolumeUnit.GALLON);

        assertEquals(2.0, result.getValue(), 1e-4);
    }

    // ---------------- EDGE CASES ----------------

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testZeroValue() {
        Quantity<VolumeUnit> v = new Quantity<>(0.0, VolumeUnit.LITRE);

        assertEquals(0.0, v.getValue(), EPS);
    }
}