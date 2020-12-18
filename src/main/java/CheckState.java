import model.AkciyaStep;
import org.apache.kafka.streams.state.KeyValueStore;

import java.math.BigDecimal;

public class CheckState {
    public static AkciyaStep checkState(String key,
                                        AkciyaStep akciyaStep,
                                        KeyValueStore<String, AkciyaStep> state) {
        state.put(key, akciyaStep);
        System.out.printf("Put out if %s : %s\n", key, akciyaStep.toString());
        if (akciyaStep.getMerchant().equals("Cafe&Restaurant")) {
            if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(3000)) >= 0
                    && akciyaStep.getStepId() == 0) {
                akciyaStep.setStepId(1);
                state.put(key, akciyaStep);
                System.out.printf("Put %s : %s\n", key, akciyaStep.toString());
                return akciyaStep;
            }
        }
        if (akciyaStep.getMerchant().equals("E-Commerce")) {
            if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(5000)) >= 0
                    && akciyaStep.getStepId() == 1) {
                akciyaStep.setStepId(2);
                state.put(key, akciyaStep);
                System.out.printf("Put %s : %s\n", key, akciyaStep.toString());
                return akciyaStep;
            }
        }
        if (akciyaStep.getMerchant().equals("Supermarket")) {
            if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(3000)) >= 0
                    && akciyaStep.getStepId() == 2) {
                akciyaStep.setStepId(3);
                state.put(key, akciyaStep);
                System.out.printf("Put %s : %s\n", key, akciyaStep.toString());
                return akciyaStep;
            }
        }
        return null;
    }
}
