package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.LoginDTO;
import com.abcaa.sistema_atividades.business.dto.RegisterDTO;
import com.abcaa.sistema_atividades.business.dto.TokenDTO;
import com.abcaa.sistema_atividades.business.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.entities.PasswordResetToken;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.enums.UserType;
import com.abcaa.sistema_atividades.business.repositories.DepartmentRepository;
import com.abcaa.sistema_atividades.business.repositories.PasswordResetTokenRepository;
import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
import com.abcaa.sistema_atividades.infrastructure.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${url.reset-password}")
    private String resetPasswordUrl;

    private final VolunteerRepository volunteerRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;


    public AuthService(VolunteerRepository volunteerRepository, DepartmentRepository departmentRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.volunteerRepository = volunteerRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;

    }

    public TokenDTO register(RegisterDTO dto) {
        if (volunteerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        Department department = dto.getDepartmentId() != null
                ? departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."))
                : departmentRepository.findById(1L)
                        .orElseThrow(() -> new EntityNotFoundException("Setor padrão não encontrado."));

        Volunteer volunteer = new Volunteer();
        volunteer.setName(dto.getName());
        volunteer.setEmail(dto.getEmail());
        volunteer.setPassword(passwordEncoder.encode(dto.getPassword()));
        volunteer.setDepartment(department);
        volunteer.setUserType(UserType.VOLUNTEER);

        volunteerRepository.save(volunteer);

        String token = jwtService.generateToken(volunteer);
        return new TokenDTO(token, volunteer.getName(), volunteer.getId(),
                                   volunteer.getDepartment().getId(), volunteer.getUserType().name());
    }

    public TokenDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        Volunteer volunteer = volunteerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        String token = jwtService.generateToken(volunteer);
        return new TokenDTO(token, volunteer.getName(), volunteer.getId(),
                                   volunteer.getDepartment().getId(), volunteer.getUserType().name());
    }


    public void requestPasswordReset(String email) {
        Volunteer user = volunteerRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Email não encontrado"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setVolunteerId(user);
        resetToken.setExpiration(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        String link = resetToken + "/auth/reset-password?token=" + token;

        emailService.sendEmail(user.getEmail(), "Recuperação de senha", link);
    }

    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Volunteer user = resetToken.getVolunteerId();

        user.setPassword(passwordEncoder.encode(newPassword));
        volunteerRepository.save(user);

        tokenRepository.delete(resetToken);
    }


}
