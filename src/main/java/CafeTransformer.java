import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class CafeTransformer implements Transformer<String, PojoJson, AkciyaStep> {
    private KeyValueStore<String, AkciyaStep> state;

    @Override
    public void init(ProcessorContext processorContext) {
        state = (KeyValueStore<String, AkciyaStep>) processorContext.getStateStore("akciya-steps");
        processorContext.schedule(Duration.ofMinutes(1), PunctuationType.WALL_CLOCK_TIME, new CafePunctuator(this.state, processorContext));
    }

    @Override
    public AkciyaStep transform(String key, PojoJson pojoJson) {
        AkciyaStep akciyaStep = state.get(key);
        if (akciyaStep == null) {
            state.put(key, new AkciyaStep(pojoJson));
            System.out.printf("Put %s : %s\n", key, new AkciyaStep(pojoJson).toString());
        }
        else {
            akciyaStep.updateAntRur(pojoJson.getReqAmt());
            state.put(key, akciyaStep);
            System.out.printf("Updated %s %s\n", key, akciyaStep.toString());
        }
        return null;
    }

    @Override
    public void close() {

    }
}
