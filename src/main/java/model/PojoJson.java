package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import generator.Pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PojoJson {
    @JsonProperty("CLIENT_PIN")
    private String clientPin;

    @JsonProperty("REQ_AMT")
    private BigDecimal reqAmt;

    @JsonProperty("MERCHANT")
    private String merchant;

    @JsonProperty("UTIME")
    private Date uTime;

    public PojoJson(String clientPin, BigDecimal reqAmt, String merchant, Date uTime) {
        this.clientPin = clientPin;
        this.reqAmt = reqAmt;
        this.merchant = merchant;
        this.uTime = uTime;
    }

    public PojoJson(Pojo pojo) {
        this.clientPin = pojo.clientPin;
        this.reqAmt = pojo.reqAmt;
        this.merchant = pojo.merchant;
        this.uTime = pojo.uTime;
    }

    public BigDecimal getReqAmt() {
        return reqAmt;
    }

    public String getMerchant() {
        return merchant;
    }
}
