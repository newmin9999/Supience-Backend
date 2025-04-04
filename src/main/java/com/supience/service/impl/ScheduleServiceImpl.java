package com.supience.service.impl;

import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;
import com.supience.entity.Schedule;
import com.supience.repository.ScheduleRepository;
import com.supience.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Schedule schedule = new Schedule(
            request.getTitle(),
            request.getDescription(),
            request.getStartTime(),
            request.getEndTime(),
            request.getMaxParticipants()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> getSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ScheduleResponse convertToResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .maxParticipants(schedule.getMaxParticipants())
                .currentParticipants(schedule.getCurrentParticipants())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
} 