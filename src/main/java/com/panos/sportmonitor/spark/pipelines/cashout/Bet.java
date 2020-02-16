package com.panos.sportmonitor.spark.pipelines.cashout;

import java.io.Serializable;

public class Bet implements Serializable {
    private String betId;
    private long betStamp;
    private float amount;
    private float totalOdd;
    private float totalReturn;
    private float cashOut;
    private float cashOutOdd;
    private int selections;

    public Bet() {
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(float totalReturn) {
        this.totalReturn = totalReturn;
    }

    public float getCashOut() {
        return cashOut;
    }

    public void setCashOut(float cashOut) {
        this.cashOut = cashOut;
    }

    public float getTotalOdd() {
        return totalOdd;
    }

    public void setTotalOdd(float totalOdd) {
        this.totalOdd = totalOdd;
    }

    public float getCashOutOdd() {
        return cashOutOdd;
    }

    public void setCashOutOdd(float cashOutOdd) {
        this.cashOutOdd = cashOutOdd;
    }

    public int getSelections() {
        return selections;
    }

    public void setSelections(int selections) {
        this.selections = selections;
    }

    public long getBetStamp() {
        return betStamp;
    }

    public void setBetStamp(long betStamp) {
        this.betStamp = betStamp;
    }
}
