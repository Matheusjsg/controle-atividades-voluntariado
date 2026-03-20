package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/certificate")
@Tag(name = "Certificados", description = "Geração de certificados de voluntariado")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/generate/{volunteerId}")
    @Operation(
        summary = "Gerar certificado em PDF",
        description = "Gera certificado para voluntários com no mínimo 20 horas aprovadas. " +
                     "Aceita filtros opcionais de período (startDate e endDate)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou período inválido"),
        @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Voluntário não possui horas suficientes")
    })
    public ResponseEntity<byte[]> generateCertificate(
            @PathVariable Long volunteerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        byte[] pdf = certificateService.generateCertificate(volunteerId, startDate, endDate);

        String filename = String.format("certificado_voluntariado_%s.pdf", 
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
            .filename(filename)
            .build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
