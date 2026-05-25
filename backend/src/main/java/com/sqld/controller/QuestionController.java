package com.sqld.controller;

import com.sqld.model.Question;
import com.sqld.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // 전체 문제 목록
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    // 랜덤 1문제
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String difficulty
    ) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(questionService.getRandomQuestionByCategory(category));
        }
        if (type != null && !type.isEmpty()) {
            return ResponseEntity.ok(questionService.getRandomQuestionByType(type));
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            return ResponseEntity.ok(questionService.getRandomQuestionByDifficulty(difficulty));
        }
        return ResponseEntity.ok(questionService.getRandomQuestion());
    }

    // 특정 ID 문제
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        Question q = questionService.getQuestionById(id);
        if (q == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(q);
    }

    // 카테고리 목록
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(questionService.getCategories());
    }

    // 랜덤 N문제 (필터 지원)
    @GetMapping("/random/batch")
    public ResponseEntity<List<Question>> getRandomQuestions(
        @RequestParam(defaultValue = "10") int count,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String difficulty
    ) {
        return ResponseEntity.ok(questionService.getRandomQuestions(count, category, type, difficulty));
    }

    // 통계
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<Question> all = questionService.getAllQuestions();
        long basic = all.stream().filter(q -> "기출".equals(q.getType())).count();
        long variant = all.stream().filter(q -> "변형".equals(q.getType())).count();
        return ResponseEntity.ok(Map.of(
            "total", all.size(),
            "기출", basic,
            "변형", variant,
            "categories", questionService.getCategories().size()
        ));
    }
}
