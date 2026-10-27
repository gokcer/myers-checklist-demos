// CO08 · Computation · Can a value go outside its meaningful range?
// Two discounts are added: 60% (campaign) + 50% (coupon) = 110% -> a negative price.
public class CO08MeaningfulRange {

    static double priceBuggy(double price, double campaign, double coupon) {
        double discount = campaign + coupon;                        // BUG: discount > 100% possible
        return Math.round(price * (1 - discount) * 100) / 100.0;
    }

    static double priceFixed(double price, double campaign, double coupon) {
        double discount = Math.min(campaign + coupon, 0.9);         // FIX: the spec's limit (here 90%)
        return Math.round(price * (1 - discount) * 100) / 100.0;
    }

    public static void main(String[] args) {
        System.out.println("CO08 Value outside its meaningful range (Java)   100 TL, 60% + 50%");
        System.out.println("  BUG -> " + priceBuggy(100, 0.60, 0.50));
        System.out.println("  FIX -> " + priceFixed(100, 0.60, 0.50));
    }
}
