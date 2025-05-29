package com.supience.controller.admin;

import com.supience.dto.ApiResponse;
import com.supience.dto.competition.CreateCompetitionRequest;
import com.supience.dto.competition.CompetitionResponse;
import com.supience.service.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Competition", description = "관리자 대회 API")
@RestController
@RequestMapping("/v1/admin/competitions")
@RequiredArgsConstructor
public class AdminCompetitionController {
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

    @Operation(summary = "대회 생성", description = "새로운 대회를 생성합니다.")
    @PostMapping
    public ApiResponse<CompetitionResponse> createCompetition(@Valid @RequestBody CreateCompetitionRequest request) {
        return ApiResponse.success(competitionService.createCompetition(request));
    }

    @Operation(summary = "대회 수정", description = "기존 대회의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ApiResponse<CompetitionResponse> updateCompetition(
            @PathVariable Long id,
            @Valid @RequestBody CreateCompetitionRequest request) {
        return ApiResponse.success(competitionService.updateCompetition(id, request));
    }

    @Operation(summary = "대회 삭제", description = "대회를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return ApiResponse.success();
    }
} 