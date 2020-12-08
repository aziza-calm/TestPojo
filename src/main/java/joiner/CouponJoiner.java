package joiner;

import model.Coupon;
import model.PojoJson;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class CouponJoiner implements ValueJoiner<PojoJson, PojoJson, Coupon> {
    @Override
    public Coupon apply(PojoJson pojoJson, PojoJson pojoJson2) {
        return new Coupon(pojoJson, pojoJson2);
    }
}
