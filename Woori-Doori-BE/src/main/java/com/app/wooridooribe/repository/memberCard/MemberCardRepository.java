package com.app.wooridooribe.repository.memberCard;

import com.app.wooridooribe.entity.MemberCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCardRepository extends JpaRepository<MemberCard, Long>, MemberCardRepositoryCustom {

    List<MemberCard> findByMemberId(Long memberId);
}
