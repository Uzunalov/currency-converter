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
import com.umbrella.currencyconverter.service.CurrencyValueService;
import com.umbrella.currencyconverter.service.cbar.CBARCurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceImplTest {
    @InjectMocks
    private CurrencyServiceImpl currencyService;
    @Mock
    private CBARCurrencyService cbarCurrencyService;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyValueService currencyValueService;

    CurrencyValuesRequest currencyValuesRequest = new CurrencyValuesRequest(LocalDate.of(2022, 5, 25));
    CBARCurrencyResponse currencyResponseMock = mock(CBARCurrencyResponse.class);
    Currency currencyMock = mock(Currency.class);
    CurrencyValue currencyValueMock = mock(CurrencyValue.class);

    @Test
    public void testSaveDailyCurrencyValuesWhenCurrencyIsNull() {
        when(currencyResponseMock.getCurrencyValueTypes()).thenReturn(
                List.of(CBARCurrencyValueType.builder().type("Bank metalları")
                                .values(List.of(CBARValue.builder()
                                        .code("XAU")
                                        .name("Qizil")
                                        .value(BigDecimal.valueOf(100L))
                                        .nominal("1 t.u.").build())).build(),
                        CBARCurrencyValueType.builder().type("Xarici valyutalar")
                                .values(List.of(CBARValue.builder()
                                        .code("USD")
                                        .name("1 ABŞ dolları")
                                        .value(BigDecimal.valueOf(1.7))
                                        .nominal("1").build())).build()));

        when(cbarCurrencyService.getCurrencies(ArgumentMatchers.any(String.class))).thenReturn(currencyResponseMock);

        when(currencyValueService.saveNewCurrencyValue(ArgumentMatchers.any(Currency.class),
                ArgumentMatchers.any(BigDecimal.class),
                ArgumentMatchers.any(LocalDate.class))).thenReturn(currencyValueMock);

        when(currencyRepository.save(ArgumentMatchers.any(Currency.class))).thenReturn(currencyMock);

        List<CurrencyValue> currencyValues = currencyService.saveDailyCurrencyValues(currencyValuesRequest);
        Assertions.assertTrue(currencyValues.size() > 0);
    }

    @Test
    public void testSaveDailyCurrencyValuesWhenCurrencyIsNonNull() {
        when(currencyResponseMock.getCurrencyValueTypes()).thenReturn(
                List.of(CBARCurrencyValueType.builder().type("Bank metalları")
                                .values(List.of(CBARValue.builder()
                                        .code("XAU")
                                        .name("Qizil")
                                        .value(BigDecimal.valueOf(100L))
                                        .nominal("1 t.u.").build())).build(),
                        CBARCurrencyValueType.builder().type("Xarici valyutalar")
                                .values(List.of(CBARValue.builder()
                                        .code("USD")
                                        .name("1 ABŞ dolları")
                                        .value(BigDecimal.valueOf(1.7))
                                        .nominal("1").build())).build()));

        when(cbarCurrencyService.getCurrencies(ArgumentMatchers.any(String.class))).thenReturn(currencyResponseMock);

        when(currencyValueService.saveNewCurrencyValue(ArgumentMatchers.any(Currency.class),
                ArgumentMatchers.any(BigDecimal.class),
                ArgumentMatchers.any(LocalDate.class))).thenReturn(currencyValueMock);

        when(currencyRepository.findByCode(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(currencyMock));

        List<CurrencyValue> currencyValues = currencyService.saveDailyCurrencyValues(currencyValuesRequest);
        Assertions.assertTrue(currencyValues.size() > 0);
    }

    @Test
    public void testGetCurrencyValues() {
        CurrencyValuesFilterRequest filterRequest = new CurrencyValuesFilterRequest("USD", LocalDate.of(2022, 5, 25));
        CurrencyValueResponse currencyValueResponse = CurrencyValueResponse.builder()
                .code("USD")
                .type(CurrencyType.CURRENCY)
                .name("1 ABŞ dolları")
                .nominal("1")
                .value(BigDecimal.valueOf(1.7))
                .date("25.05.2022").build();
        when(currencyValueService.getCurrencyValues(ArgumentMatchers.any(CurrencyValuesFilterRequest.class), ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(currencyValueResponse)));

        Page<CurrencyValueResponse> responsePage = currencyService.getCurrencyValues(filterRequest, Pageable.unpaged());

        Assertions.assertEquals(responsePage.getTotalElements(), 1);
    }

    @Test
    public void testRemoveDailyCurrencyValues() {
        Mockito.doNothing().when(currencyValueService).removeDailyCurrencyValues(currencyValuesRequest.getCalculationDate());
        currencyService.removeDailyCurrencyValues(currencyValuesRequest);
        Mockito.verify(currencyValueService, Mockito.times(1)).removeDailyCurrencyValues(currencyValuesRequest.getCalculationDate());
    }
}