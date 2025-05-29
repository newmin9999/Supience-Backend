package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.AdminLoginResponse;
import com.supience.dto.notice.CreateNoticeRequest;
import com.supience.dto.notice.NoticeResponse;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.entity.Admin;
import com.supience.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/notices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminNoticeController {
    private final NoticeService noticeService;
    private final HttpSession httpSession;

    @GetMapping
    public ApiResponse<List<NoticeResponse>> getAllNotices() {
        List<NoticeResponse> notices = noticeService.getAllNotices();
        return ApiResponse.success("공지사항 목록 조회 성공", notices);
    }

    @GetMapping("/{id}")
    public ApiResponse<NoticeResponse> getNotice(@PathVariable Long id) {
        NoticeResponse notice = noticeService.getNotice(id);
        return ApiResponse.success("공지사항 조회 성공", notice);
    }

    @PostMapping
    public ApiResponse<NoticeResponse> createNotice(
            @Valid @RequestBody CreateNoticeRequest request) {
        Long adminId = (Long) httpSession.getAttribute("adminId");
        if (adminId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        
        // content 데이터 크기 로깅
        String content = request.getContent();
        if (content != null) {
            int byteLength = content.getBytes().length;
            System.out.println("Content length in bytes: " + byteLength);
            System.out.println("Content length in characters: " + content.length());
        }
        
        NoticeResponse notice = noticeService.createNotice(request);
        return ApiResponse.success("공지사항 생성 성공", notice);
    }

    // @PutMapping("/{id}")
    // public ApiResponse<NoticeResponse> updateNotice(
    //         @PathVariable Long id,
    //         @RequestBody CreateNoticeRequest request) {
    //     NoticeResponse notice = noticeService.updateNotice(request);
    //     return ApiResponse.success("공지사항 수정 성공", notice);
    // }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ApiResponse.success("공지사항 삭제 성공", null);
    }
} 