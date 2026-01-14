package com.pm.auth_service.controller;

import com.pm.auth_service.dto.LoginRequestDTO;
import com.pm.auth_service.dto.LoginResponseDTO;
import com.pm.auth_service.exception.UnAuthorizedException;
import com.pm.auth_service.service.AuthService;
import com.pm.auth_service.utils.message.ErrorMessageUtils;
import com.pm.auth_service.utils.message.SuccessMessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
       String token = authService.authenticate(loginRequestDTO);
       log.info(SuccessMessageUtils.SUCCESS_OPERATION.formatted("Login" , "Process"));
       return new LoginResponseDTO(token);
    }

    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public void validateToken(
            @RequestHeader("Authorization") String authHeader
    ){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new UnAuthorizedException(ErrorMessageUtils.UNAUTHORIZED);
        }

        authService.validateToken(authHeader.substring(7));
        log.info(SuccessMessageUtils.SUCCESS_OPERATION.formatted("Validation" , "Process"));

    }
}
