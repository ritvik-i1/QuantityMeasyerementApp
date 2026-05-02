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

        // 🔥 UC6: Addition (instance method)
        public QuantityLength add(QuantityLength other) {
            if (other == null)
                throw new IllegalArgumentException("Other quantity cannot be null");

            // Convert both to base unit
            double sumFeet = this.toFeet() + other.toFeet();

            // Convert back to THIS unit (first operand rule)
            double resultValue = unit.fromFeet(sumFeet);

            return new QuantityLength(resultValue, this.unit);
        }

        // 🔥 Static version (flexible API)
        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
            return q1.add(q2);
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

        QuantityLength f = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength i = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("1 ft + 12 in = " + f.add(i));

        QuantityLength y = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength f2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("1 yard + 3 ft = " + y.add(f2));

        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("2.54 cm + 1 in = " + cm.add(inch));
    }
}