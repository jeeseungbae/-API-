package com.example.ordermanagement.domain.model.enumClass;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GradeStatusConverter implements AttributeConverter<GradeStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GradeStatus attribute) {
        return attribute.getId();
    }

    @Override
    public GradeStatus convertToEntityAttribute(Integer dbData) {
        return GradeStatus.getData(dbData);
    }
}
