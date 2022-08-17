package com.umbrella.currencyconverter.service.cbar;

import com.umbrella.currencyconverter.model.cbar.CBARCurrencyResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CBARCurrencyServiceImpl implements CBARCurrencyService {
    private final String CBARDomain = "https://www.cbar.az";
    private final String CBARCurrencyEndpoint = "/currencies/";
    private final RestTemplate restTemplate;

    public CBARCurrencyServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CBARCurrencyResponse getCurrencies(String date) {
        String url = CBARDomain + CBARCurrencyEndpoint + date + ".xml";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), CBARCurrencyResponse.class).getBody();
    }
}
