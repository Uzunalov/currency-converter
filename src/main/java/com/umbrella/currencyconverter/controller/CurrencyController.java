package com.umbrella.currencyconverter.controller;

import com.umbrella.currencyconverter.model.request.CurrencyValuesFilterRequest;
import com.umbrella.currencyconverter.model.request.CurrencyValuesRequest;
import com.umbrella.currencyconverter.model.response.CurrencyValueResponse;
import com.umbrella.currencyconverter.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> saveCurrencyValues(@RequestBody @Valid CurrencyValuesRequest currencyValuesRequest) {
        currencyService.saveDailyCurrencyValues(currencyValuesRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeCurrencyValues(@RequestBody @Valid CurrencyValuesRequest currencyValuesRequest) {
        currencyService.removeDailyCurrencyValues(currencyValuesRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping
    public ResponseEntity<Page<CurrencyValueResponse>> getCurrencyValues(CurrencyValuesFilterRequest currencyValuesFilterRequest, Pageable pageable) {
        return new ResponseEntity<>(currencyService.getCurrencyValues(currencyValuesFilterRequest, pageable), HttpStatus.OK);
    }
}
