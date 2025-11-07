package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class SetGoalDto {

    private String goalJob; // 직업
    private String goalIncome; // 수입
    private Integer previousGoalMoney; // 목표소비금액

}