package com.umbrella.currencyconverter.service.impl;

import com.umbrella.currencyconverter.converter.CurrencyValueResponseConverter;
import com.umbrella.currencyconverter.dao.CurrencyValueRepository;
import com.umbrella.currencyconverter.error.exception.ResourceAlreadyExistsException;
import com.umbrella.currencyconverter.error.exception.ResourceNotFoundException;
import com.umbrella.currencyconverter.model.entity.Currency;
import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import com.umbrella.currencyconverter.service.CurrencyValueService;
import com.umbrella.currencyconverter.service.specification.CurrencyValueSpecification;
import com.umbrella.currencyconverter.util.DateHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyValueServiceImpl implements CurrencyValueService {
    private final CurrencyValueRepository currencyValueRepository;
    private final CurrencyValueResponseConverter currencyValueResponseConverter;

    public CurrencyValueServiceImpl(CurrencyValueRepository currencyValueRepository,
                                    CurrencyValueResponseConverter currencyValueResponseConverter) {
        this.currencyValueRepository = currencyValueRepository;
        this.currencyValueResponseConverter = currencyValueResponseConverter;
    }

    @Override
    public CurrencyValue saveNewCurrencyValue(Currency currency, BigDecimal value, LocalDate calculationDate) {
        CurrencyValue currencyValue = currencyValueRepository.findByCalculationDateAndCurrencyId(calculationDate, currency.getId()).orElse(null);
        if(currencyValue != null) {
            throw new ResourceAlreadyExistsException("Currency values already exists");
        }
        return currencyValueRepository.save(CurrencyValue.builder()
                .currency(currency)
                .value(value)
                .insertDate(DateHelper.getUtcNow())
                .calculationDate(calculationDate).build());
    }

    @Override
    public void removeDailyCurrencyValues(LocalDate calculationDate) {
        List<CurrencyValue> currencyValues = currencyValueRepository.findAllByCalculationDate(calculationDate);
        if(currencyValues != null && currencyValues.size() > 0) {
            currencyValueRepository.deleteAll(currencyValues);
        } else {
            throw new ResourceNotFoundException("Currency values not found");
        }
    }

    @Override
    public Page<CurrencyValueResponse> getCurrencyValues(CurrencyValuesFilterRequest filterRequest, Pageable pageable) {
        if(pageable.getSort() == null || Sort.unsorted().equals(pageable.getSort())) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("calculationDate").descending());
        }
        return currencyValueRepository.findAll(new CurrencyValueSpecification(filterRequest), pageable)
                .map(currencyValueResponseConverter);
    }
}
