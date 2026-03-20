package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.LoginDTO;
import com.abcaa.sistema_atividades.business.dto.RegisterDTO;
import com.abcaa.sistema_atividades.business.dto.TokenDTO;
import com.abcaa.sistema_atividades.business.entities.Department;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.enums.UserType;
import com.abcaa.sistema_atividades.business.repositories.DepartmentRepository;
import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
import com.abcaa.sistema_atividades.infrastructure.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final VolunteerRepository volunteerRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(VolunteerRepository volunteerRepository, DepartmentRepository departmentRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.volunteerRepository = volunteerRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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
        return new TokenDTO(token, volunteer.getName(), volunteer.getUserType().name());
    }

    public TokenDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        Volunteer volunteer = volunteerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        String token = jwtService.generateToken(volunteer);
        return new TokenDTO(token, volunteer.getName(), volunteer.getUserType().name());
    }
}
