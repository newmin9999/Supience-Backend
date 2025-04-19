package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.LoginResponse;
import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;
import com.supience.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "운동 일정 관리 API")
@RestController
@RequestMapping("/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final HttpSession httpSession;

    @Operation(summary = "운동 일정 생성", description = "새로운 운동 일정을 생성합니다.")
    @PostMapping
    public ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        return ApiResponse.success(scheduleService.createSchedule(request));
    }

    @Operation(summary = "운동 일정 목록 조회", description = "모든 운동 일정 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getSchedules() {
        return ApiResponse.success(scheduleService.getSchedules());
    }

    @Operation(summary = "운동 일정 상세 조회", description = "특정 운동 일정의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<ScheduleResponse> getSchedule(@PathVariable Long id) {
        return ApiResponse.success(scheduleService.getSchedule(id));
    }

    @Operation(summary = "운동 일정 제거", description = "특정 운동 일정을 제거합니다.")
    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(((LoginResponse) httpSession.getAttribute("user")).getId(), scheduleId);
        return ApiResponse.success(null);
    }
} 