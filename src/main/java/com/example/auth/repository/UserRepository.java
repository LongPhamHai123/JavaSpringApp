package com.example.auth.repository;

import com.example.auth.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface UserRepository extends MongoRepository<User, String>{
    // Optional<User> findByUsername(String username);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}


// package com.example.auth.repository;

// import com.example.auth.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.Optional;

// @Repository
// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByUsername(String username);
// }
