package com.supience.service;

import com.supience.dto.notice.CreateNoticeRequest;
import com.supience.dto.notice.NoticeResponse;

import java.util.List;

public interface NoticeService {
    List<NoticeResponse> getAllNotices();
    NoticeResponse getNotice(Long id);
    NoticeResponse createNotice(CreateNoticeRequest request);
    // NoticeResponse updateNotice(CreateNoticeRequest request);
    void deleteNotice(Long id);
} 