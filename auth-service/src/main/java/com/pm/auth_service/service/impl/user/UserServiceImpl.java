package com.pm.auth_service.service.impl.user;


import com.pm.auth_service.exception.UserNotFoundException;
import com.pm.auth_service.model.User;
import com.pm.auth_service.repository.UserRepository;
import com.pm.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
     return userRepository.findByEmail(email)
             .orElseThrow(() -> new UserNotFoundException("Email with this user is not found"));

    }
}
