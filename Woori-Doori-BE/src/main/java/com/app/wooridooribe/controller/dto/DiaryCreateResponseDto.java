package com.app.wooridooribe.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryCreateResponseDto {
    private Long diaryId;
    private String message;
}
