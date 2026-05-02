import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // ---------------- EQUALS ----------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    // ---------------- CONVERSION ----------------
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit null");

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(result, targetUnit);
    }

    // ---------------- ADDITION ----------------
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Other null");

        double base = unit.convertToBaseUnit(value)
                + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(result, targetUnit);
    }

    // ---------------- SUBTRACTION ----------------
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Other null");

        double base = unit.convertToBaseUnit(value)
                - other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(result, targetUnit);
    }

    // ---------------- DIVISION ----------------
    public double divide(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Other null");

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        if (base2 == 0)
            throw new ArithmeticException("Divide by zero");

        return base1 / base2;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}