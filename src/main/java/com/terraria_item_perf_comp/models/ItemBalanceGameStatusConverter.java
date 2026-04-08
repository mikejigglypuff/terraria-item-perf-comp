package com.terraria_item_perf_comp.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link ItemBalanceGameStatus} 엔티티 필드와 DB {@code item_balance_games.status} 컬럼 간 변환을 담당합니다.
 * <p>
 * DB에는 CHECK 제약에 맞는 소문자 문자열({@code ready}, {@code active}, {@code completed}, {@code abandoned})이 저장되고,
 * 애플리케이션에서는 Java {@code enum} 상수({@code READY}, {@code ACTIVE}, …)로 다룹니다.
 * {@link jakarta.persistence.Enumerated}의 기본 문자열 매핑은 대문자 상수명을 쓰므로, 스키마와 맞추기 위해 이 컨버터를 둡니다.
 */
@Converter
public class ItemBalanceGameStatusConverter implements AttributeConverter<ItemBalanceGameStatus, String> {

    @Override
    public String convertToDatabaseColumn(ItemBalanceGameStatus attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public ItemBalanceGameStatus convertToEntityAttribute(String dbData) {
        return ItemBalanceGameStatus.fromDbValue(dbData);
    }
}
