package com.app.wooridooribe.controller;

import com.app.wooridooribe.controller.dto.ApiResponse;
import com.app.wooridooribe.controller.dto.CardResponseDto;
import com.app.wooridooribe.service.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카드", description = "카드 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Operation(summary = "카드 목록 조회", description = "DB에 있는 모든 카드 목록을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카드 목록 조회 성공")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패 (JWT 필요)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CardResponseDto>>> getCardList(
            @Parameter(hidden = true) Authentication authentication
    ) {
        // DB에 있는 모든 카드 조회
        List<CardResponseDto> result = cardService.getAllCards();

        return ResponseEntity.ok(
                ApiResponse.res(HttpStatus.OK.value(), "카드 목록 조회 성공", result)
        );
    }
}

