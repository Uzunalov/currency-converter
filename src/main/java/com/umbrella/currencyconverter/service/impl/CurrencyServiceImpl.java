package com.umbrella.currencyconverter.service.impl;

import com.umbrella.currencyconverter.dao.CurrencyRepository;
import com.umbrella.currencyconverter.enums.CurrencyType;
import com.umbrella.currencyconverter.model.cbar.CBARCurrencyResponse;
import com.umbrella.currencyconverter.model.cbar.CBARCurrencyValueType;
import com.umbrella.currencyconverter.model.cbar.CBARValue;
import com.umbrella.currencyconverter.model.entity.Currency;
import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.request.CurrencyValuesRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import com.umbrella.currencyconverter.service.CurrencyService;
import com.umbrella.currencyconverter.service.CurrencyValueService;
import com.umbrella.currencyconverter.service.cbar.CBARCurrencyService;
import com.umbrella.currencyconverter.util.DateHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CBARCurrencyService cbarCurrencyService;
    private final CurrencyRepository currencyRepository;
    private final CurrencyValueService currencyValueService;

    public CurrencyServiceImpl(CBARCurrencyService cbarCurrencyService, CurrencyRepository currencyRepository,
                               CurrencyValueService currencyValueService) {
        this.cbarCurrencyService = cbarCurrencyService;
        this.currencyRepository = currencyRepository;
        this.currencyValueService = currencyValueService;
    }

    @Override
    public List<CurrencyValue> saveDailyCurrencyValues(CurrencyValuesRequest currencyValuesRequest) {
        List<CurrencyValue> currencyValues = new ArrayList<>();
        CBARCurrencyResponse currencyResponse = cbarCurrencyService.getCurrencies(DateHelper.getDateString(currencyValuesRequest.getCalculationDate()));
        for(CBARCurrencyValueType valueType: currencyResponse.getCurrencyValueTypes()) {
            for (CBARValue value: valueType.getValues()) {
                Currency currency = currencyRepository.findByCode(value.getCode()).orElse(null);
                if (currency == null) {
                    currency = currencyRepository.save(Currency.builder()
                            .code(value.getCode())
                            .name(value.getName())
                            .nominal(value.getNominal())
                            .type(("Bank metalları".equals(valueType.getType())) ? CurrencyType.METAL : CurrencyType.CURRENCY).build());
                }
                currencyValues.add(currencyValueService.saveNewCurrencyValue(currency, value.getValue(), currencyValuesRequest.getCalculationDate()));
            }
        }
        return currencyValues;
    }

    @Override
    public void removeDailyCurrencyValues(CurrencyValuesRequest currencyValuesRequest) {
        currencyValueService.removeDailyCurrencyValues(currencyValuesRequest.getCalculationDate());
    }

    @Override
    public Page<CurrencyValueResponse> getCurrencyValues(CurrencyValuesFilterRequest filterRequest, Pageable pageable) {
        return currencyValueService.getCurrencyValues(filterRequest, pageable);
    }
}
