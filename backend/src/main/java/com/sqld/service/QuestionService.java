package com.sqld.service;

import com.sqld.model.Question;
import com.sqld.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Random random = new Random();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /** 전체 문제 목록 (필터 지원) */
    public List<Question> getQuestions(String subject, String category, String type, String difficulty) {
        return questionRepository.findByFilters(
            emptyToNull(subject),
            emptyToNull(category),
            emptyToNull(type),
            emptyToNull(difficulty)
        );
    }

    /** 전체 문제 (필터 없음) */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /** ID로 단건 조회 */
    public Optional<Question> getQuestionById(int id) {
        return questionRepository.findById(id);
    }

    /** 카테고리 목록 */
    public List<String> getCategories() {
        return questionRepository.findDistinctCategories();
    }

    /** 과목 목록 */
    public List<String> getSubjects() {
        return questionRepository.findDistinctSubjects();
    }

    /** 통계 */
    public Map<String, Object> getStats() {
        List<Question> all = questionRepository.findAll();
        Map<String, Long> bySubject = all.stream()
            .collect(Collectors.groupingBy(q -> q.getSubject() != null ? q.getSubject() : "미분류", Collectors.counting()));
        Map<String, Long> byType = all.stream()
            .collect(Collectors.groupingBy(q -> q.getType() != null ? q.getType() : "기타", Collectors.counting()));
        Map<String, Long> byDifficulty = all.stream()
            .collect(Collectors.groupingBy(q -> q.getDifficulty() != null ? q.getDifficulty() : "중", Collectors.counting()));

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("total", all.size());
        stats.put("bySubject", bySubject);
        stats.put("byType", byType);
        stats.put("byDifficulty", byDifficulty);
        return stats;
    }

    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
