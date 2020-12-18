import model.AkciyaStep;
import org.apache.kafka.streams.state.KeyValueStore;

import java.math.BigDecimal;

public class CheckState {
    public static AkciyaStep checkState(String key,
                                        AkciyaStep akciyaStep,
                                        KeyValueStore<String, AkciyaStep> state,
                                        String merchant) {
        if (merchant.equals("Cafe&Restaurant")) {
            state.put(key, akciyaStep);
            System.out.printf("Put %s : %s\n", key, akciyaStep.toString());
            if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(3000)) >= 0
                    && akciyaStep.getStepId() == 0) {
                akciyaStep.setStepId(1);
                state.put(key, akciyaStep);
                return akciyaStep;
            }
        }
        return null;
    }
}
