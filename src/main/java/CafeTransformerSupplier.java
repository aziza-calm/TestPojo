import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;

public class CafeTransformerSupplier implements TransformerSupplier {
    @Override
    public Transformer get() {
        return new CafeTransformer();
    }
}
