package com.umbrella.currencyconverter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyValuesFilterRequest {
    private String currencyCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
