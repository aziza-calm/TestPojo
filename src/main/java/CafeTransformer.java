import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class CafeTransformer implements Transformer<String, PojoJson, AkciyaStep> {
    private KeyValueStore<String, AkciyaStep> state;

    @Override
    public void init(ProcessorContext processorContext) {
        this.state = (KeyValueStore<String, AkciyaStep>) processorContext.getStateStore("akciya-steps");
        processorContext.schedule(Duration.ofMinutes(1), PunctuationType.WALL_CLOCK_TIME, new CafePunctuator(this.state, processorContext));
    }

    @Override
    public AkciyaStep transform(String key, PojoJson pojoJson) {
        Object check = state.putIfAbsent(key, new AkciyaStep(pojoJson));
        if (check != null) {
            AkciyaStep akciyaStep = state.get(key);
            akciyaStep.updateAntRur(pojoJson.getReqAmt());
            System.out.println("Updated!");
            state.put(key, akciyaStep);
        }
        return null;
    }

    @Override
    public void close() {

    }
}
