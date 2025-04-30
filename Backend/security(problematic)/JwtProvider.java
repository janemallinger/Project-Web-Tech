//package com.example.frogcrew.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//
//@Component
//public class JwtProvider {
//    private final JwtEncoder jwtEncoder;
//
//    @Value("${jwt.expiration:3600}")
//    private long jwtExpiration;
//
//    public JwtProvider(JwtEncoder jwtEncoder) {
//        this.jwtEncoder = jwtEncoder;
//    }
//
//    public String createToken(Authentication authentication) {
//        Instant now = Instant.now();
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(now)
//                .expiresAt(now.plus(jwtExpiration, ChronoUnit.SECONDS))
//                .subject(authentication.getName())
//                .claim("scope", authentication.getAuthorities())
//                .build();
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//}