package com.github.gubbib.backend.Service.Mail;

public interface MailService {
    void sendStudentVerificationMail(String toEmail, String code);
    String  buildVerificationMessage(String code);
}
