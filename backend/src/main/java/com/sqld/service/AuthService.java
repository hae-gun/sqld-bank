package com.sqld.service;

import com.sqld.model.User;
import com.sqld.model.dto.AuthResponse;
import com.sqld.model.dto.LoginRequest;
import com.sqld.model.dto.RegisterRequest;
import com.sqld.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(sha256(req.getPassword()));
        user.setToken(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new AuthResponse(user.getId(), user.getUsername(), user.getToken());
    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));
        if (!user.getPasswordHash().equals(sha256(req.getPassword()))) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }
        // Issue a fresh token on every login
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        return new AuthResponse(user.getId(), user.getUsername(), user.getToken());
    }

    public User getUserByToken(String token) {
        return userRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    }

    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 사용할 수 없습니다.", e);
        }
    }
}
