package com.app.wooridooribe.controller.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnGoalDto {
    private String resultMsg;   // resultMsg (이번 달 설정 / 다음 달 등록 / 수정됨)
    private SetGoalDto goalData; // 실제 데이터
}
