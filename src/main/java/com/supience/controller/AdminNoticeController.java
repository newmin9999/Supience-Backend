package com.supience.controller;

import com.supience.dto.notice.CreateNoticeRequest;
import com.supience.dto.notice.NoticeResponse;
import com.supience.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/notices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminNoticeController {
    private final NoticeService noticeService;

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
            @Valid @RequestBody CreateNoticeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(noticeService.createNotice(request, userDetails.getUsername()));
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