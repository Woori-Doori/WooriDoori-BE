package com.app.wooridooribe.repository.cardhistory;

import com.app.wooridooribe.controller.dto.CardHistorySummaryResponseDto;
import com.app.wooridooribe.controller.dto.CategorySummaryDto;
import com.app.wooridooribe.entity.CardHistory;
import com.app.wooridooribe.entity.type.StatusType;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface CardHistoryQueryDsl {

    CardHistorySummaryResponseDto findByUserAndMonthAndStatus(Long userId, int year, int month, StatusType status);

    CardHistory findDetailById(Long historyId);

    void updateIncludeTotal(Long historyId, boolean includeTotal);

    void updateCategory(Long historyId, String newCategory);

    void updateDutchpay(Long historyId, int count);

    void updatePrice(Long historyId, int price);

    /**
     * 사용자 ID로 카테고리별 소비 금액 TOP 4 조회
     * 
     * @param userId 사용자 ID
     * @param year   연도
     * @param month  월
     * @return 카테고리별 통계 리스트 (TOP 4)
     */
    List<CategorySummaryDto> findTop4CategoriesByUserId(Long userId, int year, int month);

    /**
     * 사용자 카드 ID로 카테고리별 소비 금액 TOP 4 조회
     * 
     * @param userCardId 사용자 카드 ID
     * @param year       연도
     * @param month      월
     * @return 카테고리별 통계 리스트 (TOP 4)
     */
    List<CategorySummaryDto> findTop4CategoriesByUserCardId(Long userCardId, int year, int month);

    /**
     * 사용자 ID 기준으로 가장 최근 거래가 발생한 연월 조회
     * 
     * @param userId 사용자 ID
     * @return 최근 거래가 존재한다면 해당 연월, 없으면 null
     */
    YearMonth findLatestHistoryYearMonthByUserId(Long userId);

    /**
     * 목표 시작일을 기준으로 한 달 범위의 소비 카테고리 TOP 4 조회
     * 
     * @param userId           사용자 ID
     * @param startDate        기간 시작일(포함)
     * @param endDateExclusive 기간 종료일(제외)
     * @return 카테고리별 통계 리스트 (TOP 4)
     */
    List<CategorySummaryDto> findTop4CategoriesByUserIdWithinPeriod(Long userId, LocalDate startDate,
            LocalDate endDateExclusive);
}
