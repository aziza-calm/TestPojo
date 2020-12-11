package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import generator.Pojo;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PojoJson {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("reqAmt")
    private BigDecimal reqAmt;

    @JsonProperty("merchant")
    private String merchant;

    @JsonProperty("uTime")
    private String uTime;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PojoJson(@JsonProperty("clientPin")String clientPin,
                    @JsonProperty("reqAmt")BigDecimal reqAmt,
                    @JsonProperty("merchant")String merchant,
                    @JsonProperty("uTime")String uTime) {
        this.clientPin = clientPin;
        this.reqAmt = reqAmt;
        this.merchant = merchant;
        this.uTime = uTime;
    }

    @JsonCreator
    public PojoJson(Pojo pojo) {
        this.clientPin = pojo.clientPin;
        this.reqAmt = pojo.reqAmt;
        this.merchant = pojo.merchant;
        this.uTime = pojo.uTime.toString();
    }

    public BigDecimal getReqAmt() {
        return reqAmt;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getClientPin() {
        return clientPin;
    }
}
