package com.app.wooridooribe.repository.diary;

import com.app.wooridooribe.entity.Diary;
import java.util.List;

public interface DiaryQueryDsl {
    List<Diary> findByMemberAndMonth(Long memberId, int year, int month);
}