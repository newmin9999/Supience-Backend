package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.news.NewsResponse;
import com.supience.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "News", description = "뉴스 API")
@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
public class NewsController {
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
} 