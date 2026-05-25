package com.sqld.repository;

import com.sqld.model.UserHistory;
import com.sqld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUserOrderByAnsweredAtDesc(User user);
    Optional<UserHistory> findByIdAndUser(Long id, User user);
}
