import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class EcommerceTransformer implements Transformer<String, PojoJson, KeyValue> {
    private KeyValueStore<String, AkciyaStep> state;

    @Override
    public void init(ProcessorContext processorContext) {
        state = (KeyValueStore<String, AkciyaStep>) processorContext.getStateStore("akciya-steps");
    }

    @Override
    public KeyValue transform(String key, PojoJson pojoJson) {
        AkciyaStep akciyaStep = state.get(key);
        if (akciyaStep != null) {
            if (akciyaStep.getStepId() == 1 && akciyaStep.getMerchant().equals("Cafe&Restaurant")) {
                akciyaStep.setMerchant("E-Commerce");
                akciyaStep.setAntRur(pojoJson.getReqAmt());
            }
            else if (akciyaStep.getStepId() == 1 && akciyaStep.getMerchant().equals("E-Commerce"))
                akciyaStep.updateAntRur(pojoJson.getReqAmt());
            akciyaStep = CheckState.checkState(key, akciyaStep, state);
        }
        if (akciyaStep == null) return null;
        return new KeyValue(key, akciyaStep);
    }

    @Override
    public void close() {

    }
}
