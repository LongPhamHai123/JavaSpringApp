package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
                // .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        // log.info("User {} found", username);
        // log.info("LoadUserByUsername executed for user: {}", username);
        // return new org.springframework.security.core.userdetails.User(
        //         user.getUsername(),
        //         user.getPassword(),
        //         user.isEnabled(),
        //         true, // accountNonExpired
        //         true, // credentialsNonExpired
        //         true, // accountNonLocked
        //         Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        // );
           return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
    }
}


// package com.example.auth.service;

// import com.example.auth.model.User;
// import com.example.auth.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

//         return org.springframework.security.core.userdetails.User.builder()
//                 .username(user.getUsername())
//                 .password(user.getPassword())
//                 .roles(user.getRole())
//                 .disabled(!user.isEnabled())
//                 .build();
//     }
// }

