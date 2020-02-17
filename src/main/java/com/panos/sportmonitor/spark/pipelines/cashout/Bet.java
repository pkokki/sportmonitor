package com.panos.sportmonitor.spark.pipelines.cashout;

import org.apache.commons.math3.util.Precision;

import java.io.Serializable;

public class Bet implements Serializable {
    private String betId;
    private long betStamp;
    private int selections;
    private double initialPrice;
    private double cashOutPrice;
    private double logCashOutPrice;

    public Bet() {
    }

    public Bet(String betId, long betStamp, int selections, double totalOdd, double cashOutOdd) {
        this.betId = betId;
        this.betStamp = betStamp;
        this.selections = selections;
        setInitialPrice(totalOdd);
        setCashOutPrice(cashOutOdd);
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = Precision.round(initialPrice, 2);
    }

    public double getCashOutPrice() {
        return cashOutPrice;
    }
    public double getLogCashOutPrice() {
        return logCashOutPrice;
    }

    public void setCashOutPrice(double cashOutPrice) {
        this.cashOutPrice = Precision.round(cashOutPrice, 2);
        this.logCashOutPrice = Math.log(cashOutPrice);
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
