package com.app.wooridooribe.repository.diary;

import com.app.wooridooribe.entity.Diary;
import com.app.wooridooribe.entity.QDiary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryQueryDslImpl implements DiaryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Diary> findByMemberAndMonth(Long memberId, LocalDate startDate, LocalDate endDate) {
        QDiary d = QDiary.diary;

        return queryFactory
                .selectFrom(d)
                .where(
                        d.member.id.eq(memberId),
                        d.diaryDay.between(startDate, endDate)
                )
                .orderBy(d.diaryDay.asc())
                .fetch();
    }
}
