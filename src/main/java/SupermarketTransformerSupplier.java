import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;

public class SupermarketTransformerSupplier implements TransformerSupplier<String, PojoJson, KeyValue<String, AkciyaStep>> {
    @Override
    public Transformer<String, PojoJson, KeyValue<String, AkciyaStep>> get() {
        return new SupermarketTransformer();
    }
}
