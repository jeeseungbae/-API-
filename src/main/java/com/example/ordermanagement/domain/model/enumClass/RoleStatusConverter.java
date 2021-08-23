package com.example.ordermanagement.domain.model.enumClass;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleStatusConverter implements AttributeConverter<RoleStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RoleStatus attribute) {
        return attribute.getId();
    }

    @Override
    public RoleStatus convertToEntityAttribute(Integer dbData) {
        return RoleStatus.getData(dbData);
    }
}
