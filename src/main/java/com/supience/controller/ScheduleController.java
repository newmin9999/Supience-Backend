package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.ScheduleRequest;
import com.supience.dto.ScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "운동 일정 관리 API")
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    @Operation(summary = "운동 일정 생성", description = "새로운 운동 일정을 생성합니다.")
    @PostMapping
    public ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        // TODO: 실제 구현 필요
        return ApiResponse.success(new ScheduleResponse());
    }

    @Operation(summary = "운동 일정 목록 조회", description = "모든 운동 일정 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getSchedules() {
        // TODO: 실제 구현 필요
        return ApiResponse.success(List.of());
    }
} 