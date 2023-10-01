package com.pancredit.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

public class TransactionData {
    @JsonAlias("Id")
    @JsonProperty("Id")
    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}$")
    private String id;
    @JsonAlias("ApplicationId")
    @JsonProperty("ApplicationId")
    private Integer applicationId;
    @JsonAlias("Type")
    @JsonProperty("Type")
    @Pattern(regexp = "^(\\bDebit\\b)|(\\bCredit\\b)$")
    private String type;
    @JsonAlias("Summary")
    @JsonProperty("Summary")
    @Pattern(regexp = "^(\\bPayment\\b)|(\\bRefund\\b)$")
    private String summary;
    @JsonAlias("Amount")
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonAlias("PostingDate")
    @JsonProperty("PostingDate")
    private String postingDate;
    @JsonAlias("IsCleared")
    @JsonProperty("IsCleared")
    private boolean cleared;
    @JsonAlias("ClearedDate")
    @JsonProperty("ClearedDate")
    private String clearedDate;

    public TransactionData() {
    }

    public TransactionData(String id, Integer applicationId, String type, String summary, BigDecimal amount, String postingDate, boolean cleared, String clearedDate) {
        this.id = id;
        this.applicationId = applicationId;
        this.type = type;
        this.summary = summary;
        this.amount = amount;
        this.postingDate = postingDate;
        this.cleared = cleared;
        this.clearedDate = clearedDate;
    }

    public String getId() {
        return id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public String getType() {
        return type;
    }

    public String getSummary() {
        return summary;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public boolean isCleared() {
        return cleared;
    }

    public String getClearedDate() {
        return clearedDate;
    }

    //@JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    //@JsonProperty("ApplicationId")
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    //@JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    //@JsonProperty("Summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    //@JsonProperty("Amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //@JsonProperty("PostingDate")
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    //@JsonProperty("IsCleared")
    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    //@JsonProperty("ClearedDate")
    public void setClearedDate(String clearedDate) {
        this.clearedDate = clearedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionData transactionData = (TransactionData) o;
        return cleared == transactionData.cleared
                && Objects.equals(id, transactionData.id)
                && Objects.equals(applicationId, transactionData.applicationId)
                && Objects.equals(type, transactionData.type)
                && Objects.equals(summary, transactionData.summary)
                && Objects.equals(amount, transactionData.amount)
                && Objects.equals(postingDate, transactionData.postingDate)
                && Objects.equals(clearedDate, transactionData.clearedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationId, type, summary, amount, postingDate, cleared, clearedDate);
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", applicationId=" + applicationId +
                ", type='" + type + '\'' +
                ", summary='" + summary + '\'' +
                ", amount=" + amount +
                ", postingDate='" + postingDate + '\'' +
                ", cleared=" + cleared +
                ", clearedDate='" + clearedDate + '\'' +
                '}';
    }
}
