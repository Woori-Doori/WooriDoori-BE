package com.app.wooridooribe.repository.diary;

import com.app.wooridooribe.entity.Diary;
import java.time.LocalDate;
import java.util.List;

public interface DiaryQueryDsl {
    List<Diary> findByMemberAndMonth(Long memberId, LocalDate startDate, LocalDate endDate);
}