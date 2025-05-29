package com.supience.service;

import com.supience.dto.news.CreateNewsRequest;
import com.supience.dto.news.NewsResponse;

import java.util.List;

public interface NewsService {
    List<NewsResponse> getAllNews();
    NewsResponse getNews(Long id);
    NewsResponse createNews(CreateNewsRequest request);
    NewsResponse updateNews(Long id, CreateNewsRequest request);
    void deleteNews(Long id);
} 