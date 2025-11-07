package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.Member;
import com.app.wooridooribe.entity.Goal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GoalDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId; // 목표의 ID

    private Long memberId;

    private LocalDate goalStartDate; // 목표시작날짜

    private Integer previousGoalMoney; // 목표금액

    private Integer goalScore; // 받은 점수

    private String goalComment; // 두리 코멘트

    private String goalJob; // 직업

    private String goalIncome; // 수입

}