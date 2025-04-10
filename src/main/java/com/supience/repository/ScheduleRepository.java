package com.supience.repository;

import com.supience.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStartTimeGreaterThanOrderByStartTimeAsc(LocalDateTime now);
} 