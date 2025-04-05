package com.supience.service.impl;

import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;
import com.supience.entity.Schedule;
import com.supience.repository.ScheduleRepository;
import com.supience.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Schedule schedule = Schedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .maxParticipants(request.getMaxParticipants())
                .currentParticipants(0)
                .createdBy("admin")
                .build();
        return ScheduleResponse.from(scheduleRepository.save(schedule));
    }

    @Override
    public List<ScheduleResponse> getSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResponse getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없습니다."));
        return ScheduleResponse.from(schedule);
    }
} 