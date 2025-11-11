package com.app.wooridooribe.repository.categoryMember;

import com.app.wooridooribe.entity.CategoryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMemberRepository extends JpaRepository<CategoryMember, Long>, CategoryMemberQueryDsl {
}

