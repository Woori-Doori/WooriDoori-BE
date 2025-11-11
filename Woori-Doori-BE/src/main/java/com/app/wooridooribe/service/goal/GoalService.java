package com.app.wooridooribe.service.goal;


import com.app.wooridooribe.controller.dto.GoalDto;
import com.app.wooridooribe.controller.dto.GoalResponseDto;
import com.app.wooridooribe.controller.dto.SetGoalDto;

import java.util.List;


public interface GoalService {
    GoalResponseDto setGoal(Long memberId, SetGoalDto setGoalDto);
    List<SetGoalDto> getGoalHistory(String userName);
}
