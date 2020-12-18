package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AkciyaSink {
    @JsonProperty("clientPin")
    private String clientPin;

    @JsonProperty("stepId")
    private int stepId;

    @JsonProperty("eventDttm")
    private String eventDttm;

    @JsonProperty("antRur")
    private BigDecimal antRur;

    public AkciyaSink(AkciyaStep akciyaStep) {
        this.clientPin = akciyaStep.getClientPin();
        this.stepId = akciyaStep.getStepId();
        this.eventDttm = akciyaStep.getEventDttm();
        this.antRur = akciyaStep.getAntRur();
    }
}
