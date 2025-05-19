package com.supience.service.impl;

import com.supience.dto.notice.CreateNoticeRequest;
import com.supience.dto.notice.NoticeResponse;
import com.supience.entity.Admin;
import com.supience.entity.Notice;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.AdminRepository;
import com.supience.repository.NoticeRepository;
import com.supience.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    @Override
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다."));
        return new NoticeResponse(notice);
    }

    @Override
    @Transactional
    public NoticeResponse createNotice(CreateNoticeRequest request, Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADMIN_NOT_FOUND));

        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setCreatedBy(admin);

        Notice savedNotice = noticeRepository.save(notice);
        return new NoticeResponse(savedNotice);
    }

    @Override
    @Transactional
    public NoticeResponse updateNotice(Long id, CreateNoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다."));
        
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        
        return new NoticeResponse(notice);
    }

    @Override
    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다."));
        noticeRepository.delete(notice);
    }
} 