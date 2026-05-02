import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    @Test
    void testLengthUnit_ConversionFactor() {
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPS);
        assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), EPS);
    }

    @Test
    void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0,
                LengthUnit.INCHES.convertToBaseUnit(12.0), EPS);
    }

    @Test
    void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0,
                LengthUnit.INCHES.convertFromBaseUnit(1.0), EPS);
    }

    @Test
    void testEquality_CrossUnit() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(12.0, LengthUnit.INCHES);

        assertTrue(a.equals(b));
    }

    // ✅ FIXED TEST
    @Test
    void testConvertTo() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        var result = a.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), EPS); // ✔ correct
    }

    @Test
    void testAddition_WithTargetUnit() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(12.0, LengthUnit.INCHES);

        var result = QuantityMeasurementApp.QuantityLength.add(a, b, LengthUnit.FEET);

        assertTrue(result.equals(
                new QuantityMeasurementApp.QuantityLength(2.0, LengthUnit.FEET)));
    }

    @Test
    void testAddition_TargetYards() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(12.0, LengthUnit.INCHES);

        var result = QuantityMeasurementApp.QuantityLength.add(a, b, LengthUnit.YARDS);

        assertEquals(2.0, result.toBaseUnit(), EPS);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityLength(1.0, null));
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityLength(Double.NaN, LengthUnit.FEET));
    }
}