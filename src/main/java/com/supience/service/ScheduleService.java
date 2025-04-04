package com.supience.service;

import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse createSchedule(ScheduleRequest request);
    List<ScheduleResponse> getSchedules();
    ScheduleResponse getSchedule(Long id);
} 