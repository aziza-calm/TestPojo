import joiner.CouponJoiner;
import model.Analytics;
import model.Coupon;
import model.PojoJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import serdes.SerDeFactory;

import java.time.Duration;
import java.util.Properties;

public class StreamingProblemFive {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "problem-five");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
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

//        джоиним потоки с Кафе\ресторанами и супермаркетами с окном 45 минут
        ValueJoiner<PojoJson, PojoJson, Coupon> couponJoiner = new CouponJoiner();
        JoinWindows fourtyfiveMinuteWindow =  JoinWindows.of(Duration.ofMinutes(45));
        KStream<String, Coupon> coupons = filtByMerch[caferestr].join(filtByMerch[supermarket],
                couponJoiner, /* ValueJoiner */
                fourtyfiveMinuteWindow,
                Joined.with(
                        Serdes.String(), /* key */
                        SerDeFactory.getPOJOSerde(PojoJson.class),   /* left value */
                        SerDeFactory.getPOJOSerde(PojoJson.class))  /* right value */
        );
//        и отправляем в топик coupons
        coupons.to("coupons", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(Coupon.class)));

//        третья ветка ecommerce: считаем среднюю сумму покупок в окне 20 мин, обновляя каждую минуту.
//        Маскируем ключ, там хранится clientPin
        Analytics<PojoJson> analytics = new Analytics<>();
        filtByMerch[ecommerce].groupByKey(Grouped.with(Serdes.String(), SerDeFactory.getPOJOSerde(PojoJson.class)))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(20)).grace(Duration.ofMinutes(1)))
                .aggregate(() -> analytics,
                        (key, value, agvalue) -> agvalue.recountAv(value),
                        Materialized
                                .with(Serdes.String(), SerDeFactory.getPOJOSerde(Analytics.class))
                )
                .toStream()
                .map((k, v) -> KeyValue.pair("xxxxxx", v))
                .to("analytics", Produced.with(Serdes.String(), SerDeFactory.getPOJOSerde(Analytics.class)));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
        System.out.println(streams.toString());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
