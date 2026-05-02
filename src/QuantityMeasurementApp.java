public class QuantityMeasurementApp {

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
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

        public LengthUnit getUnit() {
            return unit;
        }

        public double toBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = this.toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);

            return new QuantityLength(converted, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            return add(this, other, this.unit);
        }

        public static QuantityLength add(QuantityLength q1,
                                         QuantityLength q2,
                                         LengthUnit targetUnit) {

            if (q1 == null || q2 == null)
                throw new IllegalArgumentException("Operands cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 1e-6;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }
}