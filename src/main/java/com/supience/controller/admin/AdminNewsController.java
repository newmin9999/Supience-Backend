package com.supience.controller.admin;

import com.supience.dto.ApiResponse;
import com.supience.dto.news.CreateNewsRequest;
import com.supience.dto.news.NewsResponse;
import com.supience.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin News", description = "관리자 뉴스 API")
@RestController
@RequestMapping("/v1/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {
    private final NewsService newsService;

    @Operation(summary = "뉴스 목록 조회", description = "모든 뉴스 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<NewsResponse>> getAllNews() {
        return ApiResponse.success(newsService.getAllNews());
    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<NewsResponse> getNews(@PathVariable Long id) {
        return ApiResponse.success(newsService.getNews(id));
    }

    @Operation(summary = "뉴스 생성", description = "새로운 뉴스를 생성합니다.")
    @PostMapping
    public ApiResponse<NewsResponse> createNews(@Valid @RequestBody CreateNewsRequest request) {
        return ApiResponse.success(newsService.createNews(request));
    }

    @Operation(summary = "뉴스 수정", description = "기존 뉴스의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ApiResponse<NewsResponse> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody CreateNewsRequest request) {
        return ApiResponse.success(newsService.updateNews(id, request));
    }

    @Operation(summary = "뉴스 삭제", description = "뉴스를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ApiResponse.success();
    }
} 