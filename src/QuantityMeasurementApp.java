public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        // Equality
        System.out.println("Equality: " + v1.equals(v2));

        // Conversion
        System.out.println("Convert: " + v1.convertTo(VolumeUnit.MILLILITRE));

        // Addition
        System.out.println("Add: " + v1.add(v2));

        // Subtraction
        System.out.println("Subtract: " + v1.subtract(v2));

        // Division
        System.out.println("Divide: " + v1.divide(v2));
    }
}