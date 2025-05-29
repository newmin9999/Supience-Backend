package com.supience.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    private String title;
    private String description;
    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalDateTime startTime;
    @NotNull(message = "종료 시간은 필수입니다.")
    private LocalDateTime endTime;
    private Integer maxParticipants;
} 