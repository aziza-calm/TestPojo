import model.AkciyaStep;
import org.apache.kafka.streams.state.KeyValueStore;

import java.math.BigDecimal;

public class CheckState {
    public AkciyaStep checkState(String key, AkciyaStep akciyaStep, KeyValueStore<String, AkciyaStep> state) {
        state.put(key, akciyaStep);
        System.out.printf("Put %s : %s\n", key, akciyaStep.toString());
        if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(3000)) >= 0) {
            akciyaStep.setStepId(1);
            System.out.printf(String.format("Forwarded %s %s\n", key, akciyaStep.toString()));
            state.delete(key);
            return akciyaStep;
        }
        return null;
    }
}
