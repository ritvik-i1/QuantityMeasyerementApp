public class QuantityMeasurementApp {

    // Step 1: Extended Enum with more units (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),           // 1 inch = 1/12 feet
        YARDS(3.0),                 // 1 yard = 3 feet
        CENTIMETERS(0.393701 / 12); // 1 cm = 0.393701 inches → convert to feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Generic Quantity class (unchanged from UC3)
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }
    }

    // Demo
    public static void main(String[] args) {

        System.out.println("Yard to Feet:");
        System.out.println(new QuantityLength(1.0, LengthUnit.YARDS)
                .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        System.out.println("Yard to Inches:");
        System.out.println(new QuantityLength(1.0, LengthUnit.YARDS)
                .equals(new QuantityLength(36.0, LengthUnit.INCH)));

        System.out.println("Centimeter to Inches:");
        System.out.println(new QuantityLength(1.0, LengthUnit.CENTIMETERS)
                .equals(new QuantityLength(0.393701, LengthUnit.INCH)));
    }
}