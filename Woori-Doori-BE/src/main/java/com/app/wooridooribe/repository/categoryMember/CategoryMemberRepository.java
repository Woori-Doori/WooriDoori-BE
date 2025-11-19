package com.app.wooridooribe.repository.categoryMember;

import com.app.wooridooribe.entity.CategoryMember;
import com.app.wooridooribe.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMemberRepository extends JpaRepository<CategoryMember, Long>, CategoryMemberQueryDsl {

    void deleteByMember(Member member);

    List<CategoryMember> findByMemberId(Long memberId);
}

