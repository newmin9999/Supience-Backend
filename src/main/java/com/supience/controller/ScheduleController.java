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
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "운동 일정 관리 API")
@RestController
@RequestMapping("/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

} 