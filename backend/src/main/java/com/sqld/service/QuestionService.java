package com.sqld.service;

import com.sqld.data.QuestionBank;
import com.sqld.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final List<Question> questions = QuestionBank.getAllQuestions();
    private final Random random = new Random();

    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public Question getRandomQuestion() {
        return questions.get(random.nextInt(questions.size()));
    }

    public Question getRandomQuestionByCategory(String category) {
        List<Question> filtered = questions.stream()
            .filter(q -> q.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
        if (filtered.isEmpty()) return getRandomQuestion();
        return filtered.get(random.nextInt(filtered.size()));
    }

    public Question getRandomQuestionByType(String type) {
        List<Question> filtered = questions.stream()
            .filter(q -> q.getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());
        if (filtered.isEmpty()) return getRandomQuestion();
        return filtered.get(random.nextInt(filtered.size()));
    }

    public Question getRandomQuestionByDifficulty(String difficulty) {
        List<Question> filtered = questions.stream()
            .filter(q -> q.getDifficulty().equalsIgnoreCase(difficulty))
            .collect(Collectors.toList());
        if (filtered.isEmpty()) return getRandomQuestion();
        return filtered.get(random.nextInt(filtered.size()));
    }

    public List<String> getCategories() {
        return questions.stream()
            .map(Question::getCategory)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public Question getQuestionById(int id) {
        return questions.stream()
            .filter(q -> q.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public List<Question> getRandomQuestions(int count, String category, String type, String difficulty) {
        List<Question> filtered = questions.stream()
            .filter(q -> category == null || q.getCategory().equalsIgnoreCase(category))
            .filter(q -> type == null || q.getType().equalsIgnoreCase(type))
            .filter(q -> difficulty == null || q.getDifficulty().equalsIgnoreCase(difficulty))
            .collect(Collectors.toList());

        Collections.shuffle(filtered);
        return filtered.stream().limit(count).collect(Collectors.toList());
    }
}
