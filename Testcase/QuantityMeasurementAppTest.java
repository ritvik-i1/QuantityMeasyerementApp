import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    @Test
    void testEquality_KgToKg() {
        var a = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_KgToGram() {
        var a = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.QuantityWeight(1000.0, WeightUnit.GRAM);

        assertTrue(a.equals(b));
    }

    @Test
    void testConversion_KgToPound() {
        var a = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var result = a.convertTo(WeightUnit.POUND);

        assertEquals(2.20462, result.getValue(), 1e-3);
    }

    @Test
    void testConversion_GramToKg() {
        var a = new QuantityMeasurementApp.QuantityWeight(1000.0, WeightUnit.GRAM);
        var result = a.convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_KgPlusGram() {
        var a = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.QuantityWeight(1000.0, WeightUnit.GRAM);

        var result = QuantityMeasurementApp.QuantityWeight.add(a, b, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_TargetGram() {
        var a = new QuantityMeasurementApp.QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.QuantityWeight(1000.0, WeightUnit.GRAM);

        var result = QuantityMeasurementApp.QuantityWeight.add(a, b, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), EPS);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityWeight(1.0, null));
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityWeight(Double.NaN, WeightUnit.KILOGRAM));
    }
}