package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.ActivityReportDTO;
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
import java.util.Locale;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;

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
        // Aumentei a margem superior para empurrar o conteúdo mais para baixo
        // (alteração solicitada pelo usuário para deixar o texto mais abaixo em relação ao fundo)
        document.setMargins(180, 70, 80, 70);

        // Fontes
        PdfFont font;
        PdfFont bold;
        try {
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        } catch (Exception e) {
            // fallback para caso de problema ao carregar fonte
            font = null;
            bold = null;
        }

        // Fundo único: usar imagem criada em src/main/resources/static/certificado.png como background
        // Alteração: desenhar a imagem diretamente no canvas da primeira página cobrindo TODO o retângulo da página
        // Isso evita áreas em branco no topo que podem ocorrer ao adicionar a imagem como elemento do Document.
        try (java.io.InputStream is = getClass().getResourceAsStream("/static/certificado.png")) {
            if (is != null) {
                byte[] imgBytes = is.readAllBytes();
                com.itextpdf.io.image.ImageData imgData = ImageDataFactory.create(imgBytes);
                try {
                    // desenha a imagem diretamente no conteúdo da página (plano de fundo)
                    com.itextpdf.kernel.geom.Rectangle rect = pdf.getDefaultPageSize();
                    PdfCanvas canvasBg = new PdfCanvas(pdf.getFirstPage());
                    canvasBg.saveState();
                    canvasBg.addImageFittedIntoRectangle(imgData, rect, false);
                    canvasBg.restoreState();
                } catch (Exception ignored) {
                    // fallback: se não puder desenhar no canvas, adiciona como elemento para não quebrar a geração
                    Image bg = new Image(imgData);
                    float pageW = document.getPdfDocument().getDefaultPageSize().getWidth();
                    float pageH = document.getPdfDocument().getDefaultPageSize().getHeight();
                    bg.scaleToFit(pageW, pageH);
                    bg.setFixedPosition(0, 0);
                    try { bg.setOpacity(1f); } catch (Exception e) {}
                    document.add(bg);
                }
            }
        } catch (Exception ignored) {
            // se não houver imagem de fundo, o certificado ainda é gerado sem decoração
        }

        // Título
        Paragraph title = new Paragraph("CERTIFICADO DE VOLUNTARIADO")
            .setFontSize(25)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(10)
            .setBold();
        if (bold != null) title.setFont(bold);
        document.add(title);

        // Texto introdutório
        Paragraph intro = new Paragraph("Certificamos para os devidos fins que:")
            .setFontSize(12)
            .setTextAlignment(TextAlignment.CENTER)
              .setMarginBottom(20);
        if (font != null) intro.setFont(font);
        document.add(intro);

        // Nome do voluntário (destaque)
        Paragraph name = new Paragraph(report.getVolunteerName().toUpperCase())
            .setFontSize(20)
            .setTextAlignment(TextAlignment.CENTER)
            .setBold()
            .setMarginBottom(20);
        if (bold != null) name.setFont(bold);
        document.add(name);

        // Conteúdo principal
        double hours = report.getTotalMinutes() / 60.0;
        String periodText = formatPeriod(startDate, endDate);
        String hoursFormatted = String.format(Locale.forLanguageTag("pt-BR"), "%.1f", hours);
        String activitiesLabel = report.getTotalActivities() > 1 ? "atividades aprovadas" : "atividade aprovada";

        String contentText = String.format(
            "Participou como voluntário(a) no setor de %s, cumprindo um total de %s horas de trabalho voluntário, por meio de %d %s, %s.",
            report.getDepartment(),
            hoursFormatted,
            report.getTotalActivities(),
            activitiesLabel,
            periodText
        );

        Paragraph content = new Paragraph(contentText)
            .setFontSize(12)
            .setTextAlignment(TextAlignment.JUSTIFIED)
            .setMarginTop(8)
            .setMarginBottom(10);
        content.setMaxWidth(620); // limitar largura para melhorar a formatação justificada
        content.setHorizontalAlignment(HorizontalAlignment.CENTER);
        if (font != null) content.setFont(font);
        document.add(content);

        // Data e local de emissão
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR"));
        String formattedDate = today.format(dateFormatter);

        Paragraph localDate = new Paragraph(String.format("%s - %s, %s", organizationCity, organizationState, formattedDate))
            .setFontSize(12)
            .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(10)
                .setMarginBottom(10);
        if (font != null) localDate.setFont(font);
        document.add(localDate);

        // Rodapé institucional (sem assinatura)
        String certNumber = String.format("CERT-%s-%d",
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
            volunteerId == null ? 0L : volunteerId);

        Paragraph footerCert = new Paragraph(String.format("Certificado nº: %s", certNumber))
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(18);
        if (font != null) footerCert.setFont(font);
        document.add(footerCert);

        Paragraph orgLine = new Paragraph(String.format("%s • CNPJ: %s", organizationName, organizationCnpj))
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(6);
        if (bold != null) orgLine.setFont(bold);
        document.add(orgLine);

        Paragraph recordNote = new Paragraph("Registro de contribuição - sem necessidade de assinatura.")
            .setFontSize(9)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(2);
        if (font != null) recordNote.setFont(font);
        document.add(recordNote);

        document.close();
        return baos.toByteArray();
    }

    private String formatPeriod(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.forLanguageTag("pt-BR"));

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
