package com.danram.server.service.user;

import com.danram.server.domain.User;
import com.danram.server.dto.UserDto;
import com.danram.server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public interface UserService {
    @Transactional
    public User signUp(UserDto userDto);

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username);

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities();
}
