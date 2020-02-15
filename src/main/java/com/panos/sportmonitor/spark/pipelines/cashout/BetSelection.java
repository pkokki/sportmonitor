package com.panos.sportmonitor.spark.pipelines.cashout;

import java.io.Serializable;

public class BetSelection implements Serializable {
    private String betId;
    private long selectionId;
    private float price;

    public BetSelection() {
    }
    public BetSelection(String betId, long selectionId, float price) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }
}
