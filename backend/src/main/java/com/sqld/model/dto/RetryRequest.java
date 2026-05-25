package com.sqld.model.dto;

public class RetryRequest {
    private boolean retriedCorrectly;
    private int retriedOption;

    public boolean isRetriedCorrectly() { return retriedCorrectly; }
    public void setRetriedCorrectly(boolean retriedCorrectly) { this.retriedCorrectly = retriedCorrectly; }
    public int getRetriedOption() { return retriedOption; }
    public void setRetriedOption(int retriedOption) { this.retriedOption = retriedOption; }
}
