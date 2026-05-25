package com.sqld.model.dto;

import com.sqld.model.UserHistory;
import java.time.format.DateTimeFormatter;

public class HistoryResponse {
    private Long id;
    private int questionId;
    private boolean answeredCorrectly;
    private int selectedOption;
    private String answeredAt;
    private Boolean retriedCorrectly;
    private Integer retriedOption;
    private String retriedAt;

    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static HistoryResponse from(UserHistory h) {
        HistoryResponse r = new HistoryResponse();
        r.id = h.getId();
        r.questionId = h.getQuestionId();
        r.answeredCorrectly = h.isAnsweredCorrectly();
        r.selectedOption = h.getSelectedOption();
        r.answeredAt = h.getAnsweredAt() != null ? h.getAnsweredAt().format(FMT) : null;
        r.retriedCorrectly = h.getRetriedCorrectly();
        r.retriedOption = h.getRetriedOption();
        r.retriedAt = h.getRetriedAt() != null ? h.getRetriedAt().format(FMT) : null;
        return r;
    }

    public Long getId() { return id; }
    public int getQuestionId() { return questionId; }
    public boolean isAnsweredCorrectly() { return answeredCorrectly; }
    public int getSelectedOption() { return selectedOption; }
    public String getAnsweredAt() { return answeredAt; }
    public Boolean getRetriedCorrectly() { return retriedCorrectly; }
    public Integer getRetriedOption() { return retriedOption; }
    public String getRetriedAt() { return retriedAt; }
}
