import model.AkciyaStep;
import model.PojoJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import serdes.SerDeFactory;

import java.util.Properties;

public class ProblemSixApp {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-processor");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, PojoJson> source = builder.stream("source-topic", Consumed.with(Serdes.String(), SerDeFactory.getPOJOSerde(PojoJson.class)));

        // сначала фильтруем
        KStream<String, PojoJson> filteredSource = source.filter((key, pojo) -> pojo.getReqAmt().doubleValue() <= 5000);

//        готовим разделение на ветки
        Predicate<String, PojoJson> isCafeRestr =
                (key, pojo) ->
                        pojo.getMerchant().equals("Cafe&Restaurant");
        Predicate<String, PojoJson> isSupermarket =
                (key, pojo) ->
                        pojo.getMerchant().equals("Supermarket");
        Predicate<String, PojoJson> isECommerce =
                (key, pojo) ->
                        pojo.getMerchant().equals("E-Commerce");
        int caferestr = 0;
        int supermarket = 1;
        int ecommerce = 2;

//        делим на ветки, предварительно задав ключ
        KStream<String, PojoJson>[] filtByMerch =
                filteredSource.selectKey((k, v) -> v.getClientPin())
                        .branch(isCafeRestr, isSupermarket, isECommerce);

        // create store
        StoreBuilder<KeyValueStore<String,AkciyaStep>> keyValueStoreBuilder =
                Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("akciya-steps"),
                        Serdes.String(),
                        SerDeFactory.getPOJOSerde(AkciyaStep.class));
        // register store
        builder.addStateStore(keyValueStoreBuilder);

        filtByMerch[caferestr].transform(new CafeTransformerSupplier(), "akciya-steps")
                                .to("sink-topic", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(AkciyaStep.class)));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), config);
        kafkaStreams.cleanUp();
        kafkaStreams.start();
        System.out.println(kafkaStreams.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
