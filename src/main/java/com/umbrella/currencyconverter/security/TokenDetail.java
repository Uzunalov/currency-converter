package com.umbrella.currencyconverter.security;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TokenDetail {
    private String token;
    private Date expirationDate;
    private Date createDate;
}
