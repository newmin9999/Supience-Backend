package com.supience.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 인증 관련 에러
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    
    // 관리자 관련 에러
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자를 찾을 수 없습니다."),
    ADMIN_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "관리자 비밀번호가 일치하지 않습니다."),
    
    // 회원가입 관련 에러
    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "이름 형식이 올바르지 않습니다."),
    
    // 공통 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_SCHEDULE_REQUEST(HttpStatus.BAD_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다."),
    INVALID_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다."),

    // 파일 관련 에러
    INVALID_FILE(HttpStatus.BAD_REQUEST, "유효하지 않은 파일입니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),

    // 공지사항 관련 에러
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."),
    NOTICE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 공지사항입니다."),

    // 일정 관련 에러
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
    SCHEDULE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 일정입니다."),
    SCHEDULE_TIME_CONFLICT(HttpStatus.CONFLICT, "일정 시간이 중복됩니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
} 