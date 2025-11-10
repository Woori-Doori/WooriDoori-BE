package com.app.wooridooribe.repository.membercard;

import com.app.wooridooribe.entity.MemberCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCardRepository extends JpaRepository<MemberCard, Long> {
    
    @Query("SELECT mc FROM MemberCard mc JOIN FETCH mc.card c LEFT JOIN FETCH c.cardImage WHERE mc.member.id = :memberId")
    List<MemberCard> findByMemberIdWithCard(@Param("memberId") Long memberId);
    
    List<MemberCard> findByMemberId(Long memberId);
}

