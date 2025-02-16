package com.exam_management.stage.controller;

import com.exam_management.stage.entity.Chapter;
import com.exam_management.stage.entity.ExamResult;
import com.exam_management.stage.entity.User;
import com.exam_management.stage.repository.ChapterRepository;
import com.exam_management.stage.repository.ExamResultRepository;
import com.exam_management.stage.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            long totalUsers = userRepository.count();
            long totalChapters = chapterRepository.count();
            double averageScore = examResultRepository.getAverageScore();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", totalUsers);
            stats.put("totalChapters", totalChapters);
            stats.put("averageScore", averageScore);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Error fetching dashboard stats: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to fetch dashboard stats."));
        }
    }

    @GetMapping("/dashboard/matrix")
    public ResponseEntity<?> getTrainingNeedsMatrix() {
        try {
            List<Map<String, Object>> matrix = chapterRepository.getTrainingNeedsMatrix();
            return ResponseEntity.ok(matrix);
        } catch (Exception e) {
            logger.error("Error fetching training needs matrix: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch training needs matrix.");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> roleRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String newRole = roleRequest.get("role");
            if (newRole != null && !newRole.isEmpty()) {
                user.setRole(newRole);
                userRepository.save(user);
                return ResponseEntity.ok("User role updated successfully.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @GetMapping("/modules")
    public ResponseEntity<List<Chapter>> getAllModules() {
        try {
            List<Chapter> chapters = chapterRepository.findAll();
            return ResponseEntity.ok(chapters);
        } catch (Exception e) {
            logger.error("Error fetching modules: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/modules")
    public ResponseEntity<Chapter> createModule(@RequestBody Chapter chapter) {
        try {
            Chapter savedChapter = chapterRepository.save(chapter);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedChapter);
        } catch (Exception e) {
            logger.error("Error creating module: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/modules/{id}")
    public ResponseEntity<?> updateModule(@PathVariable Long id, @RequestBody Chapter updatedChapter) {
        try {
            Optional<Chapter> chapterOptional = chapterRepository.findById(id);
            if (chapterOptional.isPresent()) {
                Chapter chapter = chapterOptional.get();
                chapter.setName(updatedChapter.getName());
                Chapter savedChapter = chapterRepository.save(chapter);
                return ResponseEntity.ok(savedChapter);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
            }
        } catch (Exception e) {
            logger.error("Error updating module: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update module.");
        }
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        try {
            chapterRepository.deleteById(id);
            return ResponseEntity.ok("Module deleted successfully.");
        } catch (Exception e) {
            logger.error("Error deleting module: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete module.");
        }
    }

    @GetMapping("/account-requests")
    public ResponseEntity<List<User>> getAccountRequests() {
        try {
            List<User> accountRequests = userRepository.findByStatus("PENDING");
            return ResponseEntity.ok(accountRequests);
        } catch (Exception e) {
            logger.error("Error fetching account requests: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/account-requests/{id}/approve")
    public ResponseEntity<String> approveAccount(@PathVariable Long id) {
        return updateUserStatus(id, "APPROVED", "Account approved successfully.");
    }

    @PutMapping("/account-requests/{id}/revoke")
    public ResponseEntity<String> revokeAccount(@PathVariable Long id) {
        return updateUserStatus(id, "PENDING", "Account revoked successfully.");
    }

    private ResponseEntity<String> updateUserStatus(Long id, String status, String message) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setStatus(status);
                userRepository.save(user);
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            logger.error("Error updating user status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user status.");
        }
    }
    @GetMapping("/exam-results")
    public ResponseEntity<List<ExamResult>> getAllExamResults() {
        try {
            List<ExamResult> results = examResultRepository.findAll();
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            logger.error("Error fetching exam results: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}