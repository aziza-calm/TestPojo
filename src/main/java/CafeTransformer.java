import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class CafeTransformer implements Transformer<String, PojoJson, KeyValue> {
    private KeyValueStore<String, AkciyaStep> state;

    @Override
    public void init(ProcessorContext processorContext) {
        state = (KeyValueStore<String, AkciyaStep>) processorContext.getStateStore("akciya-steps");
//        processorContext.schedule(Duration.ofMinutes(1), PunctuationType.WALL_CLOCK_TIME, new CafePunctuator(this.state, processorContext));
    }

    @Override
    public KeyValue transform(String key, PojoJson pojoJson) {
        AkciyaStep akciyaStep = state.get(key);
        if (akciyaStep != null) System.out.printf("Got from state %s\n", akciyaStep.toString());
        CheckState checker = new CheckState();
        if (akciyaStep == null) {
            akciyaStep = new AkciyaStep(pojoJson);
            akciyaStep = checker.checkState(key, akciyaStep, state);
        }
        else {
            akciyaStep.updateAntRur(pojoJson.getReqAmt());
            System.out.printf("Updated %s %s added %s\n", key, akciyaStep.toString(), pojoJson.getReqAmt().toString());
            akciyaStep = checker.checkState(key, akciyaStep, state);
        }
        if (akciyaStep == null) return null;
        return new KeyValue(key, akciyaStep);
    }

    @Override
    public void close() {

    }
}
