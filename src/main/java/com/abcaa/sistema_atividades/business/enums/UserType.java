package com.abcaa.sistema_atividades.business.enums;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Tipo de usuário no sistema")
public enum UserType {
    @Schema(description = "Usuário voluntário com permissões básicas")
    VOLUNTEER,

    @Schema(description = "Usuário administrador com permissões completas")
    ADMIN
}
