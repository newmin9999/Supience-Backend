package com.supience.dto.notice;

import com.supience.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean hasSchedule;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static NoticeResponse from(Notice notice) {
        NoticeResponse.NoticeResponseBuilder builder = NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt());

        if (notice.getSchedule() != null) {
            builder.hasSchedule(true)
                   .startTime(notice.getSchedule().getStartTime())
                   .endTime(notice.getSchedule().getEndTime());
        } else {
            builder.hasSchedule(false);
        }

        return builder.build();
    }
} 