package com.app.wooridooribe.entity;

import com.app.wooridooribe.entity.type.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_card_benefit_category")
public class CardBenefitCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 카드 혜택 카테고리 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card; // 카드 고유번호

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private CategoryType categoryType; // 카테고리 (ENUM)
}

