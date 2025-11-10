package com.app.wooridooribe.service.card;

import com.app.wooridooribe.controller.dto.CardResponseDto;

import java.util.List;

public interface CardService {
    List<CardResponseDto> getCardList(Long memberId);
    List<CardResponseDto> getAllCards();
}

