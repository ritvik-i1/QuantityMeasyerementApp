import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    @Test
    void testAddition_TargetFeet() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(QuantityMeasurementApp.QuantityLength.add(f, i,
                        QuantityMeasurementApp.LengthUnit.FEET)
                .equals(new QuantityMeasurementApp.QuantityLength(2.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    @Test
    void testAddition_TargetInches() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(QuantityMeasurementApp.QuantityLength.add(f, i,
                        QuantityMeasurementApp.LengthUnit.INCHES)
                .equals(new QuantityMeasurementApp.QuantityLength(24.0,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }

    @Test
    void testAddition_TargetYards() {
        var f = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var i = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        double result = QuantityMeasurementApp.QuantityLength.add(f, i,
                QuantityMeasurementApp.LengthUnit.YARDS).toFeet();

        assertEquals(2.0, result, EPS);
    }

    @Test
    void testAddition_Commutativity_WithTarget() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var r1 = QuantityMeasurementApp.QuantityLength.add(a, b,
                QuantityMeasurementApp.LengthUnit.YARDS);

        var r2 = QuantityMeasurementApp.QuantityLength.add(b, a,
                QuantityMeasurementApp.LengthUnit.YARDS);

        assertTrue(r1.equals(r2));
    }

    @Test
    void testAddition_TargetNull() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.QuantityLength.add(a, b, null));
    }

    @Test
    void testAddition_NegativeValues_TargetInches() {
        var a = new QuantityMeasurementApp.QuantityLength(5.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(-2.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var result = QuantityMeasurementApp.QuantityLength.add(a, b,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(result.equals(
                new QuantityMeasurementApp.QuantityLength(36.0,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }
}