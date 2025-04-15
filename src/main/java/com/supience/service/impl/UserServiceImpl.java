package com.supience.service.impl;

import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;
import com.supience.dto.SignupRequest;
import com.supience.entity.User;
import com.supience.exception.BusinessException;
import com.supience.exception.ErrorCode;
import com.supience.repository.UserRepository;
import com.supience.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    public UserServiceImpl(UserRepository userRepository, 
                         PasswordEncoder passwordEncoder,
                         HttpSession httpSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpSession = httpSession;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        LoginResponse loginResponse = LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        httpSession.setAttribute("user", loginResponse);

        return loginResponse;
    }

    @Override
    public User signup(SignupRequest request) {
        // 아이디 중복 체크
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 사용자 생성
        User user = User.builder()
                .loginId(request.getLoginId())
                .password(encodedPassword)
                .name(request.getName())
                .build();

        // 사용자 저장
        return userRepository.save(user);
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }
} 