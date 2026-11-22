package com.books.spring.Misc;

import jakarta.persistence.*;
import java.util.Base64;
@Converter(autoApply = false)
public class EncryptColumnConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(attribute.getBytes());
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return new String(dbData);
    }
}