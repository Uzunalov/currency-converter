package com.umbrella.currencyconverter.service;

import com.umbrella.currencyconverter.model.entity.Currency;
import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CurrencyValueService {
    CurrencyValue saveNewCurrencyValue(Currency currency, BigDecimal value, LocalDate calculationDate);
    void removeDailyCurrencyValues(LocalDate calculationDate);
    Page<CurrencyValueResponse> getCurrencyValues(CurrencyValuesFilterRequest filterRequest, Pageable pageable);
}
