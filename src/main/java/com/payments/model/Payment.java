package main.java.com.payments.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private Integer id;
    private TypePayment type;
    private BigDecimal amount;
    private LocalDate date;
    private String motif;
    private boolean conditionValidee;
    private Integer agentId;

    public Payment(Integer id, TypePayment type, BigDecimal amount, LocalDate date, String motif, boolean conditionValidee, Integer agentId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.motif = motif;
        this.conditionValidee = conditionValidee;
        this.agentId = agentId;
    }

    public Payment(TypePayment type, BigDecimal amount, LocalDate date, String motif, boolean conditionValidee, Integer agentId) {
        this(null, type, amount, date, motif, conditionValidee, agentId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypePayment getType() {
        return type;
    }

    public void setType(TypePayment type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean isConditionValidee() {
        return conditionValidee;
    }

    public void setConditionValidee(boolean conditionValidee) {
        this.conditionValidee = conditionValidee;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return String.format("Payment[id=%d, type=%s, amount=%s, date=%s, motif=%s, cond=%s, agentId=%d]",
                id, type, amount, date, motif, conditionValidee, agentId);
    }
}
