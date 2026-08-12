package com.typingspeedtest.api.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("========== JWT FILTER ==========");
        System.out.println(
                "Request: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println("No Bearer token");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            System.out.println("Token received");

            if (!jwtService.isTokenValid(token)) {

                System.out.println("JWT IS INVALID");

                filterChain.doFilter(request, response);
                return;
            }

            System.out.println("JWT IS VALID");

            String email = jwtService.extractEmail(token);

            System.out.println(
                    "Email from JWT: " + email
            );

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(email);

            System.out.println(
                    "User found: "
                            + userDetails.getUsername()
            );

            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                System.out.println(
                        "SecurityContext authentication set"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT authentication failed: "
                            + e.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}