package punctuator;

import model.AkciyaStep;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.math.BigDecimal;

public class CafePunctuator implements Punctuator {
    private KeyValueStore<String, AkciyaStep> store;
    private ProcessorContext context;

    public CafePunctuator(KeyValueStore<String, AkciyaStep> store, ProcessorContext context) {
        this.store = store;
        this.context = context;
    }

    @Override
    public void punctuate(long l) {
        KeyValueIterator<String, AkciyaStep> performanceIterator = store.all();

        while (performanceIterator.hasNext()) {
            KeyValue<String, AkciyaStep> keyValue = performanceIterator.next();
            String key = keyValue.key;
            AkciyaStep akciyaStep = keyValue.value;

            if (akciyaStep != null) {
                if (akciyaStep.getAntRur().compareTo(BigDecimal.valueOf(3000)) >= 0) {
                    akciyaStep.setStepId(1);
                    context.forward(key, akciyaStep);
                    System.out.printf(String.format("Forwarded %s %s\n", key, akciyaStep.toString()));
                    store.delete(key);
                }
            }
        }
    }
}
