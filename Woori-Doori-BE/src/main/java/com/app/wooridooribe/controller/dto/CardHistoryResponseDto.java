package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.CardHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CardHistoryResponseDto {
    private Long id;
    private LocalDate date;
    private String historyName;
    private String historyCategory;
    private Integer historyPrice;
    private Integer historyDutchpay;
    private String includeTotal;

    public static CardHistoryResponseDto from(CardHistory entity) {
        return CardHistoryResponseDto.builder()
                .id(entity.getId())
                .date(entity.getHistoryDate())
                .historyName(entity.getHistoryName())
                .historyCategory(entity.getHistoryCategory())
                .historyPrice(entity.getHistoryPrice())
                .historyDutchpay(entity.getHistoryDutchpay())
                .includeTotal(entity.getHistoryIncludeTotal())
                .build();
    }
}
