package com.app.wooridooribe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 카테고리 고유 ID

    @Column(name = "category_name")
    private String categoryName; // 카테고리 명

    @Column(name = "category_color")
    private String categoryColor; // 카테고리 배경색

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CategoryMember> categoryMembers = new ArrayList<>();
}
