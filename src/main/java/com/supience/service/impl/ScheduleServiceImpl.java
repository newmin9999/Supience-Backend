package com.supience.service.impl;

import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;
import com.supience.entity.Schedule;
import com.supience.entity.User;
import com.supience.repository.ScheduleRepository;
import com.supience.repository.UserRepository;
import com.supience.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public ScheduleResponse createSchedule(Long userId, ScheduleRequest request) {
        Schedule schedule = Schedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .maxParticipants(request.getMaxParticipants())
                .currentParticipants(0)
                .user(userRepository.getReferenceById(userId))
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

    @Override
    public void deleteSchedule(Long userId, Long scheduleId) {
        log.debug("Delete schedule with userId: {}, scheduleId: {}", userId, scheduleId);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없습니다."));
        log.debug("Found schedule: {}", schedule);
        /* TODO : createdBy 대신 userID 사용하는 것으로 변경 필요 */
        //if(schedule.getCreatedBy() != userId)
        //    throw new BadCredentialsException("사용자 권한이 없습니다"); // 이 에러가 맞나?

        scheduleRepository.delete(schedule);
        log.debug("Deleted schedule: {}", schedule);
    }
} 