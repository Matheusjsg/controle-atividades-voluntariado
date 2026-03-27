package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.LoginDTO;
import com.abcaa.sistema_atividades.business.dto.RegisterDTO;
import com.abcaa.sistema_atividades.business.dto.TokenDTO;
import com.abcaa.sistema_atividades.business.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Registro e login de voluntários")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar voluntário", description = "Cria uma nova conta de voluntário")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody RegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o voluntário e retorna o token JWT")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }


        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@RequestParam String email) {
            authService.requestPasswordReset(email);
            return ResponseEntity.ok("Email enviado");
        }

        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(
                @RequestParam String token,
                @RequestParam String password) {

            authService.resetPassword(token, password);
            return ResponseEntity.ok("Senha redefinida");
        }
    }


