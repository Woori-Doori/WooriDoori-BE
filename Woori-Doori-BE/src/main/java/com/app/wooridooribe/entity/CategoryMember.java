package com.app.wooridooribe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_category_member")
public class CategoryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 카테고리-멤버 중간테이블 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 유저 고유번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카테고리 고유 ID

    @Column(name = "is_essential", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean isEssential = false; // 필수여부
}

