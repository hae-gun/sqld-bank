package com.sqld.service;

import com.sqld.model.User;
import com.sqld.model.UserHistory;
import com.sqld.model.dto.HistoryRequest;
import com.sqld.model.dto.HistoryResponse;
import com.sqld.model.dto.RetryRequest;
import com.sqld.repository.UserHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserHistoryService {

    private final UserHistoryRepository historyRepository;

    public UserHistoryService(UserHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<HistoryResponse> getHistory(User user) {
        return historyRepository.findByUserOrderByAnsweredAtDesc(user)
                .stream()
                .map(HistoryResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public HistoryResponse saveHistory(User user, HistoryRequest req) {
        UserHistory h = new UserHistory();
        h.setUser(user);
        h.setQuestionId(req.getQuestionId());
        h.setAnsweredCorrectly(req.isAnsweredCorrectly());
        h.setSelectedOption(req.getSelectedOption());
        h.setAnsweredAt(LocalDateTime.now());
        historyRepository.save(h);
        return HistoryResponse.from(h);
    }

    @Transactional
    public HistoryResponse retryHistory(User user, Long historyId, RetryRequest req) {
        UserHistory h = historyRepository.findByIdAndUser(historyId, user)
                .orElseThrow(() -> new IllegalArgumentException("기록을 찾을 수 없습니다."));
        h.setRetriedCorrectly(req.isRetriedCorrectly());
        h.setRetriedOption(req.getRetriedOption());
        h.setRetriedAt(LocalDateTime.now());
        historyRepository.save(h);
        return HistoryResponse.from(h);
    }
}
