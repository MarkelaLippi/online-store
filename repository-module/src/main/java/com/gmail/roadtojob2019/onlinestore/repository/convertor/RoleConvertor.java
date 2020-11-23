package com.gmail.roadtojob2019.onlinestore.repository.convertor;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConvertor implements AttributeConverter<Role, String> {
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

        return Role.valueOf(role.toUpperCase());
    }
}
