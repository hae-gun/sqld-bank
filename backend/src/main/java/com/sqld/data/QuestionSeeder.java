package com.sqld.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqld.model.Question;
import com.sqld.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 앱 시작 시 questions/ 폴더 아래의 모든 JSON 파일을 읽어 DB에 저장합니다.
 *
 * JSON 추가 방법:
 *   backend/src/main/resources/questions/ 폴더에 *.json 파일을 넣기만 하면 됩니다.
 *   앱을 재시작하면 새 문제가 자동으로 DB에 저장됩니다.
 *   (이미 존재하는 ID는 덮어쓰지 않고 건너뜁니다)
 *
 * DB 전환 방법:
 *   application.properties의 datasource URL을 MySQL/PostgreSQL로 변경하면
 *   이 시더가 동일하게 동작합니다.
 */
@Component
public class QuestionSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(QuestionSeeder.class);

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public QuestionSeeder(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:questions/*.json");

        List<Question> toSave = new ArrayList<>();
        int skipped = 0;

        for (Resource resource : resources) {
            log.info("문제 파일 로드: {}", resource.getFilename());
            try (InputStream is = resource.getInputStream()) {
                List<Question> loaded = objectMapper.readValue(
                    is, new TypeReference<List<Question>>() {}
                );
                for (Question q : loaded) {
                    if (!questionRepository.existsById(q.getId())) {
                        toSave.add(q);
                    } else {
                        skipped++;
                    }
                }
            } catch (Exception e) {
                log.error("파일 파싱 오류: {} - {}", resource.getFilename(), e.getMessage());
            }
        }

        if (!toSave.isEmpty()) {
            questionRepository.saveAll(toSave);
            log.info("문제 시딩 완료: {}개 저장, {}개 이미 존재 (스킵)", toSave.size(), skipped);
        } else {
            log.info("문제 시딩: 신규 문제 없음 (기존 {}개 유지)", questionRepository.count());
        }
    }
}
