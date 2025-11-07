package com.app.wooridooribe.service.diary;

import com.app.wooridooribe.controller.dto.DiaryResponseDto;
import com.app.wooridooribe.entity.Diary;
import com.app.wooridooribe.exception.CustomException;
import com.app.wooridooribe.exception.ErrorCode;
import com.app.wooridooribe.repository.diary.DiaryRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public List<DiaryResponseDto> getMonthlyDiaries(Long memberId, LocalDate targetDate) {
        validateDate(targetDate);

        int year = targetDate.getYear();
        int month = targetDate.getMonthValue();

        List<Diary> diaries = diaryRepository.findByMemberAndMonth(memberId, year, month);
        if (diaries.isEmpty()) {
            throw new CustomException(ErrorCode.DIARY_ISNULL);
        }

        return diaries.stream()
                .map(DiaryResponseDto::from)
                .toList();
    }

    @Override
    public DiaryResponseDto getDiaryDetail(Long diaryId, Long memberId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIARY_ISNULL));

        if (!diary.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.DIARY_ISNOTYOURS);
        }

        return DiaryResponseDto.from(diary);
    }

    private void validateDate(LocalDate targetDate) {
        if (targetDate == null) {
            throw new CustomException(ErrorCode.DIARY_INVALID_DATE);
        }

        int year = targetDate.getYear();
        if (year < 2000) {
            throw new CustomException(ErrorCode.DIARY_INVALID_DATE);
        }
    }
}
