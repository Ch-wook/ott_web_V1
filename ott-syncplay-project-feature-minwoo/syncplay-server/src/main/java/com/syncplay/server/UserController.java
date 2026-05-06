package com.syncplay.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User request) {
        String name = request.getName() == null ? "" : request.getName().trim();
        String email = request.getEmail() == null ? "" : request.getEmail().trim().toLowerCase();
        String password = request.getPassword() == null ? "" : request.getPassword().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("모든 항목을 입력해주세요.");
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("이미 사용 중인 이메일입니다.");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        User saved = userRepository.save(user);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", "회원가입 완료");
        result.put("user", toSafeUser(saved));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = request.getEmail() == null ? "" : request.getEmail().trim().toLowerCase();
        String password = request.getPassword() == null ? "" : request.getPassword().trim();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일과 비밀번호를 입력해주세요.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", "로그인 성공");
        result.put("user", toSafeUser(user));

        return ResponseEntity.ok(result);
    }

    private Map<String, Object> toSafeUser(User user) {
        Map<String, Object> safeUser = new LinkedHashMap<>();
        safeUser.put("id", user.getId());
        safeUser.put("name", user.getName());
        safeUser.put("email", user.getEmail());
        return safeUser;
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest() {}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
