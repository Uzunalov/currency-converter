package com.umbrella.currencyconverter.service;

import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.request.CurrencyValuesRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrencyService {
    List<CurrencyValue> saveDailyCurrencyValues(CurrencyValuesRequest currencyValuesRequest);
    void removeDailyCurrencyValues(CurrencyValuesRequest currencyValuesRequest);
    Page<CurrencyValueResponse> getCurrencyValues(CurrencyValuesFilterRequest filterRequest, Pageable pageable);
}
