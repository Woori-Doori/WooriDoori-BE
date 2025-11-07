package com.app.wooridooribe.service.diary;

import com.app.wooridooribe.controller.dto.DiaryCreateRequestDto;
import com.app.wooridooribe.controller.dto.DiaryCreateResponseDto;
import com.app.wooridooribe.controller.dto.DiaryResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    List<DiaryResponseDto> getMonthlyDiaries(Long memberId, LocalDate targetDate);

    DiaryResponseDto getDiaryDetail(Long diaryId, Long memberId);

    DiaryCreateResponseDto createDiary(Long memberId, DiaryCreateRequestDto request);
}
