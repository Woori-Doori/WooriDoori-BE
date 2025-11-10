package com.app.wooridooribe.controller.dto;

import com.app.wooridooribe.entity.Member;
import com.app.wooridooribe.entity.Goal;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "목표의 ID", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @Schema(description = "회원 ID", example = "example@naver.com")
    private Long memberId;

    @Schema(description = "목표 시작 날짜", example = "2025-11-01")
    private LocalDate goalStartDate;

    @Schema(description = "목표 제한금액", example = "300")
    private Integer previousGoalMoney;

    @Schema(description = "목표 점수 (예: 85점)", example = "0")
    private Integer goalScore;

    @Schema(description = "두리 코멘트", example = "")
    private String goalComment;

    @Schema(description = "회원 직업", example = "회사원")
    private String goalJob;

    @Schema(description = "월 수입", example = "2000")
    private String goalIncome;

}