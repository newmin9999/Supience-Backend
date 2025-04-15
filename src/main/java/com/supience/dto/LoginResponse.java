package com.supience.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private Long id;
    private String name;
    private String sessionId;  // 프론트엔드에서 쿠키로 사용할 세션 ID
}