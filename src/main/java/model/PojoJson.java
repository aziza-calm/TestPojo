package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import generator.Pojo;
import serdes.MultiDateDeserializer;

import java.math.BigDecimal;
import java.util.Date;

public class PojoJson {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("reqAmt")
    private BigDecimal reqAmt;

    @JsonProperty("merchant")
    private String merchant;

    @JsonProperty("uTime")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonDeserialize(using = MultiDateDeserializer.class)
    private Date uTime;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PojoJson(@JsonProperty("clientPin")String clientPin,
                    @JsonProperty("reqAmt")BigDecimal reqAmt,
                    @JsonProperty("merchant")String merchant,
                    @JsonProperty("uTime")Date uTime) {
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
