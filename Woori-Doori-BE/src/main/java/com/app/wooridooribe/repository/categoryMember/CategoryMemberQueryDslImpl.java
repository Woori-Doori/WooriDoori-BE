package com.app.wooridooribe.repository.categoryMember;

import com.app.wooridooribe.entity.QCategoryMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryMemberQueryDslImpl implements CategoryMemberQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findEssentialCategoryNamesByMemberId(Long memberId) {
        QCategoryMember categoryMember = QCategoryMember.categoryMember;

        return queryFactory
                .select(categoryMember.category.categoryName)
                .from(categoryMember)
                .where(
                        categoryMember.member.id.eq(memberId),
                        categoryMember.isEssential.isTrue()
                )
                .fetch();
    }
}

