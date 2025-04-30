//package com.example.frogcrew.security;
//
//import com.example.frogcrew.security.model.User;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class AuthService {
//    private final JwtProvider jwtProvider;
//
//    public AuthService(JwtProvider jwtProvider) {
//        this.jwtProvider = jwtProvider;
//    }
//
//    public Map<String, Object> createLoginInfo(Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//
//        // Generate JWT token
//        String token = this.jwtProvider.createToken(authentication);
//
//        // Create login response
//        Map<String, Object> loginInfo = new HashMap<>();
//        loginInfo.put("userId", user.getId());
//        loginInfo.put("username", user.getUsername());
//        loginInfo.put("role", user.getRole());
//        loginInfo.put("token", token);
//
//        return loginInfo;
//    }
//}