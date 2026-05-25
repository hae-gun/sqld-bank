package com.sqld.model;

import com.sqld.model.converter.StringListConverter;
import jakarta.persistence.*;
import java.util.List;

/**
 * 문제 엔티티.
 * - 현재는 H2 파일 DB에 JSON 시딩 방식으로 운영
 * - 추후 MySQL/PostgreSQL 전환 시 application.properties datasource만 변경하면 됨
 * - id는 JSON 파일에서 직접 지정 (history 테이블에서 questionId로 참조하므로 고정)
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @Column(name = "question_id")
    private int id;

    @Column(nullable = false, length = 20)
    private String subject;   // "1과목" | "2과목"

    @Column(nullable = false, length = 50)
    private String category;  // "SELECT/WHERE", "JOIN", "정규화" 등

    @Column(nullable = false, length = 10)
    private String type;      // "기출" | "변형"

    @Column(nullable = false, length = 5)
    private String difficulty; // "하" | "중" | "상"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;  // 문제 본문 (SQL 코드 포함 시 \n으로 구분)

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private List<String> options; // 4지선다 보기

    @Column(nullable = false)
    private int answer;       // 정답 번호 (1-based)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation; // 해설

    public Question() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public int getAnswer() { return answer; }
    public void setAnswer(int answer) { this.answer = answer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
