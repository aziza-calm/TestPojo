package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Analytics<T> {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("avReqAmt")
    private BigDecimal avReqAmt;

    BigDecimal sum;
    int count;

    public Analytics() {
        this.clientPin = null;
        this.avReqAmt = BigDecimal.ZERO;
        this.sum = BigDecimal.ZERO;
        this.count = 0;
    }

    public Analytics<T> add(PojoJson pojoJson) {
        this.count += 1;
        this.sum = this.sum.add(pojoJson.getReqAmt());
        this.avReqAmt = this.sum.divide(BigDecimal.valueOf(this.count));
        return this;
    }
}
