package com.supience.service;

import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse createSchedule(Long userId, ScheduleRequest request);
    List<ScheduleResponse> getSchedules();
    ScheduleResponse getSchedule(Long id);
    void deleteSchedule(Long userId, Long id);
}