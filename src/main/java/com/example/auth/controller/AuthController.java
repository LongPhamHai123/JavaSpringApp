package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/auth/hehe")
    public  ResponseEntity<?> hehe() {
        return ResponseEntity.ok("Hehe successful");
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            // log.info("User {} logged in successfully, with password {}", loginRequest.getUsername(), loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful " + loginRequest.getUsername());
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // user.setEmail(registerRequest.getEmail());
        user.setRole("USER");
        // user.setEnabled(true);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            log.error("Registration failed for user {}: {}", registerRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body("Registration failed");
        }

    }

    // @GetMapping("/user/profile")
    // public ResponseEntity<?> getUserProfile() {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     return ResponseEntity.ok("Logged in as: " + auth.getName());
    // }
    @GetMapping("/public/hello")
    public String publicEndpoint() {
        return "This is a public endpoint - no authentication required";
    }

    @GetMapping("/user/profile")
    public String userEndpoint() {
        log.info("Accessing user profile endpoint");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Hello " + auth.getName() + "! This is a user endpoint.";
    }

    @GetMapping("/admin/dashboard")
    public String adminEndpoint() {
        log.info("Accessing admin dashboard endpoint");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Hello Admin " + auth.getName() + "! This is an admin-only endpoint.";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Protected resource accessed by: " + auth.getName() + 
               " with roles: " + auth.getAuthorities();
    }
}


// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.stream.Collectors;

// @RestController
// public class ApiController {

//     @GetMapping("/")
//     public Map<String, Object> home(Authentication authentication) {
//         Map<String, Object> response = new HashMap<>();
        
//         if (authentication != null) {
//             response.put("message", "Welcome to Basic Auth Demo API");
//             response.put("authenticated", true);
//             response.put("username", authentication.getName());
//             response.put("authorities", authentication.getAuthorities()
//                     .stream()
//                     .map(GrantedAuthority::getAuthority)
//                     .collect(Collectors.toList()));
//         } else {
//             response.put("message", "Welcome to Basic Auth Demo API");
//             response.put("authenticated", false);
//         }
        
//         return response;
//     }

//     @GetMapping("/public/hello")
//     public Map<String, String> publicEndpoint() {
//         Map<String, String> response = new HashMap<>();
//         response.put("message", "This is a PUBLIC endpoint - No authentication required");
//         response.put("access", "public");
//         return response;
//     }

//     @GetMapping("/api/user/profile")
//     public Map<String, Object> userProfile(Authentication authentication) {
//         Map<String, Object> response = new HashMap<>();
//         response.put("message", "User Profile");
//         response.put("username", authentication.getName());
//         response.put("roles", authentication.getAuthorities()
//                 .stream()
//                 .map(GrantedAuthority::getAuthority)
//                 .collect(Collectors.toList()));
//         response.put("access", "USER or ADMIN");
//         return response;
//     }

//     @GetMapping("/api/user/dashboard")
//     public Map<String, String> userDashboard(Authentication authentication) {
//         Map<String, String> response = new HashMap<>();
//         response.put("message", "Welcome to User Dashboard");
//         response.put("user", authentication.getName());
//         response.put("access", "USER or ADMIN");
//         return response;
//     }

//     @GetMapping("/api/admin/panel")
//     public Map<String, String> adminPanel(Authentication authentication) {
//         Map<String, String> response = new HashMap<>();
//         response.put("message", "Welcome to Admin Panel");
//         response.put("admin", authentication.getName());
//         response.put("access", "ADMIN only");
//         response.put("warning", "This endpoint requires ADMIN role");
//         return response;
//     }

//     @GetMapping("/api/admin/users")
//     public Map<String, Object> adminUsers(Authentication authentication) {
//         Map<String, Object> response = new HashMap<>();
//         response.put("message", "Admin - User Management");
//         response.put("admin", authentication.getName());
//         response.put("access", "ADMIN only");
//         response.put("totalUsers", 3);
//         return response;
//     }
// }
