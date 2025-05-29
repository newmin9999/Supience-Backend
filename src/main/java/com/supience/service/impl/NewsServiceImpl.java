package com.supience.service.impl;

import com.supience.dto.news.CreateNewsRequest;
import com.supience.dto.news.NewsResponse;
import com.supience.entity.News;
import com.supience.entity.Schedule;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.NewsRepository;
import com.supience.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll().stream()
                .map(NewsResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponse getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        return NewsResponse.from(news);
    }

    @Override
    @Transactional
    public NewsResponse createNews(CreateNewsRequest request) {
        News news = News.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        if (request.isHasSchedule()) {
            if (request.getStartTime() == null || request.getEndTime() == null) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다.");
            }
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다.");
            }
            
            LocalDateTime startDateTime = LocalDateTime.of(request.getStartTime(), LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(request.getEndTime(), LocalTime.MAX);
            
            Schedule schedule = Schedule.builder()
                    .startTime(startDateTime)
                    .endTime(endDateTime)
                    .build();
            
            news.setSchedule(schedule);
        }

        News savedNews = newsRepository.save(news);
        return NewsResponse.from(savedNews);
    }

    @Override
    @Transactional
    public NewsResponse updateNews(Long id, CreateNewsRequest request) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        
        news.update(request.getTitle(), request.getContent());
        
        if (request.isHasSchedule()) {
            if (request.getStartTime() == null || request.getEndTime() == null) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다.");
            }
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다.");
            }
            
            LocalDateTime startDateTime = LocalDateTime.of(request.getStartTime(), LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(request.getEndTime(), LocalTime.MAX);
            
            Schedule schedule = news.getSchedule();
            if (schedule == null) {
                schedule = Schedule.builder()
                        .startTime(startDateTime)
                        .endTime(endDateTime)
                        .build();
                news.setSchedule(schedule);
            } else {
                schedule = Schedule.builder()
                        .startTime(startDateTime)
                        .endTime(endDateTime)
                        .build();
                news.setSchedule(schedule);
            }
        } else {
            news.setSchedule(null);
        }
        
        return NewsResponse.from(news);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        
        newsRepository.delete(news);
    }
} 