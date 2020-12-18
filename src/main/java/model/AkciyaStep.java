package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AkciyaStep {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("stepId")
    private int stepId;

    @JsonProperty("eventDttm")
    private String eventDttm;

    @JsonProperty("antRur")
    private BigDecimal antRur;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AkciyaStep(@JsonProperty("clientPin") String clientPin,
                      @JsonProperty("stepId") int stepId,
                      @JsonProperty("eventDttm") String eventDttm,
                      @JsonProperty("antRur") BigDecimal antRur) {
        this.clientPin = clientPin;
        this.stepId = stepId;
        this.eventDttm = eventDttm;
        this.antRur = antRur;
    }

    public AkciyaStep(PojoJson pojoJson) {
        this.clientPin = pojoJson.getClientPin();
        this.stepId = 0;
        this.eventDttm = new Date(System.currentTimeMillis()).toString();
        this.antRur = pojoJson.getReqAmt();
    }

    public String getClientPin() {
        return clientPin;
    }

    public BigDecimal getAntRur() {
        return antRur;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void updateAntRur(BigDecimal reqAmt) {
        antRur = antRur.add(reqAmt);
    }

    @Override
    public String toString() {
        return "AkciyaStep{" +
                "clientPin='" + clientPin + '\'' +
                ", stepId=" + stepId +
                ", eventDttm='" + eventDttm + '\'' +
                ", antRur=" + antRur +
                '}';
    }
}
