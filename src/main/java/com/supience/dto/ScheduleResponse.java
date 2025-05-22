package com.supience.dto;

import com.supience.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long noticeId;

    public static ScheduleResponse from(Schedule schedule) {
        ScheduleResponse.ScheduleResponseBuilder builder = ScheduleResponse.builder()
                .id(schedule.getId())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime());

        if (schedule.getNotice() != null) {
            builder.noticeId(schedule.getNotice().getId());
        }

        return builder.build();
    }
} 