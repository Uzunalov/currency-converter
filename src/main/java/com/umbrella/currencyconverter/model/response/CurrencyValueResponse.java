package com.umbrella.currencyconverter.model.response;

import com.umbrella.currencyconverter.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyValueResponse {
    private String name;
    private String nominal;
    private String code;
    private CurrencyType type;
    private BigDecimal value;
    private String date;
}
