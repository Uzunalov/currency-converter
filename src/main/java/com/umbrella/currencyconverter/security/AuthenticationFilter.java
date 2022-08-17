package com.umbrella.currencyconverter.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umbrella.currencyconverter.constants.SecurityConstants;
import com.umbrella.currencyconverter.enums.UserRole;
import com.umbrella.currencyconverter.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.usertype.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    //private final CustomUserDetailService userDetailService;
    private final SecurityConstants securityConstants;
    //private final JWTProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)) {
            if (authHeader.startsWith("Basic ")) {
                String encodedCredentials = authHeader.substring(6);
                if (StringUtils.isBlank(encodedCredentials)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Username or password is not correct");
                } else {
                    String[] decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8).split(":");
                    String username = decodedCredentials[0];
                    String password = decodedCredentials[1];
                    if (securityConstants.getUsername().equals(username) && securityConstants.getPassword().equals(password)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.name())));
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } else {
                        ResponseEntity<ErrorResponse> responseEntity =
                                new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Provide valid JWT Header"), HttpStatus.UNAUTHORIZED);
                        response.setStatus(401);
                        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
                        return;
                    }
                }
            } else {
                if(securityConstants.getAccessToken().equals(authHeader)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(authHeader, authHeader, Collections.singletonList(new SimpleGrantedAuthority(UserRole.ADMIN.name())));
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    ResponseEntity<ErrorResponse> responseEntity =
                            new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Provide valid JWT Header"), HttpStatus.UNAUTHORIZED);
                    response.setStatus(401);
                    response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
