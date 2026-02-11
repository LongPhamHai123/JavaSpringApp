package com.example.auth.model;

import org.springframework.data.mongodb.core.index.Indexed;

// import jakarta.persistence.*;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

// @Entity
// @Table(name = "users")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class User {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @Column(unique = true, nullable = false)
//     private String username;
    
//     @Column(nullable = false)
//     private String password;
    
//     @Column(nullable = false)
//     private String email;
    
//     @Column(nullable = false)
//     private String role;
    
//     private boolean enabled = true;
// }

// package com.example.auth.model;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Table(name = "users")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false)
//     private String username;

//     @Column(nullable = false)
//     private String password;

//     @Column(nullable = false)
//     private String role;

//     private boolean enabled = true;

//     public User(String username, String password, String role) {
//         this.username = username;
//         this.password = password;
//         this.role = role;
//     }
// }
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String email;
    
    
    // Getters and setters
    // public String getId() { return id; }
    // public void setId(String id) { this.id = id; }
    
    // public String getUsername() { return username; }
    // public void setUsername(String username) { this.username = username; }
    
    // public String getPassword() { return password; }
    // public void setPassword(String password) { this.password = password; }
    
    // public String getRole() { return role; }
    // public void setRole(String role) { this.role = role; }
}