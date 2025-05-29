package com.supience.dto.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supience.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponse {
    private Long id;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    private boolean hasSchedule;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    public static NoticeResponse from(Notice notice) {
        NoticeResponse.NoticeResponseBuilder builder = NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt());

        if (notice.getSchedule() != null) {
            builder.hasSchedule(true)
                   .startTime(notice.getSchedule().getStartTime().toLocalDate())
                   .endTime(notice.getSchedule().getEndTime().toLocalDate());
        } else {
            builder.hasSchedule(false);
        }

        return builder.build();
    }
} 