public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // -------- VOLUME (UC11) --------
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        // Equality
        System.out.println("1L == 1000mL: " + v1.equals(v2));
        System.out.println("1 Gallon == 3.78541L: " +
                v3.equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));

        // Conversion
        System.out.println("1L to mL: " + v1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println("1 Gallon to L: " + v3.convertTo(VolumeUnit.LITRE));

        // Addition
        System.out.println("1L + 1000mL (L): " +
                v1.add(v2, VolumeUnit.LITRE));

        System.out.println("1L + 1000mL (mL): " +
                v1.add(v2, VolumeUnit.MILLILITRE));

        System.out.println("1 Gallon + 3.78541L (Gallon): " +
                v3.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON));
    }
}