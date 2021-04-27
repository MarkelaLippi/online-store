package com.gmail.roadtojob2019.onlinestore.repository.converter;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Unit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UnitConvertor implements AttributeConverter<Unit, String> {
    @Override
    public String convertToDatabaseColumn(Unit unit) {
        if (unit == null) {
            return null;
        }
        return unit.toString();
    }

    @Override
    public Unit convertToEntityAttribute(String unit) {
        if (unit == null) {
            return null;
        }
        try {
            return Unit.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
