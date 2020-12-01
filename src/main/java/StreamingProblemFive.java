import model.PojoJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import serdes.SerDeFactory;

import java.util.Properties;

public class StreamingProblemFive {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "problem-five");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, PojoJson> source = builder.stream("source-topic");

        KStream<String, PojoJson> filteredSource = source.filter((key, pojo) -> pojo.getReqAmt().doubleValue() <= 0);

        Predicate<String, PojoJson> isCafeRestr =
                (key, pojo) ->
                pojo.getMerchant().equals("Cafe&Restaraunt");
        Predicate<String, PojoJson> isSupermarket =
                (key, pojo) ->
                pojo.getMerchant().equals("Supermarket");
        Predicate<String, PojoJson> isECommerce =
                (key, pojo) ->
                pojo.getMerchant().equals("E-Commerce");
        int caferest = 0;
        int supermarket = 1;
        int ecommerce = 2;

        KStream<String, PojoJson>[] filtByMerch =
                filteredSource.branch(isCafeRestr, isSupermarket, isECommerce);

        filtByMerch[ecommerce].to("sink-topic", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(PojoJson.class)));
    }
}
