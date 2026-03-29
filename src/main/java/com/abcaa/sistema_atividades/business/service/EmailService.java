package com.abcaa.sistema_atividades.business.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
    public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }

    public String htmlEmailTemplate(String link) {
        String html = """
                <div style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
                
                <div style="max-width:600px; margin:auto; background:#ffffff; padding:30px; border-radius:10px; text-align:center;">
                
                <h2 style="color:#333;">🔐 Recuperação de Senha</h2>
                
                <p style="color:#555; font-size:16px;">
                    Recebemos uma solicitação para redefinir sua senha.
                </p>
                
                <p style="color:#555; font-size:16px;">
                    Clique no botão abaixo para continuar:
                </p>
                
                <a href="%s"
                   style="display:inline-block; margin-top:20px; padding:12px 25px; 
                          background:#007bff; color:#fff; text-decoration:none; 
                          border-radius:5px; font-weight:bold;">
                    Redefinir senha
                </a>
                
                <p style="margin-top:30px; font-size:12px; color:#999;">
                    Este link expira em 1 hora.
                </p>
                
                <p style="font-size:12px; color:#999;">
                    Se você não solicitou, ignore este email.
                </p>
                
                </div>
                
                </div>
                """.formatted(link);
        return html;

    }
    }



