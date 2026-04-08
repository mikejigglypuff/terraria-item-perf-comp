package com.terraria_item_perf_comp.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link ItemCompGameStatus} 엔티티 필드와 DB {@code item_comp_games.status} 컬럼 간 변환을 담당합니다.
 */
@Converter
public class ItemCompGameStatusConverter implements AttributeConverter<ItemCompGameStatus, String> {

    @Override
    public String convertToDatabaseColumn(ItemCompGameStatus attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public ItemCompGameStatus convertToEntityAttribute(String dbData) {
        return ItemCompGameStatus.fromDbValue(dbData);
    }
}

