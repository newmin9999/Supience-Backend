package com.supience.controller;

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
    public ResponseEntity<List<NoticeResponse>> getNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(
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
        
        return ResponseEntity.ok(noticeService.createNotice(request, adminId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody CreateNoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
} 