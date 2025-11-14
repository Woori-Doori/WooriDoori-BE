package com.app.wooridooribe.repository.cardhistory;

import com.app.wooridooribe.controller.dto.CardHistorySummaryResponseDto;
import com.app.wooridooribe.controller.dto.CategorySummaryDto;
import com.app.wooridooribe.entity.CardHistory;
import com.app.wooridooribe.entity.QCardHistory;
import com.app.wooridooribe.entity.QMemberCard;
import com.app.wooridooribe.entity.type.StatusType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CardHistoryQueryDslImpl implements CardHistoryQueryDsl {

        private final JPAQueryFactory queryFactory;

        @Override
        public CardHistorySummaryResponseDto findByUserAndMonthAndStatus(Long userId, int year, int month,
                        StatusType status) {
                QCardHistory history = QCardHistory.cardHistory;
                QMemberCard memberCard = QMemberCard.memberCard;

                // 한 번의 쿼리로 전체 리스트 조회
                List<CardHistory> histories = queryFactory
                                .selectFrom(history)
                                .join(history.memberCard, memberCard)
                                .where(
                                                memberCard.member.id.eq(userId),
                                                history.historyStatus.eq(status),
                                                history.historyDate.year().eq(year),
                                                history.historyDate.month().eq(month))
                                .orderBy(history.historyDate.asc())
                                .fetch();

                int totalAmount = histories.stream()
                                .mapToInt(CardHistory::getHistoryPrice)
                                .sum();

                return new CardHistorySummaryResponseDto(totalAmount, histories);
        }

        @Override
        public CardHistory findDetailById(Long historyId) {
                QCardHistory history = QCardHistory.cardHistory;
                QMemberCard memberCard = QMemberCard.memberCard;

                return queryFactory
                                .selectFrom(history)
                                .join(history.memberCard, memberCard).fetchJoin() // memberCard 같이 가져옴
                                .where(history.id.eq(historyId))
                                .fetchOne();
        }

        @Override
        @Transactional
        public void updateIncludeTotal(Long historyId, boolean includeTotal) {
                QCardHistory ch = QCardHistory.cardHistory;

                queryFactory
                                .update(ch)
                                .set(ch.historyIncludeTotal, includeTotal ? "Y" : "N")
                                .where(ch.id.eq(historyId))
                                .execute();
        }

        @Override
        @Transactional
        public void updateCategory(Long historyId, String newCategory) {
                QCardHistory ch = QCardHistory.cardHistory;

                queryFactory
                                .update(ch)
                                .set(ch.historyCategory, newCategory)
                                .where(ch.id.eq(historyId))
                                .execute();
        }

        @Override
        @Transactional
        public void updateDutchpay(Long historyId, int count) {
                QCardHistory ch = QCardHistory.cardHistory;

                queryFactory
                                .update(ch)
                                .set(ch.historyDutchpay, count)
                                .where(ch.id.eq(historyId))
                                .execute();
        }

        @Override
        @Transactional
        public void updatePrice(Long historyId, int price) {
                QCardHistory ch = QCardHistory.cardHistory;

                queryFactory
                                .update(ch)
                                .set(ch.historyPrice, price)
                                .where(ch.id.eq(historyId))
                                .execute();
        }

        @Override
        public List<CategorySummaryDto> findTop4CategoriesByUserId(Long userId, int year, int month) {
                QCardHistory history = QCardHistory.cardHistory;
                QMemberCard memberCard = QMemberCard.memberCard;

                return queryFactory
                                .select(Projections.constructor(
                                                CategorySummaryDto.class,
                                                history.historyCategory,
                                                history.historyPrice.sum().longValue(), // Long으로 변환
                                                history.count().longValue())) // Long으로 변환
                                .from(history)
                                .join(history.memberCard, memberCard)
                                .where(
                                                memberCard.member.id.eq(userId),
                                                history.historyStatus.eq(StatusType.ABLE),
                                                history.historyDate.year().eq(year),
                                                history.historyDate.month().eq(month),
                                                history.historyIncludeTotal.eq("Y") // 총 지출에 포함된 것만
                                )
                                .groupBy(history.historyCategory)
                                .orderBy(history.historyPrice.sum().desc())
                                .limit(4)
                                .fetch();
        }

        @Override
        public List<CategorySummaryDto> findTop4CategoriesByUserCardId(Long userCardId, int year, int month) {
                QCardHistory history = QCardHistory.cardHistory;

                return queryFactory
                                .select(Projections.constructor(
                                                CategorySummaryDto.class,
                                                history.historyCategory,
                                                history.historyPrice.sum().longValue(), // Long으로 변환
                                                history.count().longValue())) // Long으로 변환
                                .from(history)
                                .where(
                                                history.memberCard.id.eq(userCardId),
                                                history.historyStatus.eq(StatusType.ABLE),
                                                history.historyDate.year().eq(year),
                                                history.historyDate.month().eq(month),
                                                history.historyIncludeTotal.eq("Y") // 총 지출에 포함된 것만
                                )
                                .groupBy(history.historyCategory)
                                .orderBy(history.historyPrice.sum().desc())
                                .limit(4)
                                .fetch();
        }

        @Override
        public YearMonth findLatestHistoryYearMonthByUserId(Long userId) {
                QCardHistory history = QCardHistory.cardHistory;
                QMemberCard memberCard = QMemberCard.memberCard;

                LocalDate latestDate = queryFactory
                                .select(history.historyDate.max())
                                .from(history)
                                .join(history.memberCard, memberCard)
                                .where(
                                                memberCard.member.id.eq(userId),
                                                history.historyStatus.eq(StatusType.ABLE),
                                                history.historyIncludeTotal.eq("Y"))
                                .fetchOne();

                return latestDate != null ? YearMonth.from(latestDate) : null;
        }

        @Override
        public List<CategorySummaryDto> findTop4CategoriesByUserIdWithinPeriod(Long userId, LocalDate startDate,
                        LocalDate endDateExclusive) {
                QCardHistory history = QCardHistory.cardHistory;
                QMemberCard memberCard = QMemberCard.memberCard;

                return queryFactory
                                .select(Projections.constructor(
                                                CategorySummaryDto.class,
                                                history.historyCategory,
                                                history.historyPrice.sum().longValue(),
                                                history.count().longValue()))
                                .from(history)
                                .join(history.memberCard, memberCard)
                                .where(
                                                memberCard.member.id.eq(userId),
                                                history.historyStatus.eq(StatusType.ABLE),
                                                history.historyIncludeTotal.eq("Y"),
                                                history.historyDate.goe(startDate),
                                                history.historyDate.lt(endDateExclusive))
                                .groupBy(history.historyCategory)
                                .orderBy(history.historyPrice.sum().desc())
                                .limit(4)
                                .fetch();
        }

}
