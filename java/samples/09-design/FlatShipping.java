/** 一律送料。 */
public class FlatShipping implements ShippingFeePolicy {
    private final int fee;

    public FlatShipping(int fee) {
        this.fee = fee;
    }

    @Override
    public int calculate(int weightGram) {
        return fee;
    }
}
