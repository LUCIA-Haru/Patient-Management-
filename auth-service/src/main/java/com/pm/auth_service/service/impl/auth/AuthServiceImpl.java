package com.pm.auth_service.service.impl.auth;

import com.pm.auth_service.dto.LoginRequestDTO;
import com.pm.auth_service.exception.InvalidCredentialException;
import com.pm.auth_service.exception.UnAuthorizedException;
import com.pm.auth_service.model.User;
import com.pm.auth_service.service.AuthService;
import com.pm.auth_service.service.UserService;
import com.pm.auth_service.utils.jwt.JWTUtil;
import com.pm.auth_service.utils.message.ErrorMessageUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public String authenticate(LoginRequestDTO loginRequestDTO) {
        User user = userService.findByEmail(loginRequestDTO.getEmail());

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword()))
        {
            throw new InvalidCredentialException(ErrorMessageUtils.INVALID.formatted("Password"));
        }
        return jwtUtil.generateToken(user.getEmail(),user.getRole());
    }


    @Override
    public void validateToken(String token) {
      try{
          jwtUtil.validateToken(token);
      }catch (JwtException ex){
          throw new UnAuthorizedException("UnAuthorized User");
      }
    }

}
