/** 重量課金の送料。 */
public class WeightShipping implements ShippingFeePolicy {
    private final int feePer500g;

    public WeightShipping(int feePer500g) {
        this.feePer500g = feePer500g;
    }

    @Override
    public int calculate(int weightGram) {
        int units = (int) Math.ceil(weightGram / 500.0);
        return units * feePer500g;
    }
}
