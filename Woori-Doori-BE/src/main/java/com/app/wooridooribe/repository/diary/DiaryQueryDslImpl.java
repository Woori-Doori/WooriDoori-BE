package com.app.wooridooribe.repository.diary;

import com.app.wooridooribe.entity.Diary;
import com.app.wooridooribe.entity.QDiary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryQueryDslImpl implements DiaryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Diary> findByMemberAndMonth(Long memberId, int year, int month) {
        QDiary d = QDiary.diary;

        return queryFactory
                .selectFrom(d)
                .where(
                        d.member.id.eq(memberId),
                        d.diaryDay.year().eq(year),
                        d.diaryDay.month().eq(month)
                )
                .orderBy(d.diaryDay.asc())
                .fetch();
    }
}
