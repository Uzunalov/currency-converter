package com.umbrella.currencyconverter.converter;

import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import com.umbrella.currencyconverter.util.DateHelper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CurrencyValueResponseConverter implements Function<CurrencyValue, CurrencyValueResponse> {
    @Override
    public CurrencyValueResponse apply(CurrencyValue currencyValue) {
        return CurrencyValueResponse.builder()
                .code(currencyValue.getCurrency().getCode())
                .name(currencyValue.getCurrency().getName())
                .nominal(currencyValue.getCurrency().getNominal())
                .type(currencyValue.getCurrency().getType())
                .value(currencyValue.getValue())
                .date(DateHelper.getDateString(currencyValue.getCalculationDate())).build();
    }
}
