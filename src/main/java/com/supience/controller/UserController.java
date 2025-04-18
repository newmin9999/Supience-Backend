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

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", loginResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody SignupRequest request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입 성공", user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        userService.logout();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 성공", null));
    }
} 