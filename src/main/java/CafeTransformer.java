import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class CafeTransformer implements ValueTransformer<PojoJson, AkciyaStep> {
    private KeyValueStore<String, AkciyaStep> state;

    @Override
    public void init(ProcessorContext processorContext) {
        this.state = (KeyValueStore<String, AkciyaStep>) processorContext.getStateStore("akciya-steps");
        processorContext.schedule(Duration.ofMinutes(1), PunctuationType.WALL_CLOCK_TIME, new CafePunctuator(this.state, processorContext));
    }

    @Override
    public AkciyaStep transform(PojoJson pojoJson) {
        AkciyaStep akciyaStep = new AkciyaStep(pojoJson);
        this.state.putIfAbsent(akciyaStep.getClientPin(), akciyaStep);
        this.state.get(akciyaStep.getClientPin()).setAntRur(pojoJson);
        return null;
    }

    @Override
    public void close() {

    }
}
