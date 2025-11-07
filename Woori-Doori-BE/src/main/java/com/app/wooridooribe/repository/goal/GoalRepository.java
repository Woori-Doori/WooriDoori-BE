package com.app.wooridooribe.repository.goal;

import com.app.wooridooribe.entity.Goal;
import com.app.wooridooribe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Optional<Goal> findByMemberId(Long memberId);
}