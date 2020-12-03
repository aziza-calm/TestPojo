import joiner.CouponJoiner;
import model.Coupon;
import model.PojoJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import serdes.SerDeFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class StreamingProblemFive {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "problem-five");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, PojoJson> source = builder.stream("source-topic", Consumed.with(Serdes.String(), SerDeFactory.getPOJOSerde(PojoJson.class)));

        KStream<String, PojoJson> filteredSource = source.filter((key, pojo) -> pojo.getReqAmt().doubleValue() <= 5000);

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

        KStream<String, PojoJson>[] filtByMerch =
                filteredSource.selectKey((k, v) -> v.getClientPin())
                        .branch(isCafeRestr, isSupermarket, isECommerce);

        ValueJoiner<PojoJson, PojoJson, Coupon> couponJoiner = new CouponJoiner();
        JoinWindows fourtyfiveMinuteWindow =  JoinWindows.of(60 * 1000 * 45);
        KStream<String, Coupon> coupons = filtByMerch[caferestr].join(filtByMerch[supermarket],
                couponJoiner, /* ValueJoiner */
                fourtyfiveMinuteWindow,
                Joined.with(
                        Serdes.String(), /* key */
                        SerDeFactory.getPOJOSerde(PojoJson.class),   /* left value */
                        SerDeFactory.getPOJOSerde(PojoJson.class))  /* right value */
        );
        coupons.to("coupons", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(Coupon.class)));
//
//        filtByMerch[ecommerce].to("sink-topic", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(PojoJson.class)));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
        System.out.println(streams.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
