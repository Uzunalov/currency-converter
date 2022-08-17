package com.umbrella.currencyconverter.service.impl;

import com.umbrella.currencyconverter.converter.CurrencyValueResponseConverter;
import com.umbrella.currencyconverter.dao.CurrencyValueRepository;
import com.umbrella.currencyconverter.enums.CurrencyType;
import com.umbrella.currencyconverter.error.exception.ResourceAlreadyExistsException;
import com.umbrella.currencyconverter.error.exception.ResourceNotFoundException;
import com.umbrella.currencyconverter.model.cbar.CBARCurrencyResponse;
import com.umbrella.currencyconverter.model.entity.Currency;
import com.umbrella.currencyconverter.model.entity.CurrencyValue;
import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import com.umbrella.currencyconverter.service.specification.CurrencyValueSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyValueServiceImplTest {
    @InjectMocks
    private CurrencyValueServiceImpl currencyValueService;
    @Mock
    private CurrencyValueRepository currencyValueRepository;
    @Mock
    private CurrencyValueResponseConverter currencyValueResponseConverter;

    CurrencyValue currencyValueMock = mock(CurrencyValue.class);
    LocalDate date = LocalDate.of(2022, 5, 25);
    BigDecimal value = BigDecimal.valueOf(1.7);

    @Test
    void testSaveNewCurrencyValue() {
        Currency currency = Currency.builder()
                .type(CurrencyType.CURRENCY)
                .code("USD")
                .type(CurrencyType.CURRENCY)
                .name("1 ABŞ dolları")
                .nominal("1").build();

        when(currencyValueMock.getCurrency()).thenReturn(currency);
        when(currencyValueMock.getId()).thenReturn("test");
        when(currencyValueRepository.save(ArgumentMatchers.any(CurrencyValue.class))).thenReturn(currencyValueMock);
        CurrencyValue currencyValue = currencyValueService.saveNewCurrencyValue(currency, value, date);

        Assertions.assertEquals(currencyValue.getId(), "test");
        Assertions.assertEquals(currencyValue.getCurrency().getCode(), currency.getCode());
    }

    @Test
    void testSaveNewCurrencyValueWhenResourceAlreadyExists() {
        Currency currency = Currency.builder()
                .id("test")
                .type(CurrencyType.CURRENCY)
                .code("USD")
                .type(CurrencyType.CURRENCY)
                .name("1 ABŞ dolları")
                .nominal("1").build();

        when(currencyValueRepository.findByCalculationDateAndCurrencyId(ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(currencyValueMock));
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () ->
                currencyValueService.saveNewCurrencyValue(currency,
                        value,
                        date));
    }

    @Test
    void testRemoveDailyCurrencyValues() {
        List<CurrencyValue> currencyValues = List.of(currencyValueMock);
        Mockito.doNothing().when(currencyValueRepository).deleteAll(currencyValues);
        when(currencyValueRepository.findAllByCalculationDate(ArgumentMatchers.any(LocalDate.class))).thenReturn(currencyValues);
        currencyValueService.removeDailyCurrencyValues(date);

        Mockito.verify(currencyValueRepository, Mockito.times(1)).deleteAll(currencyValues);
    }

    @Test
    void testRemoveDailyCurrencyValuesWhenResourceNotFound() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> currencyValueService.removeDailyCurrencyValues(date));
    }

    @Test
    void testGetCurrencyValuesWithoutSort() {
        CurrencyValuesFilterRequest filterRequest = new CurrencyValuesFilterRequest("USD", date);
        Page<CurrencyValue> currencyValues = new PageImpl<>(List.of(
                CurrencyValue.builder()
                        .value(value)
                        .calculationDate(date).build()
        ));

        when(currencyValueRepository.findAll(ArgumentMatchers.any(CurrencyValueSpecification.class), ArgumentMatchers.any(Pageable.class))).thenReturn(currencyValues);

        Page<CurrencyValueResponse> responsePage = currencyValueService.getCurrencyValues(filterRequest, PageRequest.of(0, 20));

        Assertions.assertEquals(responsePage.getTotalElements(), 1L);
    }
}