package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginRequestDTO;


public interface AuthService {
    String authenticate(LoginRequestDTO loginRequestDTO);

    void validateToken(String token);
}
