public class QuantityMeasurementApp {

    // Base unit = FEET
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

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

        public double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 UC6 (existing)
        public QuantityLength add(QuantityLength other) {
            return add(this, other, this.unit);
        }

        // 🔥 UC7 (NEW: explicit target unit)
        public static QuantityLength add(QuantityLength q1,
                                         QuantityLength q2,
                                         LengthUnit targetUnit) {

            if (q1 == null || q2 == null)
                throw new IllegalArgumentException("Operands cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            // Convert both to base unit
            double sumFeet = q1.toFeet() + q2.toFeet();

            // Convert to target unit
            double resultValue = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toFeet() - other.toFeet()) < 1e-6;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Demo
    public static void main(String[] args) {

        var f = new QuantityLength(1.0, LengthUnit.FEET);
        var i = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Feet target: " +
                QuantityLength.add(f, i, LengthUnit.FEET));

        System.out.println("Inches target: " +
                QuantityLength.add(f, i, LengthUnit.INCHES));

        System.out.println("Yards target: " +
                QuantityLength.add(f, i, LengthUnit.YARDS));

        var cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        var inch = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("CM target: " +
                QuantityLength.add(cm, inch, LengthUnit.CENTIMETERS));
    }
}