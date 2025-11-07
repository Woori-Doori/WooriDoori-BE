package com.app.wooridooribe.service.mainpage;


import com.app.wooridooribe.controller.dto.JoinDto;
import com.app.wooridooribe.controller.dto.LoginResponseDto;
import com.app.wooridooribe.controller.dto.TokenDto;
import com.app.wooridooribe.controller.dto.TokenRequestDto;
import com.app.wooridooribe.entity.Member;
import com.app.wooridooribe.entity.RefreshToken;
import com.app.wooridooribe.exception.CustomException;
import com.app.wooridooribe.exception.ErrorCode;
import com.app.wooridooribe.jwt.MemberDetail;
import com.app.wooridooribe.jwt.TokenProvider;
import com.app.wooridooribe.repository.member.MemberRepository;
import com.app.wooridooribe.repository.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainpageServiceImpl implements MainpageService {


}