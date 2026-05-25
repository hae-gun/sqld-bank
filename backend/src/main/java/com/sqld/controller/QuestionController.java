package com.sqld.controller;

import com.sqld.model.Question;
import com.sqld.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 전체 문제 목록 조회 (필터 지원)
     * GET /api/questions?subject=1과목&category=JOIN&type=기출&difficulty=중
     */
    @GetMapping
    public ResponseEntity<List<Question>> getQuestions(
        @RequestParam(required = false) String subject,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String difficulty
    ) {
        return ResponseEntity.ok(questionService.getQuestions(subject, category, type, difficulty));
    }

    /**
     * 단건 조회
     * GET /api/questions/101
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        return questionService.getQuestionById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 카테고리 목록
     * GET /api/questions/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(questionService.getCategories());
    }

    /**
     * 과목 목록
     * GET /api/questions/subjects
     */
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getSubjects() {
        return ResponseEntity.ok(questionService.getSubjects());
    }

    /**
     * 통계 (총 문제수, 과목별/유형별/난이도별 분포)
     * GET /api/questions/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(questionService.getStats());
    }
}
