package com.supience.service.impl;

import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;
import com.supience.entity.User;
import com.supience.repository.UserRepository;
import com.supience.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // TODO: 비밀번호 검증 로직 추가
        if (!user.getPassword().equals(request.getPassword())) {
            throw new EntityNotFoundException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
} 