package com.sqld.repository;

import com.sqld.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findBySubject(String subject);

    List<Question> findByCategory(String category);

    List<Question> findBySubjectAndCategory(String subject, String category);

    List<Question> findByType(String type);

    List<Question> findByDifficulty(String difficulty);

    @Query("SELECT q FROM Question q WHERE " +
           "(:subject IS NULL OR q.subject = :subject) AND " +
           "(:category IS NULL OR q.category = :category) AND " +
           "(:type IS NULL OR q.type = :type) AND " +
           "(:difficulty IS NULL OR q.difficulty = :difficulty)")
    List<Question> findByFilters(
        @Param("subject") String subject,
        @Param("category") String category,
        @Param("type") String type,
        @Param("difficulty") String difficulty
    );

    @Query("SELECT DISTINCT q.category FROM Question q ORDER BY q.category")
    List<String> findDistinctCategories();

    @Query("SELECT DISTINCT q.subject FROM Question q ORDER BY q.subject")
    List<String> findDistinctSubjects();
}
