//package com.example.frogcrew.security;
//
//import com.example.frogcrew.system.Result;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("${api.base-path}/auth")
//public class AuthController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public Result login(Authentication authentication) {
//        LOGGER.debug("Authenticated user: {}", authentication.getName());
//        Map<String, Object> loginInfo = this.authService.createLoginInfo(authentication);
//        return new Result(true, 200, "Login success", loginInfo);
//    }
//}