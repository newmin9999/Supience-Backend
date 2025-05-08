package com.supience.controller;

import com.supience.dto.UserDto;
import com.supience.entity.User;
import com.supience.service.UserService;
import com.supience.dto.LoginRequest;
import com.supience.dto.SignupRequest;
import com.supience.dto.ApiResponse;
import com.supience.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = userService.login(request);
        
        // 세션 ID를 쿠키로 설정
        Cookie sessionCookie = new Cookie("JSESSIONID", loginResponse.getSessionId());
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true); // HTTPS에서만 쿠키 전송
        sessionCookie.setMaxAge(30 * 60); // 30분
        response.addCookie(sessionCookie);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", loginResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody SignupRequest request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입 성공", user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        userService.logout();
        SecurityContextHolder.clearContext();
        
        // 쿠키 삭제
        Cookie sessionCookie = new Cookie("JSESSIONID", null);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 성공", null));
    }
} 