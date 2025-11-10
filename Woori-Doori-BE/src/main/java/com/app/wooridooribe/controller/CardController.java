package com.app.wooridooribe.controller;

import com.app.wooridooribe.controller.dto.ApiResponse;
import com.app.wooridooribe.controller.dto.CardCreateRequestDto;
import com.app.wooridooribe.controller.dto.CardResponseDto;
import com.app.wooridooribe.controller.dto.UserCardResponseDto;
import com.app.wooridooribe.jwt.MemberDetail;
import com.app.wooridooribe.service.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카드", description = "카드 목록 조회 및 사용자 카드 등록 API")
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
            @Parameter(hidden = true) Authentication authentication) {
        // DB에 있는 모든 카드 조회
        List<CardResponseDto> result = cardService.getAllCards();

        return ResponseEntity.ok(
                ApiResponse.res(HttpStatus.OK.value(), "카드 목록 조회 성공", result));
    }

    @Operation(summary = "사용자 카드 검증 및 불러오기", description = "기존 DB에 있는 사용자의 카드 정보를 검증해서 불러옵니다. card_alias가 제공되면 업데이트합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 카드 불러오기 완료")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패 (JWT 필요)")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 카드를 찾을 수 없음")
    @PatchMapping("/putcard")
    public ResponseEntity<ApiResponse<UserCardResponseDto>> createUserCard(
            @Parameter(hidden = true) Authentication authentication,
            @RequestBody(description = "카드 검증 요청 바디", required = true, content = @Content(schema = @Schema(implementation = CardCreateRequestDto.class))) @org.springframework.web.bind.annotation.RequestBody CardCreateRequestDto request) {
        // JWT에서 인증된 사용자 ID 가져오기
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long memberId = principal.getId();

        UserCardResponseDto result = cardService.createUserCard(memberId, request);

        return ResponseEntity.ok(
                ApiResponse.res(HttpStatus.OK.value(), "사용자 카드 불러오기 완료", result));
    }
}
