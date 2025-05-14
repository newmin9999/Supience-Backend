package com.supience.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminLoginResponse {
    private Long id;
    private String name;
    private String sessionId;
    private String role;  // 관리자 역할 정보
} 