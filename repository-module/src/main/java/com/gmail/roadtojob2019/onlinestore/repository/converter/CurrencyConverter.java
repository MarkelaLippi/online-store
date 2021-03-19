package com.gmail.roadtojob2019.onlinestore.repository.converter;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency currency) {
        if (currency == null) {
            return null;
        }
        return currency.toString();
    }

    @Override
    public Currency convertToEntityAttribute(String currency) {
        if (currency == null) {
            return null;
        }
        try {
            return Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
