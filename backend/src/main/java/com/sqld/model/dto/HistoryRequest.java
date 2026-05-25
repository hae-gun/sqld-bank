package com.sqld.model.dto;

public class HistoryRequest {
    private int questionId;
    private boolean answeredCorrectly;
    private int selectedOption;

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public boolean isAnsweredCorrectly() { return answeredCorrectly; }
    public void setAnsweredCorrectly(boolean answeredCorrectly) { this.answeredCorrectly = answeredCorrectly; }
    public int getSelectedOption() { return selectedOption; }
    public void setSelectedOption(int selectedOption) { this.selectedOption = selectedOption; }
}
