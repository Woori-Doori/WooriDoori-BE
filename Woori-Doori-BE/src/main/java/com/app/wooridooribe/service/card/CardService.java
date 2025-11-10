package com.app.wooridooribe.service.card;

import com.app.wooridooribe.controller.dto.CardCreateRequestDto;
import com.app.wooridooribe.controller.dto.CardResponseDto;
import com.app.wooridooribe.controller.dto.UserCardResponseDto;

import java.util.List;

public interface CardService {
    List<CardResponseDto> getCardList(Long memberId);

    List<CardResponseDto> getAllCards();

    UserCardResponseDto createUserCard(Long memberId, CardCreateRequestDto request);
}
