package com.example.auth.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//This is executed once per HttpRequest
@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final List<String> PUBLIC_URLS = List.of(
            "/api/register",
            "/api/auth/login"
    );

    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) {
    //     String path = request.getServletPath();
    //     return PUBLIC_URLS.contains(path);
    // }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // String path = request.getServletPath();

        // 👇 Cho phép login & register đi qua không cần token
        // if (PUBLIC_URLS.contains(path)) {
        //     filterChain.doFilter(request, response);
        //     return;
        // }

        String header = request.getHeader("Authorization");
        log.info("Calling Auth token filter for url --> {}", request.getRequestURI());
        if (header == null || !header.startsWith("Bearer ")) {
            // throw new RuntimeException("Missing Authorization header");
            // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // response.setContentType("application/json");
            // response.getWriter().write("{\"error\": \"Missing or invalid token\"}");
            // return;
            filterChain.doFilter(request, response);
            return;

        }
        //Get the token from header
        String jwtToken = jwtUtils.getJwtDetailsFromHeader(request);
        //Validate the token
        if(jwtToken!=null && jwtUtils.validateJwtToken(jwtToken)){
            //Get the userNames
            String userName = jwtUtils.extractUserNameFromJwtToken(jwtToken);
            //Get the userDetails object - username, roles etc from the extracted userName
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            //Manually set authentication token in the request context
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            log.info("Token Roles extracted from JWT --> {}", userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // At this point user is authenticated and set in the context
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }else {
            log.info("Jwt token seems invalid. ");
        }

        filterChain.doFilter(request, response);
    }

}
