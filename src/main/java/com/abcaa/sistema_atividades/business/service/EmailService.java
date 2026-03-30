package com.abcaa.sistema_atividades.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
    public class EmailService {


    @Value("${resend.api.key}")
    private String apiKey;

    private final String URL = "https://api.resend.com/emails";

    public void sendHtmlEmail(String to, String subject, String html) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "from", "onboarding@resend.dev",
                "to", new String[]{to},
                "subject", subject,
                "html", html
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(URL, request, String.class);
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



