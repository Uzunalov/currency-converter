package com.umbrella.currencyconverter.security;

import com.umbrella.currencyconverter.constants.SecurityConstants;
import com.umbrella.currencyconverter.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final SecurityConstants securityConstants;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(
                securityConstants.getUsername(),
                securityConstants.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.name()))
        );
    }
}
