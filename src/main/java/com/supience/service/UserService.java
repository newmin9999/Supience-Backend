package com.supience.service;

import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;
import com.supience.dto.SignupRequest;
import com.supience.entity.User;

public interface UserService {
    LoginResponse login(LoginRequest request);
    User signup(SignupRequest request);
    void logout();
} 