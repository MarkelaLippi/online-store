package com.gmail.roadtojob2019.onlinestore.repository.converter;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.toString();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
