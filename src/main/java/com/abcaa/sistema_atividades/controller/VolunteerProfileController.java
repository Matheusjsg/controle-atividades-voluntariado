package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.VolunteerProfileDTO;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.service.VolunteerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer/profile")
@Tag(name = "Perfil do Voluntário", description = "Gerenciamento de dados pessoais do voluntário")
public class VolunteerProfileController {

    private final VolunteerProfileService profileService;

    public VolunteerProfileController(VolunteerProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Salvar perfil", description = "Cria ou atualiza os dados pessoais do voluntário autenticado")
    public ResponseEntity<VolunteerProfileDTO> save(@AuthenticationPrincipal Volunteer volunteer,
                                                     @RequestBody VolunteerProfileDTO dto) {
        return ResponseEntity.ok(profileService.save(volunteer.getId(), dto));
    }

    @GetMapping
    @Operation(summary = "Ver próprio perfil", description = "Retorna os dados pessoais do voluntário autenticado")
    public ResponseEntity<VolunteerProfileDTO> getOwn(@AuthenticationPrincipal Volunteer volunteer) {
        return ResponseEntity.ok(profileService.findByVolunteerId(volunteer.getId()));
    }

    @GetMapping("/{volunteerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ver perfil por ID", description = "Retorna os dados pessoais de qualquer voluntário — apenas ADMIN")
    public ResponseEntity<VolunteerProfileDTO> getById(@PathVariable Long volunteerId) {
        return ResponseEntity.ok(profileService.findByVolunteerId(volunteerId));
    }
}
