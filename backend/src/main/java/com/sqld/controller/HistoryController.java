package com.sqld.controller;

import com.sqld.model.User;
import com.sqld.model.dto.HistoryRequest;
import com.sqld.model.dto.HistoryResponse;
import com.sqld.model.dto.RetryRequest;
import com.sqld.service.AuthService;
import com.sqld.service.UserHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    private final AuthService authService;
    private final UserHistoryService historyService;

    public HistoryController(AuthService authService, UserHistoryService historyService) {
        this.authService = authService;
        this.historyService = historyService;
    }

    private User resolveUser(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new IllegalArgumentException("인증이 필요합니다.");
        }
        return authService.getUserByToken(auth.substring(7));
    }

    @GetMapping
    public ResponseEntity<?> getHistory(HttpServletRequest request) {
        try {
            User user = resolveUser(request);
            List<HistoryResponse> list = historyService.getHistory(user);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveHistory(@RequestBody HistoryRequest req,
                                         HttpServletRequest request) {
        try {
            User user = resolveUser(request);
            HistoryResponse res = historyService.saveHistory(user, req);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/retry")
    public ResponseEntity<?> retry(@PathVariable Long id,
                                   @RequestBody RetryRequest req,
                                   HttpServletRequest request) {
        try {
            User user = resolveUser(request);
            HistoryResponse res = historyService.retryHistory(user, id, req);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
