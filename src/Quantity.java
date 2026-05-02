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

    // ================= ENUM =================
    private enum Operation {
        ADD {
            double apply(double a, double b) { return a + b; }
        },
        SUBTRACT {
            double apply(double a, double b) { return a - b; }
        },
        DIVIDE {
            double apply(double a, double b) {
                if (b == 0) throw new ArithmeticException("Divide by zero");
                return a / b;
            }
        };

        abstract double apply(double a, double b);
    }

    // ================= VALIDATION =================
    private void validate(Quantity<U> other, U targetUnit, boolean needTarget) {

        if (other == null)
            throw new IllegalArgumentException("Other cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Different measurement categories");

        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid other value");

        if (needTarget && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }

    // ================= CORE HELPER =================
    private double performBaseArithmetic(Quantity<U> other, Operation op) {

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return op.apply(base1, base2);
    }

    // ================= EQUALS =================
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

    // ================= CONVERSION =================
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit null");

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(result, targetUnit);
    }

    // ================= ADD =================
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validate(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, Operation.ADD);
        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(result, targetUnit);
    }

    // ================= SUBTRACT =================
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validate(other, targetUnit, true);

        double baseResult = performBaseArithmetic(other, Operation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(result, targetUnit);
    }

    // ================= DIVIDE =================
    public double divide(Quantity<U> other) {

        validate(other, null, false);

        return performBaseArithmetic(other, Operation.DIVIDE);
    }

    // ================= TOSTRING =================
    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}