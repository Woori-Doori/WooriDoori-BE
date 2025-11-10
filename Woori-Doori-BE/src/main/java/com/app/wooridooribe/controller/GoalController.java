package com.app.wooridooribe.controller;

import com.app.wooridooribe.controller.dto.*;
import com.app.wooridooribe.entity.Goal;
import com.app.wooridooribe.jwt.MemberDetail;
import com.app.wooridooribe.service.goal.GoalService;
import com.app.wooridooribe.service.goal.GoalServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
@Slf4j
public class GoalController {

    private final GoalService goalService;

    /** 목표 설정 API **/
    @PutMapping("/setgoal")
    public ResponseEntity<ApiResponse<SetGoalDto>> setCurrentGoal(Authentication authentication,
            @RequestBody SetGoalDto setGoalDto) {

        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Long memberId = principal.getMember().getId();
        String resultMsg;

        ReturnGoalDto result = goalService.setGoal(memberId, setGoalDto);

        // resultMsg (이번 달 설정 / 다음 달 등록 / 수정됨)
        if (!result.isThisMonthGoalExists()) {
            resultMsg = "이번 달 목표를 설정했어요";
        } else if (!result.isNextMonthGoalExists()) {
            resultMsg = "다음 달 목표를 등록했어요";
        } else {
            resultMsg = "다음 달 목표를 수정했어요";
        }

        return ResponseEntity.ok(ApiResponse.res(200, resultMsg, result.getGoalData()));
    }



//    /** 목표 히스토리 조회 API **/
//    @GetMapping("/getgoalhistory/{memberId}")
//    public ApiResponse<List<GoalDto>> getGoalHistory(@PathVariable Long memberId) {
//        List<SetGoalDto> result = goalService.getGoalHistory(memberId);
//        return ApiResponse.res(200, "목표 히스토리를 불러왔어요", result);
//    }
}
