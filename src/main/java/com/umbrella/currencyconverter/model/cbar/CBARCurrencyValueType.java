package com.umbrella.currencyconverter.model.cbar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CBARCurrencyValueType {
    @JacksonXmlProperty(localName = "Type")
    private String type;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private List<CBARValue> values;
}
