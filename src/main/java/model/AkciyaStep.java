package model;

import java.math.BigDecimal;
import java.util.Date;

public class AkciyaStep {
    private String clientPin;
    private int stepId;
    private String eventDttm;
    private BigDecimal antRur;

    public String getClientPin() {
        return clientPin;
    }

    public BigDecimal getAntRur() {
        return antRur;
    }

    public AkciyaStep(PojoJson pojoJson) {
        this.clientPin = pojoJson.getClientPin();
        this.stepId = 0;
        this.eventDttm = new Date(System.currentTimeMillis()).toString();
        this.antRur = BigDecimal.ZERO;
    }

    public void setAntRur(PojoJson pojoJson) {
        this.antRur = this.antRur.add(pojoJson.getReqAmt());
    }
}
