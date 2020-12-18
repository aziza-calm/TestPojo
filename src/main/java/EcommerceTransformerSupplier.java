import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;

public class EcommerceTransformerSupplier implements TransformerSupplier {
    @Override
    public Transformer get() {
        return new EcommerceTransformer();
    }
}
