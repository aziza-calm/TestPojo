package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Analytics {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("avReqAmt")
    private BigDecimal avReqAmt;

    BigDecimal sum;
    int count;

    public Analytics(String clientPin) {
        this.clientPin = clientPin;
        this.avReqAmt = BigDecimal.ZERO;
        this.sum = BigDecimal.ZERO;
        this.count = 0;
    }

    public BigDecimal getAvReqAmt(String clientPin, BigDecimal reqAmt) {
        this.clientPin = clientPin;
        this.count += 1;
        this.sum = this.sum.add(reqAmt);
        this.avReqAmt = this.sum.divide(BigDecimal.valueOf(this.count));
        return avReqAmt;
    }
}
