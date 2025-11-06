package com.app.wooridooribe.service.mailService;

import com.app.wooridooribe.exception.CustomException;
import com.app.wooridooribe.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceIImpl implements MailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    
    private static final String REDIS_KEY_PREFIX = "email:verification:";
    private static final long VERIFICATION_CODE_EXPIRE_MINUTES = 3;

    /**
     * 회원가입 인증번호 발송
     * @param email 수신자 이메일
     * @return 인증번호 (6자리)
     */
    @Override
    public String sendVerificationCode(String email) {
        try {
            // 1. 6자리 인증번호 생성
            Random random = new Random();
            String verificationCode = String.format("%06d", random.nextInt(1000000));
            
            // 2. Redis에 저장 (3분 후 자동 만료)
            String redisKey = REDIS_KEY_PREFIX + email;
            redisTemplate.opsForValue().set(
                    redisKey, 
                    verificationCode, 
                    VERIFICATION_CODE_EXPIRE_MINUTES, 
                    TimeUnit.MINUTES
            );
            log.info("인증번호 Redis 저장 완료: {} -> {}", email, verificationCode);
            
            // 3. 이메일 발송
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom("ricky0130@naver.com");
            helper.setTo(email);
            helper.setSubject("🎉 우리두리 회원가입 인증번호입니다");
            helper.setText(createHtmlContent(verificationCode), true);  // true = HTML
            
            mailSender.send(mimeMessage);
            log.info("인증 이메일 발송 완료: {}", email);
            
            return verificationCode;
            
        } catch (MessagingException e) {
            log.error("이메일 발송 실패: {}", email, e);
            throw new CustomException(ErrorCode.SIGNIN_FAIL);
        }
    }
    
    /**
     * 인증번호 검증
     * @param email 이메일
     * @param code 사용자가 입력한 인증번호
     * @return 일치 여부
     */
    @Override
    public boolean verifyCode(String email, String code) {
        String redisKey = REDIS_KEY_PREFIX + email;
        String savedCode = redisTemplate.opsForValue().get(redisKey);
        
        if (savedCode == null) {
            log.error("인증번호 만료 또는 존재하지 않음: {}", email);
            throw new CustomException(ErrorCode.TIME_OUT);
        }
        
        if (!savedCode.equals(code)) {
            log.error("인증번호 불일치: {} (입력: {}, 저장: {})", email, code, savedCode);
            throw new CustomException(ErrorCode.AUTH_FAIL);
        }
        
        // 검증 성공 시 Redis에서 삭제
        redisTemplate.delete(redisKey);
        log.info("인증 성공: {}", email);
        
        return true;
    }
    
    /**
     * HTML 이메일 템플릿 생성
     */
    private String createHtmlContent(String verificationCode) {
        return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Apple SD Gothic Neo', 'Malgun Gothic', sans-serif; background-color: #f5f7fa;">
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f5f7fa; padding: 40px 0;">
                        <tr>
                            <td align="center">
                                <!-- 메인 컨테이너 -->
                                <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 16px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); overflow: hidden;">
                                    
                                    <!-- 헤더 -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 40px 30px; text-align: center;">
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 700;">
                                                우리두리
                                            </h1>
                                            <p style="margin: 10px 0 0 0; color: #e0e7ff; font-size: 14px;">
                                                함께하는 소비, 즐거운 절약 💰
                                            </p>
                                        </td>
                                    </tr>
                                    
                                    <!-- 본문 -->
                                    <tr>
                                        <td style="padding: 40px 30px;">
                                            <h2 style="margin: 0 0 20px 0; color: #1a202c; font-size: 22px; font-weight: 600; text-align: center;">
                                              👋 회원가입을 환영합니다! 👋
                                            </h2>
                                            <p style="margin: 0 0 30px 0; color: #4a5568; font-size: 15px; line-height: 1.6; text-align: center;">
                                                안녕하세요! 🎉<br>
                                                우리두리 회원가입을 위한 인증번호를 보내드립니다.<br>
                                                아래 인증번호를 입력하여 가입을 완료해주세요.
                                            </p>
                                            
                                            <!-- 인증번호 박스 -->
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td align="center" style="padding: 30px 0;">
                                                    <div style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); border-radius: 16px; padding: 35px 50px; display: inline-block; box-shadow: 0 8px 16px rgba(102, 126, 234, 0.3);">
                                                        <p style="margin: 0 0 12px 0; color: #e0e7ff; font-size: 14px; font-weight: 500;">
                                                            인증번호
                                                        </p>
                                                        <p style="margin: 0; color: #ffffff; font-size: 38px; font-weight: 700; letter-spacing: 10px; text-shadow: 0 2px 4px rgba(0,0,0,0.1); cursor: text; user-select: all; -webkit-user-select: all; -moz-user-select: all; -ms-user-select: all;">
                                                            %s
                                                        </p>
                                                    </div>
                                                    
                                                    <!-- 복사 안내 -->
                                                    <div style="margin-top: 15px; color: #718096; font-size: 13px;">
                                                        📋 인증번호를 클릭하여 복사해주세요
                                                    </div>
                                                </td>
                                            </tr>
                                            </table>
                                            
                                            <!-- 안내 사항 -->
                                            <table width="100%%" cellpadding="0" cellspacing="0" style="margin-top: 30px; background-color: #f7fafc; border-radius: 12px; padding: 25px; border-left: 4px solid #667eea;">
                                                <tr>
                                                    <td>
                                                        <p style="margin: 0 0 15px 0; color: #2d3748; font-size: 14px; font-weight: 600;">
                                                             유의사항
                                                        </p>
                                                        <ul style="margin: 0; padding-left: 20px; color: #718096; font-size: 13px; line-height: 2;">
                                                            <li> 인증번호는 <strong style="color: #667eea;">3분간 유효</strong>합니다.</li>
                                                            <li> 인증번호를 타인에게 공유하지 마세요.</li>
                                                            <li> 본인이 요청하지 않은 경우, 이 메일을 무시해주세요.</li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    
                                    <!-- 푸터 -->
                                    <tr>
                                        <td style="background-color: #f7fafc; padding: 30px; text-align: center; border-top: 1px solid #e2e8f0;">
                                            <p style="margin: 0 0 10px 0; color: #a0aec0; font-size: 12px;">
                                                © 2025 Woori-Doori 
                                            </p>
                                            <p style="margin: 0; color: #cbd5e0; font-size: 11px;">
                                                이 메일은 발신 전용입니다. 문의사항은 고객센터를 이용해주세요. 📧
                                            </p>
                                        </td>
                                    </tr>
                                    
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(verificationCode);
    }

}
