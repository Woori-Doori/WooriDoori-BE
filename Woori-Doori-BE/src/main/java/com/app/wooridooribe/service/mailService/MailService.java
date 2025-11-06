package com.app.wooridooribe.service.mailService;

public interface MailService {
    
    /**
     * 회원가입 인증번호 발송
     * @param email 수신자 이메일
     * @return 인증번호 (6자리)
     */
    String sendVerificationCode(String email);
    
    /**
     * 인증번호 검증
     * @param email 이메일
     * @param code 사용자가 입력한 인증번호
     * @return 일치 여부
     */
    boolean verifyCode(String email, String code);
}
