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
                throw new IllegalArgumentException("Invalid numeric value");

            this.value = value;
            this.unit = unit;
        }

        public double toFeet() {
            return unit.toFeet(value);
        }

        // 🔥 Instance conversion method
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseFeet = this.toFeet();
            double convertedValue = targetUnit.fromFeet(baseFeet);

            return new QuantityLength(convertedValue, targetUnit);
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

    // 🔥 Static conversion API
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        double baseFeet = source.toFeet(value);
        return target.fromFeet(baseFeet);
    }

    // 🔥 Overloaded methods (for viva)
    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return convert(value, from, to);
    }

    public static double demonstrateLengthConversion(QuantityLength quantity,
                                                     LengthUnit to) {
        return quantity.convertTo(to).value;
    }

    // Demo
    public static void main(String[] args) {

        System.out.println("1 ft → inches: " +
                convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("3 yards → feet: " +
                convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 inches → yards: " +
                convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

        System.out.println("1 cm → inches: " +
                convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));
    }
}