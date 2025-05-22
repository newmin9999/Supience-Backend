package com.supience.service.impl;

import com.supience.dto.notice.CreateNoticeRequest;
import com.supience.dto.notice.NoticeResponse;
import com.supience.entity.Notice;
import com.supience.entity.Schedule;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.NoticeRepository;
import com.supience.service.NoticeService;
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

    @Override
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
        return NoticeResponse.from(notice);
    }

    @Override
    @Transactional
    public NoticeResponse createNotice(CreateNoticeRequest request) {
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        if (request.isHasSchedule()) {
            if (request.getStartTime() == null || request.getEndTime() == null) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다.");
            }
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다.");
            }
            
            Schedule schedule = Schedule.builder()
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .build();
            
            notice.setSchedule(schedule);
        }

        Notice savedNotice = noticeRepository.save(notice);
        return NoticeResponse.from(savedNotice);
    }

    // @Override
    // @Transactional
    // public NoticeResponse updateNotice( CreateNoticeRequest request) {
    //     Notice notice = noticeRepository.findById(id)
    //             .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
        
    //     notice.update(request.getTitle(), request.getContent());
        
    //     if (request.isHasSchedule()) {
    //         if (request.getStartTime() == null || request.getEndTime() == null) {
    //             throw new BusinessException(ErrorCode.INVALID_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다.");
    //         }
    //         if (request.getStartTime().isAfter(request.getEndTime())) {
    //             throw new BusinessException(ErrorCode.INVALID_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다.");
    //         }
            
    //         Schedule schedule = notice.getSchedule();
    //         if (schedule == null) {
    //             schedule = Schedule.builder()
    //                     .startTime(request.getStartTime())
    //                     .endTime(request.getEndTime())
    //                     .build();
    //             notice.setSchedule(schedule);
    //         } else {
    //             schedule = Schedule.builder()
    //                     .startTime(request.getStartTime())
    //                     .endTime(request.getEndTime())
    //                     .build();
    //             notice.setSchedule(schedule);
    //         }
    //     } else {
    //         notice.setSchedule(null);
    //     }
        
    //     return NoticeResponse.from(notice);
    // }

    @Override
    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
        
        noticeRepository.delete(notice);
    }
} 