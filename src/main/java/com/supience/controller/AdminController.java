package com.supience.controller;

import com.supience.dto.*;
import com.supience.entity.Admin;
import com.supience.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminLoginResponse>> login(
            @Valid @RequestBody AdminLoginRequest request,
            HttpServletResponse response) {
        AdminLoginResponse loginResponse = adminService.login(request);
        
        // 세션 ID를 쿠키로 설정
        Cookie sessionCookie = new Cookie("ADMIN_SESSION_ID", loginResponse.getSessionId());
        sessionCookie.setPath("/v1/admin");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setMaxAge(30 * 60); // 30분
        response.addCookie(sessionCookie);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 로그인 성공", loginResponse));
    }

    @Operation(summary = "관리자 로그아웃", description = "관리자 계정을 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        adminService.logout();
        
        // 쿠키 삭제
        Cookie sessionCookie = new Cookie("ADMIN_SESSION_ID", null);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 로그아웃 성공", null));
    }

    @Operation(summary = "관리자 정보 조회", description = "현재 로그인한 관리자의 정보를 조회합니다.")
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AdminLoginResponse>> getAdminInfo() {
        AdminLoginResponse adminInfo = adminService.getAdminInfo();
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 정보 조회 성공", adminInfo));
    }

    @Operation(summary = "관리자 비밀번호 변경", description = "현재 로그인한 관리자의 비밀번호를 변경합니다.")
    @PostMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody AdminPasswordChangeRequest request) {
        adminService.changePassword(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "비밀번호 변경 성공", null));
    }
} 