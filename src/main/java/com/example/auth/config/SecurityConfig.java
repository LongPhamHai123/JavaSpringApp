package com.example.auth.config;

import com.example.auth.jwt.AuthTokenFilter;
import com.example.auth.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthTokenFilter authTokenFilter;
    public SecurityConfig(AuthTokenFilter authTokenFilter, CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain configured");
        http
            .csrf(csrf -> csrf.disable()) // Disable for API testing, enable in production
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/api/register", "/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                // .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
            // .exceptionHandling(exception -> 
            //     exception.authenticationEntryPoint((request, response, authException) -> {
            //         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
            //                         "Internal Server Error");
            //     })
            // );
            // .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("Setting up AuthenticationProvider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }



}


// package com.example.auth.config;


// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// // import com.example.auth.model.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.Customizer;

// @Configuration
// @EnableWebSecurity
// @Slf4j
// public class SecurityConfig {

//     // @Autowired
//     // private CustomUserDetailsService userDetailsService;
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
//     public UserDetailsService userDetailsService() {
//         log.info("Setting up in-memory user details service");
//         UserDetails user = User.builder()
//             .username("user")
//             .password(passwordEncoder().encode("password"))
//             .roles("USER")
//             .build();

//         UserDetails admin = User.builder()
//             .username("admin")
//             .password(passwordEncoder().encode("admin123"))
//             .roles("ADMIN", "USER")
//             .build();

//         return new InMemoryUserDetailsManager(user, admin);
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/public/**", "/h2-console/**").permitAll()
//                 .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                 .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
//                 .anyRequest().authenticated()
//             )
//             .httpBasic(Customizer.withDefaults())
//             .csrf(csrf -> csrf
//                 .ignoringRequestMatchers("/h2-console/**")
//                 .disable()
//             );
//             // .headers(headers -> headers
//             //     .frameOptions(frame -> frame.sameOrigin())
//             // );

//         return http.build();
//     }
//     // @Bean
//     // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//     //     AuthenticationManagerBuilder authenticationManagerBuilder = 
//     //         http.getSharedObject(AuthenticationManagerBuilder.class);
//     //     authenticationManagerBuilder
//     //         .userDetailsService(userDetailsService)
//     //         .passwordEncoder(passwordEncoder());
//     //     return authenticationManagerBuilder.build();
//     // }
// }
