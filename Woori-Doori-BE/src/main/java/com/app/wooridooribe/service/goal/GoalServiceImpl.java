package com.app.wooridooribe.service.goal;

import com.app.wooridooribe.controller.dto.ReturnGoalDto;
import com.app.wooridooribe.controller.dto.SetGoalDto;
import com.app.wooridooribe.entity.Goal;
import com.app.wooridooribe.entity.Member;
import com.app.wooridooribe.exception.CustomException;
import com.app.wooridooribe.exception.ErrorCode;
import com.app.wooridooribe.repository.goal.GoalRepository;
import com.app.wooridooribe.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    @Override
    public ReturnGoalDto setGoal(Long memberId, SetGoalDto setGoalDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate nextMonth = thisMonth.plusMonths(1);

        if (setGoalDto == null ||
                setGoalDto.getGoalJob() == null ||
                setGoalDto.getGoalIncome() == null ||
                setGoalDto.getPreviousGoalMoney() == null) {
            throw new CustomException(ErrorCode.GOAL_INVALIDVALUE);
        }

        // 이번 달, 다음 달 목표 조회
        Optional<Goal> thisMonthGoalOpt = goalRepository.findByMemberAndGoalStartDate(member, thisMonth);
        Optional<Goal> nextMonthGoalOpt = goalRepository.findByMemberAndGoalStartDate(member, nextMonth);

        Goal goal;           // 저장 또는 수정될 목표 객체
        String resultMsg;    // 반환용 메시지

        if (thisMonthGoalOpt.isEmpty()) {
            // 이번 달 목표가 없으면 → 이번 달 목표 등록
            goal = Goal.builder()
                    .member(member)
                    .goalStartDate(thisMonth)
                    .previousGoalMoney(setGoalDto.getPreviousGoalMoney())
                    .goalJob(setGoalDto.getGoalJob())
                    .goalIncome(setGoalDto.getGoalIncome())
                    .goalScore(0)
                    .build();

            goalRepository.save(goal);
            resultMsg = "이번 달 목표를 설정했어요";
        }
        else if (nextMonthGoalOpt.isEmpty()) {
            // 이번 달 목표는 있지만 다음 달 목표가 없으면 → 다음 달 목표 등록
            goal = Goal.builder()
                    .member(member)
                    .goalStartDate(nextMonth)
                    .previousGoalMoney(setGoalDto.getPreviousGoalMoney())
                    .goalJob(setGoalDto.getGoalJob())
                    .goalIncome(setGoalDto.getGoalIncome())
                    .goalScore(0)
                    .build();

            goalRepository.save(goal);
            resultMsg = "다음 달 목표를 등록했어요";
        }
        else {
            // 둘 다 있으면 → 다음 달 목표 수정
            goal = nextMonthGoalOpt.get();
            goal.setPreviousGoalMoney(setGoalDto.getPreviousGoalMoney());
            goal.setGoalJob(setGoalDto.getGoalJob());
            goal.setGoalIncome(setGoalDto.getGoalIncome());
            goalRepository.save(goal);
            resultMsg = "다음 달 목표를 수정했어요";
        }

        // 공통 반환 DTO 생성 (return 한 번만)
        return ReturnGoalDto.builder()
                .resultMsg(resultMsg)
                .goalData(SetGoalDto.builder()
                        .goalJob(goal.getGoalJob())
                        .goalIncome(goal.getGoalIncome())
                        .previousGoalMoney(goal.getPreviousGoalMoney())
                        .build())
                .build();
    }
}
