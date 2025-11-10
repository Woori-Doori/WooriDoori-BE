package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class SetGoalDto {

    @Schema(description = "회원 직업", example = "회사원")
    private String goalJob; // 직업

    @Schema(description = "월 수입", example = "2000")
    private String goalIncome; // 수입

    @Schema(description = "목표 제한금액", example = "300")
    private Integer previousGoalMoney; // 목표소비금액

}