package serdes;

import com.google.gson.Gson;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonPOJOSerializer<T> implements Serializer<T> {
    private Gson gson = new Gson();

    /**
     * Default constructor needed by Kafka
     */
    public JsonPOJOSerializer() {
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, T t) {
        if (t == null)
            return null;

        try {
            return gson.toJson(t).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
    }

}
