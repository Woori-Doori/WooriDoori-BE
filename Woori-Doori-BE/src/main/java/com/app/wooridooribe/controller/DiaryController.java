package com.app.wooridooribe.controller;

import com.app.wooridooribe.controller.dto.ApiResponse;
import com.app.wooridooribe.controller.dto.DiaryResponseDto;
import com.app.wooridooribe.jwt.MemberDetail;
import com.app.wooridooribe.service.diary.DiaryService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiaryResponseDto>>> getMonthlyDiaries(
            Authentication authentication,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate
    ) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long memberId = principal.getId();

        List<DiaryResponseDto> result = diaryService.getMonthlyDiaries(memberId, targetDate);

        return ResponseEntity.ok(
                ApiResponse.res(HttpStatus.OK.value(), "소비 일기 전체 조회 성공", result)
        );
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryResponseDto>> getDiaryDetail(
            Authentication authentication,
            @PathVariable Long diaryId
    ) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long memberId = principal.getId();

        DiaryResponseDto result = diaryService.getDiaryDetail(diaryId, memberId);
        return ResponseEntity.ok(
                ApiResponse.res(HttpStatus.OK.value(), "소비 일기 상세 조회 성공", result)
        );
    }
}
