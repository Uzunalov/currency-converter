package com.umbrella.currencyconverter.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SecurityConstants {
    @Value("${security.username}")
    private String username;

    @Value("${security.password}")
    private String password;

    @Value("${security.auth.whitelist}")
    private String[] whitelist;

    @Value("${security.access-token}")
    private String accessToken;
}
