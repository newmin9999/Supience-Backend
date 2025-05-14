package com.supience.service.impl;

import com.supience.dto.AdminLoginRequest;
import com.supience.dto.AdminLoginResponse;
import com.supience.dto.AdminPasswordChangeRequest;
import com.supience.entity.Admin;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.AdminRepository;
import com.supience.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        // 관리자 계정 조회
        Admin admin = adminRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 로그인 응답 생성
        AdminLoginResponse loginResponse = AdminLoginResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .sessionId(httpSession.getId())
                .role(admin.getRole())
                .build();

        // 세션에 관리자 정보 저장
        httpSession.setAttribute("admin", loginResponse);

        return loginResponse;
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

    @Override
    public AdminLoginResponse getAdminInfo() {
        AdminLoginResponse adminInfo = (AdminLoginResponse) httpSession.getAttribute("admin");
        if (adminInfo == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return adminInfo;
    }

    @Override
    public void changePassword(AdminPasswordChangeRequest request) {
        // 현재 로그인한 관리자 정보 가져오기
        AdminLoginResponse adminInfo = getAdminInfo();
        Admin admin = adminRepository.findById(adminInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 새 비밀번호 암호화 및 저장
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        admin.setPassword(encodedNewPassword);
        adminRepository.save(admin);
    }
} 