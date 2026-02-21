// package com.example.auth.config;

// import com.example.auth.model.User;
// import com.example.auth.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Override
//     public void run(String... args) throws Exception {
//         // Create sample users
//         if (userRepository.count() == 0) {
//             User user1 = new User(
//                 "user",
//                 passwordEncoder.encode("password"),
//                 "USER"
//             );

//             User user2 = new User(
//                 "admin",
//                 passwordEncoder.encode("admin123"),
//                 "ADMIN"
//             );

//             User user3 = new User(
//                 "john",
//                 passwordEncoder.encode("john123"),
//                 "USER"
//             );

//             userRepository.save(user1);
//             userRepository.save(user2);
//             userRepository.save(user3);

//             System.out.println("=================================================");
//             System.out.println("Sample users created:");
//             System.out.println("1. Username: user, Password: password, Role: USER");
//             System.out.println("2. Username: admin, Password: admin123, Role: ADMIN");
//             System.out.println("3. Username: john, Password: john123, Role: USER");
//             System.out.println("=================================================");
//         }
//     }
// }
