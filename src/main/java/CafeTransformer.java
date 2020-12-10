import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

public class CafeTransformer implements Transformer {

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public Object transform(Object o, Object o2) {
        return null;
    }

    @Override
    public void close() {

    }
}
