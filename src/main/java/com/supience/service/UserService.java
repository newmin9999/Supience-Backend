package com.supience.service;

import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;
import com.supience.dto.SignupRequest;

public interface UserService {
    LoginResponse login(LoginRequest request);
    LoginResponse signup(SignupRequest request);
} 