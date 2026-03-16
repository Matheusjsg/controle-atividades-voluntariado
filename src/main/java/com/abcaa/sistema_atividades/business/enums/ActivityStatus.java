package com.abcaa.sistema_atividades.business.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status de aprovação da atividade")
public enum ActivityStatus {
    @Schema(description = "Atividade aguardando aprovação")
    PENDING,

    @Schema(description = "Atividade aprovada")
    APPROVED,

    @Schema(description = "Atividade rejeitada")
    REJECTED
}
