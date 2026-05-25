package com.sqld.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_history")
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int questionId;
    private boolean answeredCorrectly;
    private int selectedOption;
    private LocalDateTime answeredAt;

    // 재풀기 정보 (null = 재풀기 안함)
    private Boolean retriedCorrectly;
    private Integer retriedOption;
    private LocalDateTime retriedAt;

    public UserHistory() {}

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public boolean isAnsweredCorrectly() { return answeredCorrectly; }
    public void setAnsweredCorrectly(boolean answeredCorrectly) { this.answeredCorrectly = answeredCorrectly; }
    public int getSelectedOption() { return selectedOption; }
    public void setSelectedOption(int selectedOption) { this.selectedOption = selectedOption; }
    public LocalDateTime getAnsweredAt() { return answeredAt; }
    public void setAnsweredAt(LocalDateTime answeredAt) { this.answeredAt = answeredAt; }
    public Boolean getRetriedCorrectly() { return retriedCorrectly; }
    public void setRetriedCorrectly(Boolean retriedCorrectly) { this.retriedCorrectly = retriedCorrectly; }
    public Integer getRetriedOption() { return retriedOption; }
    public void setRetriedOption(Integer retriedOption) { this.retriedOption = retriedOption; }
    public LocalDateTime getRetriedAt() { return retriedAt; }
    public void setRetriedAt(LocalDateTime retriedAt) { this.retriedAt = retriedAt; }
}
