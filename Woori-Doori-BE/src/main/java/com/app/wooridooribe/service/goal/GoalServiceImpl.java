package com.app.wooridooribe.service.goal;

import com.app.wooridooribe.controller.dto.GoalDto;
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

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    @Override
    public SetGoalDto setGoal(Long memberId, SetGoalDto setGoalDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.withDayOfMonth(1); // 이번 달 1일 기준

        Goal goal = null;

        // 1️⃣ 이번 달 목표 조회
        goal = goalRepository.findByMemberAndGoalStartDateBetween(
                member,
                targetDate,
                targetDate.withDayOfMonth(targetDate.lengthOfMonth())
        ).orElse(null);

        if (goal == null) {
            // 이번 달 목표가 없으면 새로 등록
            goal = Goal.builder()
                    .member(member)
                    .goalStartDate(targetDate)
                    .previousGoalMoney(setGoalDto.getPreviousGoalMoney())
                    .goalJob(setGoalDto.getGoalJob())
                    .goalIncome(setGoalDto.getGoalIncome())
                    .goalScore(0)
                    .goalComment("이번 달 목표")
                    .build();
        } else {
            // 이번 달 목표가 이미 있으면 다음 달 목표 조회
            targetDate = targetDate.plusMonths(1);
            goal = goalRepository.findByMemberAndGoalStartDateBetween(
                    member,
                    targetDate,
                    targetDate.withDayOfMonth(targetDate.lengthOfMonth())
            ).orElse(
                    // 없으면 새로 등록
                    Goal.builder()
                            .member(member)
                            .goalStartDate(targetDate)
                            .goalScore(0)
                            .goalComment("다음 달 목표")
                            .previousGoalMoney(setGoalDto.getPreviousGoalMoney())
                            .goalJob(setGoalDto.getGoalJob())
                            .goalIncome(setGoalDto.getGoalIncome())
                            .build()
            );

            // 이미 존재하면 수정
            goal.setPreviousGoalMoney(setGoalDto.getPreviousGoalMoney());
            goal.setGoalJob(setGoalDto.getGoalJob());
            goal.setGoalIncome(setGoalDto.getGoalIncome());
        }

        goalRepository.save(goal);

        return SetGoalDto.builder()
                .goalJob(goal.getGoalJob())
                .goalIncome(goal.getGoalIncome())
                .previousGoalMoney(goal.getPreviousGoalMoney())
                .build();
    }
}