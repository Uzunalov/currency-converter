package com.umbrella.currencyconverter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyValuesRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate calculationDate;
}
