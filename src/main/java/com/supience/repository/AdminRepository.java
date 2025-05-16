package com.supience.repository;

import com.supience.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    Optional<Admin> findByName(String name);
} 