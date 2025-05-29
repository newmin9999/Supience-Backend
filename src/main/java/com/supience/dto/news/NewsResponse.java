package com.supience.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supience.entity.News;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class NewsResponse {
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

    public static NewsResponse from(News news) {
        NewsResponse.NewsResponseBuilder builder = NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt());

        if (news.getSchedule() != null) {
            builder.hasSchedule(true)
                   .startTime(news.getSchedule().getStartTime().toLocalDate())
                   .endTime(news.getSchedule().getEndTime().toLocalDate());
        } else {
            builder.hasSchedule(false);
        }

        return builder.build();
    }
} 