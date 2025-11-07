package com.app.wooridooribe.controller;

import com.app.wooridooribe.controller.dto.ApiResponse;
import com.app.wooridooribe.controller.dto.MemberDto;
import com.app.wooridooribe.controller.dto.SetGoalDto;
import com.app.wooridooribe.controller.dto.GoalDto;
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
    public ResponseEntity<ApiResponse<SetGoalDto>> setGoal(Authentication authentication,
            @RequestBody SetGoalDto setGoalDto) {

        MemberDetail principal = (MemberDetail) authentication.getPrincipal();

        // GoalDto를 빌드해서 Service로 전달
        GoalDto goalDto = GoalDto.builder()
                .memberId(principal.getId())
                .goalStartDate(LocalDate.now())
                .previousGoalMoney(setGoalDto.getPreviousGoalMoney())
                .goalJob(setGoalDto.getGoalJob())
                .goalIncome(setGoalDto.getGoalIncome())
                .goalScore(0)
                .goalComment("굿 목표")
                .build();

        // Service에서 DB에 저장
        GoalDto savedGoal = goalService.setGoal(goalDto);

        // GoalDto → SetGoalDto 로 변환
        SetGoalDto responseDto = SetGoalDto.builder()
                .goalJob(savedGoal.getGoalJob())
                .goalIncome(savedGoal.getGoalIncome())
                .previousGoalMoney(savedGoal.getPreviousGoalMoney())
                .build();

        return ResponseEntity.ok(
                ApiResponse.<SetGoalDto>builder()
                        .statusCode(200)
                        .resultMsg("목표 금액을 설정했어요")
                        .resultData(responseDto)
                        .build()
        );
    }

//    /** 목표 상세 조회 API **/
//    @GetMapping("/detail/{memberId}")
//    public ApiResponse<GoalDto> getGoalDetail(@PathVariable Long id) {
//        SetGoalDto result = goalService.getGoalDetail(id);
//        return ApiResponse.res(200, "목표 상세 정보를 불러왔어요", result);
//    }
//
//    /** 목표 히스토리 조회 API **/
//    @GetMapping("/getgoalhistory/{memberId}")
//    public ApiResponse<List<GoalDto>> getGoalHistory(@PathVariable Long memberId) {
//        List<SetGoalDto> result = goalService.getGoalHistory(memberId);
//        return ApiResponse.res(200, "목표 히스토리를 불러왔어요", result);
//    }
}
