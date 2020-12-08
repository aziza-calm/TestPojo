package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Coupon {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("reqAmt")
    private BigDecimal reqAmt;

    @JsonProperty("merchant")
    private String merchant;

    public Coupon(PojoJson pojoJson, PojoJson pojoJson2) {
        this.clientPin = pojoJson.getClientPin();
        this.merchant = pojoJson.getMerchant() + " + " + pojoJson2.getMerchant();
        this.reqAmt = pojoJson.getReqAmt().add(pojoJson2.getReqAmt());
    }
}
