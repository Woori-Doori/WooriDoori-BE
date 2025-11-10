package com.app.wooridooribe.service.card;

import com.app.wooridooribe.controller.dto.CardResponseDto;
import com.app.wooridooribe.entity.Card;
import com.app.wooridooribe.entity.MemberCard;
import com.app.wooridooribe.repository.card.CardRepository;
import com.app.wooridooribe.repository.membercard.MemberCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final MemberCardRepository memberCardRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardList(Long memberId) {
        List<MemberCard> memberCards = memberCardRepository.findByMemberIdWithCard(memberId);
        
        return memberCards.stream()
                .map(CardResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponseDto> getAllCards() {
        List<Card> cards = cardRepository.findAllWithImage();
        
        return cards.stream()
                .map(CardResponseDto::fromCard)
                .collect(Collectors.toList());
    }
}

