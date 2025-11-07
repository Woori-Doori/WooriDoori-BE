package com.app.wooridooribe.repository.cardHistory;

import com.app.wooridooribe.entity.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardHistoryRepository extends JpaRepository<CardHistory, Long> {
    
    /**
     * 특정 회원의 특정 기간 동안의 총 지출 금액을 조회합니다.
     */
    @Query("SELECT COALESCE(SUM(ch.historyPrice), 0) " +
           "FROM CardHistory ch " +
           "WHERE ch.memberCard.member.id = :memberId " +
           "AND ch.historyStatus = com.app.wooridooribe.entity.type.StatusType.ABLE " +
           "AND ch.historyIncludeTotal = 'Y' " +
           "AND ch.historyDate BETWEEN :startDate AND :endDate")
    Integer getTotalSpentByMemberAndDateRange(@Param("memberId") Long memberId, 
                                               @Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
    
    /**
     * 특정 회원의 특정 기간 동안의 카테고리별 지출 금액을 조회합니다.
     */
    @Query("SELECT ch.historyCategory, SUM(ch.historyPrice) " +
           "FROM CardHistory ch " +
           "WHERE ch.memberCard.member.id = :memberId " +
           "AND ch.historyStatus = com.app.wooridooribe.entity.type.StatusType.ABLE " +
           "AND ch.historyIncludeTotal = 'Y' " +
           "AND ch.historyDate BETWEEN :startDate AND :endDate " +
           "GROUP BY ch.historyCategory " +
           "ORDER BY SUM(ch.historyPrice) DESC")
    List<Object[]> getCategorySpendingByMemberAndDateRange(@Param("memberId") Long memberId, 
                                                             @Param("startDate") LocalDate startDate, 
                                                             @Param("endDate") LocalDate endDate);
    
    /**
     * 특정 회원의 특정 기간 동안 가장 많이 사용한 카드 TOP 3를 조회합니다.
     */
    @Query("SELECT mc.card.id, SUM(ch.historyPrice) " +
           "FROM CardHistory ch " +
           "JOIN ch.memberCard mc " +
           "WHERE mc.member.id = :memberId " +
           "AND ch.historyStatus = com.app.wooridooribe.entity.type.StatusType.ABLE " +
           "AND ch.historyIncludeTotal = 'Y' " +
           "AND ch.historyDate BETWEEN :startDate AND :endDate " +
           "GROUP BY mc.card.id " +
           "ORDER BY SUM(ch.historyPrice) DESC")
    List<Object[]> getTopUsedCardsByMemberAndDateRange(@Param("memberId") Long memberId, 
                                                         @Param("startDate") LocalDate startDate, 
                                                         @Param("endDate") LocalDate endDate);
}

