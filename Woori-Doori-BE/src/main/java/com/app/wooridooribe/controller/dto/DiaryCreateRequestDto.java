package com.app.wooridooribe.controller.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class DiaryCreateRequestDto {
    private LocalDate diaryDay;
    private String diaryEmotion;
    private String diaryContent;
}
