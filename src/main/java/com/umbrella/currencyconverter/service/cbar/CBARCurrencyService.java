package com.umbrella.currencyconverter.service.cbar;

import com.umbrella.currencyconverter.model.cbar.CBARCurrencyResponse;

public interface CBARCurrencyService {
    CBARCurrencyResponse getCurrencies(String date);
}
