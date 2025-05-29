package com.supience.service.impl;

import com.supience.dto.competition.CreateCompetitionRequest;
import com.supience.dto.competition.CompetitionResponse;
import com.supience.entity.Competition;
import com.supience.entity.Schedule;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.CompetitionRepository;
import com.supience.service.CompetitionService;
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
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;

    @Override
    public List<CompetitionResponse> getAllCompetitions() {
        return competitionRepository.findAll().stream()
                .map(CompetitionResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public CompetitionResponse getCompetition(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPETITION_NOT_FOUND));
        return CompetitionResponse.from(competition);
    }

    @Override
    @Transactional
    public CompetitionResponse createCompetition(CreateCompetitionRequest request) {
        Competition competition = Competition.builder()
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
            
            competition.setSchedule(schedule);
        }

        Competition savedCompetition = competitionRepository.save(competition);
        return CompetitionResponse.from(savedCompetition);
    }

    @Override
    @Transactional
    public CompetitionResponse updateCompetition(Long id, CreateCompetitionRequest request) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPETITION_NOT_FOUND));
        
        competition.update(request.getTitle(), request.getContent());
        
        if (request.isHasSchedule()) {
            if (request.getStartTime() == null || request.getEndTime() == null) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "일정이 있는 경우 시작 시간과 종료 시간은 필수입니다.");
            }
            if (request.getStartTime().isAfter(request.getEndTime())) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다.");
            }
            
            LocalDateTime startDateTime = LocalDateTime.of(request.getStartTime(), LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(request.getEndTime(), LocalTime.MAX);
            
            Schedule schedule = competition.getSchedule();
            if (schedule == null) {
                schedule = Schedule.builder()
                        .startTime(startDateTime)
                        .endTime(endDateTime)
                        .build();
                competition.setSchedule(schedule);
            } else {
                schedule = Schedule.builder()
                        .startTime(startDateTime)
                        .endTime(endDateTime)
                        .build();
                competition.setSchedule(schedule);
            }
        } else {
            competition.setSchedule(null);
        }
        
        return CompetitionResponse.from(competition);
    }

    @Override
    @Transactional
    public void deleteCompetition(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPETITION_NOT_FOUND));
        
        competitionRepository.delete(competition);
    }
} 