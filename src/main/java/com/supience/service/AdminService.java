package com.supience.service;

import com.supience.dto.AdminLoginRequest;
import com.supience.dto.AdminLoginResponse;
import com.supience.dto.AdminPasswordChangeRequest;

public interface AdminService {
    /**
     * 관리자 로그인
     * @param request 로그인 요청 정보
     * @return 로그인 응답 정보
     */
    AdminLoginResponse login(AdminLoginRequest request);

    /**
     * 관리자 로그아웃
     */
    void logout();

    /**
     * 현재 로그인한 관리자 정보 조회
     * @return 관리자 정보
     */
    AdminLoginResponse getAdminInfo();

    /**
     * 관리자 비밀번호 변경
     * @param request 비밀번호 변경 요청 정보
     */
    void changePassword(AdminPasswordChangeRequest request);
} 