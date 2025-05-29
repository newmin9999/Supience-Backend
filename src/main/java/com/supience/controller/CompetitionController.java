package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.competition.CompetitionResponse;
import com.supience.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Competition", description = "대회 API")
@RestController
@RequestMapping("/v1/competitions")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @Operation(summary = "대회 목록 조회", description = "모든 대회 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<CompetitionResponse>> getAllCompetitions() {
        return ApiResponse.success(competitionService.getAllCompetitions());
    }

    @Operation(summary = "대회 상세 조회", description = "특정 대회의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<CompetitionResponse> getCompetition(@PathVariable Long id) {
        return ApiResponse.success(competitionService.getCompetition(id));
    }
} 