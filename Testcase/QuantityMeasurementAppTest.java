import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    @Test
    void testLengthEquality() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(a.equals(b));
    }

    @Test
    void testWeightEquality() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(a.equals(b));
    }

    @Test
    void testLengthConversion() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, LengthUnit.FEET);
        var result = a.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), EPS);
    }

    @Test
    void testWeightConversion() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, WeightUnit.KILOGRAM);
        var result = a.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testLengthAddition() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Quantity<>(12.0, LengthUnit.INCHES);

        var result = a.add(b, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testWeightAddition() {
        var a = new QuantityMeasurementApp.Quantity<>(1.0, WeightUnit.KILOGRAM);
        var b = new QuantityMeasurementApp.Quantity<>(1000.0, WeightUnit.GRAM);

        var result = a.add(b, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testCrossCategory_NotEqual() {
        var length = new QuantityMeasurementApp.Quantity<>(1.0, LengthUnit.FEET);
        var weight = new QuantityMeasurementApp.Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }
}