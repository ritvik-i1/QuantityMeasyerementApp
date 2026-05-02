public class QuantityMeasurementApp {

    public static class Quantity<U extends IMeasurable> {

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

        public double toBaseUnit() {
            return unit.convertToBaseUnit(value);
        }

        public Quantity<U> convertTo(U targetUnit) {
            double base = toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity<>(converted, targetUnit);
        }

        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double sum = this.toBaseUnit() + other.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sum);
            return new Quantity<>(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            // ❗ prevent cross-category comparison
            if (!this.unit.getClass().equals(other.unit.getClass()))
                return false;

            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 1e-6;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return value + " " + unit.getUnitName();
        }
    }

    // 🔥 MAIN DEMO
    public static void main(String[] args) {

        // Length
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println("Length Equal: " + l1.equals(l2));
        System.out.println("Convert: " + l1.convertTo(LengthUnit.INCHES));
        System.out.println("Add: " + l1.add(l2, LengthUnit.FEET));

        // Weight
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("Weight Equal: " + w1.equals(w2));
        System.out.println("Convert: " + w1.convertTo(WeightUnit.GRAM));
        System.out.println("Add: " + w1.add(w2, WeightUnit.KILOGRAM));
    }
}