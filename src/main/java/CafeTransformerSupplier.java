import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;

public class CafeTransformerSupplier implements ValueTransformerSupplier {
    @Override
    public ValueTransformer get() {
        return new CafeTransformer();
    }
}
