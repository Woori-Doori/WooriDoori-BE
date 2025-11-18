package com.app.wooridooribe.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "관리자 리포트 알림 전송 요청 DTO")
public class AdminReportNotificationRequestDto {

    @NotBlank(message = "회원 ID(이메일)는 필수입니다")
    @Schema(description = "알림을 받을 회원 ID (이메일)", example = "test@example.com", required = true)
    private String memberId;
}
