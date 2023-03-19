package com.danram.server.service.user;

import com.danram.server.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public interface CustomUserDetailsService extends UserDetailsService {
    @Transactional
    public UserDetails loadUserByUsername(String username);
}
