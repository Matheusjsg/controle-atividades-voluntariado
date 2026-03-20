package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.ActivityReportDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.geom.PageSize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CertificateService {

    private final ActivityService activityService;

    @Value("${certificate.organization.name}")
    private String organizationName;

    @Value("${certificate.organization.cnpj}")
    private String organizationCnpj;

    @Value("${certificate.organization.city}")
    private String organizationCity;

    @Value("${certificate.organization.state}")
    private String organizationState;

    public CertificateService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public byte[] generateCertificate(Long volunteerId, LocalDate startDate, LocalDate endDate) {
        // Validação de período
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final.");
        }

        // Buscar relatório
        ActivityReportDTO report = activityService.getReport(volunteerId, startDate, endDate);

        // Validações
        if (report.getTotalActivities() == 0) {
            throw new IllegalStateException("Nenhuma atividade aprovada encontrada para este voluntário.");
        }

        if (report.getTotalMinutes() < 1200) { // menos de 20 horas
            double hoursCompleted = report.getTotalMinutes() / 60.0;
            throw new IllegalStateException(
                String.format("Voluntário possui apenas %.1f horas. Necessário completar 20 horas para emitir certificado.", hoursCompleted)
            );
        }

        // Gerar PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());

        // Margens
        document.setMargins(80, 50, 80, 50);

        // Espaço superior
        document.add(new Paragraph("\n\n"));

        // Título
        Paragraph title = new Paragraph("CERTIFICADO DE VOLUNTARIADO")
            .setFontSize(26)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Linha decorativa
        document.add(new Paragraph("_______________________________________________")
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(10)
            .setMarginBottom(30));

        // Texto introdutório
        document.add(new Paragraph("Certificamos para os devidos fins que")
            .setFontSize(14)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(20));

        // Nome do voluntário (destaque)
        document.add(new Paragraph(report.getVolunteerName().toUpperCase())
            .setFontSize(18)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(20));

        // Conteúdo principal
        double hours = report.getTotalMinutes() / 60.0;
        String periodText = formatPeriod(startDate, endDate);
        
        String contentText = String.format(
            "Participou como voluntário(a) online no setor de %s, cumprindo um total de %.1f horas " +
            "de trabalho voluntário, através de %d atividade%s aprovada%s, %s.",
            report.getDepartment(),
            hours,
            report.getTotalActivities(),
            report.getTotalActivities() > 1 ? "s" : "",
            report.getTotalActivities() > 1 ? "s" : "",
            periodText
        );

        document.add(new Paragraph(contentText)
            .setFontSize(13)
            .setTextAlignment(TextAlignment.JUSTIFIED)
            .setMarginTop(10)
            .setMarginBottom(40));

        // Data e local de emissão
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
        String formattedDate = today.format(dateFormatter);

        document.add(new Paragraph(String.format("%s - %s, %s", organizationCity, organizationState, formattedDate))
            .setFontSize(12)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(50)
            .setMarginBottom(40));

        // Assinatura
        document.add(new Paragraph("_____________________________")
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(20));

        document.add(new Paragraph(organizationName)
            .setFontSize(11)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(5));

        document.add(new Paragraph("CNPJ: " + organizationCnpj)
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(3));

        document.close();
        return baos.toByteArray();
    }

    private String formatPeriod(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        if (startDate != null && endDate != null) {
            return String.format("no período de %s a %s", 
                startDate.format(formatter), 
                endDate.format(formatter));
        } else if (startDate != null) {
            return String.format("a partir de %s", startDate.format(formatter));
        } else if (endDate != null) {
            return String.format("até %s", endDate.format(formatter));
        } else {
            return "durante sua participação";
        }
    }
}
