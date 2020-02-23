package com.panos.sportmonitor.spark.dto;

import org.apache.commons.math3.util.Precision;

import java.io.Serializable;

public class BetSelection implements Serializable {
    private String betId;
    private long selectionId;
    private double price;
    private double logPrice;

    public BetSelection() {
    }
    public BetSelection(String betId, long selectionId, double price) {
        this.setBetId(betId);
        this.setSelectionId(selectionId);
        this.setPrice(price);
    }

    public long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public double getPrice() {
        return price;
    }
    public double getLogPrice() {
        return logPrice;
    }

    public void setPrice(double price) {
        this.price = Precision.round(price, 2);
        this.logPrice = Math.log(price);
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

}
