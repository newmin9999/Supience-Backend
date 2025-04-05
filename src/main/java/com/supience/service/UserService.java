package com.supience.service;

import com.supience.dto.LoginRequest;
import com.supience.dto.LoginResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);
} 