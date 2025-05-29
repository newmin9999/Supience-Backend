package com.supience.dto.competition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supience.entity.Competition;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class CompetitionResponse {
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

    public static CompetitionResponse from(Competition competition) {
        CompetitionResponse.CompetitionResponseBuilder builder = CompetitionResponse.builder()
                .id(competition.getId())
                .title(competition.getTitle())
                .content(competition.getContent())
                .createdAt(competition.getCreatedAt())
                .updatedAt(competition.getUpdatedAt());

        if (competition.getSchedule() != null) {
            builder.hasSchedule(true)
                   .startTime(competition.getSchedule().getStartTime().toLocalDate())
                   .endTime(competition.getSchedule().getEndTime().toLocalDate());
        } else {
            builder.hasSchedule(false);
        }

        return builder.build();
    }
} 