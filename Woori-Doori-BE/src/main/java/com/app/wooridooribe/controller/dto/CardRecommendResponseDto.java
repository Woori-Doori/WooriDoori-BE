package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.type.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "카드 추천 응답 DTO")
public class CardRecommendResponseDto {
    @Schema(description = "TOP1 카테고리", example = "FOOD")
    private CategoryType topCategory;

    @Schema(description = "추천 카드 목록 (최대 4개)")
    private List<CardResponseDto> cards;
}

