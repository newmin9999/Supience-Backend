package com.supience.service;

import com.supience.dto.competition.CreateCompetitionRequest;
import com.supience.dto.competition.CompetitionResponse;

import java.util.List;

public interface CompetitionService {
    List<CompetitionResponse> getAllCompetitions();
    CompetitionResponse getCompetition(Long id);
    CompetitionResponse createCompetition(CreateCompetitionRequest request);
    CompetitionResponse updateCompetition(Long id, CreateCompetitionRequest request);
    void deleteCompetition(Long id);
} 