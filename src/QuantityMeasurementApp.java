public class QuantityMeasurementApp {

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
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

        public WeightUnit getUnit() {
            return unit;
        }

        public double toBaseUnit() {
            return unit.convertToBaseUnit(value); // kg
        }

        public QuantityWeight convertTo(WeightUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = this.toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);

            return new QuantityWeight(converted, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            return add(this, other, this.unit);
        }

        public static QuantityWeight add(QuantityWeight q1,
                                         QuantityWeight q2,
                                         WeightUnit targetUnit) {

            if (q1 == null || q2 == null)
                throw new IllegalArgumentException("Operands cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumBase = q1.toBaseUnit() + q2.toBaseUnit();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new QuantityWeight(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
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

    // 🔥 MAIN METHOD (UC9 DEMO)
    public static void main(String[] args) {

        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + kg.equals(gram));

        System.out.println("Convert 1kg → gram: " +
                kg.convertTo(WeightUnit.GRAM));

        System.out.println("Add (kg target): " +
                QuantityWeight.add(kg, gram, WeightUnit.KILOGRAM));

        System.out.println("Add (gram target): " +
                QuantityWeight.add(kg, gram, WeightUnit.GRAM));
    }
}