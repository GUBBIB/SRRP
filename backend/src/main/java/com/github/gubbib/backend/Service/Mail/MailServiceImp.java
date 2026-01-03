package com.github.gubbib.backend.Service.Mail;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@Slf4j
public class MailServiceImp implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendStudentVerificationMail(String toEmail, String code) {
        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setTo(toEmail);
        smm.setSubject("[RASP] 재학생 학교 인증번호");
        smm.setText(buildVerificationMessage(code));

        try {
            mailSender.send(smm);
            log.debug("✅ 재학생 인증 메일 전송 성공!");
        } catch(MailException e) {
            log.debug("⛔ 이메일 전송중에 오류가 발생했습니다. err{}", e.getMessage());
            throw new GlobalException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    @Override
    public String buildVerificationMessage(String code) {
        return """
            RASP 동아리 페이지 재학생 인증용 메일입니다.
            
            인증번호 : %s
            
            인증 번호는 5분간 유효합니다.
            본인이 요청하지 않았다면 이 메일을 무시해주세요.
            """.formatted(code);
    }
}
