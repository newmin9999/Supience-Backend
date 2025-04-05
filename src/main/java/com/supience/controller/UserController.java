package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;
import com.supience.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        LoginResponse response = userService.login(request);
        session.setAttribute("userId", response.getId());
        return ApiResponse.success(response);
    }
} 