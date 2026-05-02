package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.LoginDTO;
import com.abcaa.sistema_atividades.dto.RegisterDTO;
import com.abcaa.sistema_atividades.dto.TokenDTO;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import com.abcaa.sistema_atividades.infrastructure.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService - Testes de Autenticação")
class AuthServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;
    private Department department;
    private Volunteer volunteer;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "Administrativo");
        
        volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("João Silva");
        volunteer.setEmail("joao@test.com");
        volunteer.setPassword("encodedPassword");
        volunteer.setDepartment(department);
        volunteer.setUserType(UserType.VOLUNTEER);
    }

    @Test
    @DisplayName("Deve registrar novo voluntário com sucesso")
    void shouldRegisterNewVolunteerSuccessfully() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("João Silva");
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(1L);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        when(jwtService.generateToken(any(Volunteer.class))).thenReturn("jwt-token");

        TokenDTO result = authService.register(dto);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("jwt-token");
        assertThat(result.getName()).isEqualTo("João Silva");
        assertThat(result.getUserType()).isEqualTo("VOLUNTEER");

        verify(volunteerRepository).findByEmail(dto.getEmail());
        verify(departmentRepository).findById(1L);
        verify(passwordEncoder).encode(dto.getPassword());
        verify(volunteerRepository).save(any(Volunteer.class));
        verify(jwtService).generateToken(any(Volunteer.class));
    }

    @Test
    @DisplayName("Deve registrar voluntário com departamento padrão quando não informado")
    void shouldRegisterWithDefaultDepartment() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("Maria Santos");
        dto.setEmail("maria@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(null);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        when(jwtService.generateToken(any(Volunteer.class))).thenReturn("jwt-token");

        TokenDTO result = authService.register(dto);

        assertThat(result).isNotNull();
        verify(departmentRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar email já existente")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("joao@test.com");

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(volunteer));

        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email já cadastrado.");

        verify(volunteerRepository).findByEmail(dto.getEmail());
        verify(volunteerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando departamento não existe")
    void shouldThrowExceptionWhenDepartmentNotFound() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("João Silva");
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(99L);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Setor não encontrado.");

        verify(volunteerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando departamento padrão não existe")
    void shouldThrowExceptionWhenDefaultDepartmentNotFound() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("João Silva");
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(null);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Setor padrão não encontrado.");

        verify(volunteerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve fazer login com credenciais válidas")
    void shouldLoginWithValidCredentials() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(volunteer));
        when(jwtService.generateToken(volunteer)).thenReturn("jwt-token");

        TokenDTO result = authService.login(dto);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("jwt-token");
        assertThat(result.getName()).isEqualTo("João Silva");
        assertThat(result.getUserType()).isEqualTo("VOLUNTEER");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(volunteerRepository).findByEmail(dto.getEmail());
        verify(jwtService).generateToken(volunteer);
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer login com credenciais inválidas")
    void shouldThrowExceptionWhenLoginWithInvalidCredentials() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("joao@test.com");
        dto.setPassword("senhaErrada");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Credenciais inválidas");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(volunteerRepository, never()).findByEmail(any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando voluntário não encontrado após autenticação")
    void shouldThrowExceptionWhenVolunteerNotFoundAfterAuth() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Voluntário não encontrado.");

        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Deve criar voluntário com tipo VOLUNTEER por padrão")
    void shouldCreateVolunteerWithDefaultUserType() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("João Silva");
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(1L);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(Volunteer.class))).thenReturn("jwt-token");

        when(volunteerRepository.save(any(Volunteer.class))).thenAnswer(invocation -> {
            Volunteer saved = invocation.getArgument(0);
            assertThat(saved.getUserType()).isEqualTo(UserType.VOLUNTEER);
            return saved;
        });

        authService.register(dto);

        verify(volunteerRepository).save(any(Volunteer.class));
    }

    @Test
    @DisplayName("Deve codificar senha antes de salvar")
    void shouldEncodePasswordBeforeSaving() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("João Silva");
        dto.setEmail("joao@test.com");
        dto.setPassword("senha123");
        dto.setDepartmentId(1L);

        when(volunteerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(passwordEncoder.encode("senha123")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(Volunteer.class))).thenReturn("jwt-token");

        when(volunteerRepository.save(any(Volunteer.class))).thenAnswer(invocation -> {
            Volunteer saved = invocation.getArgument(0);
            assertThat(saved.getPassword()).isEqualTo("encodedPassword");
            return saved;
        });

        authService.register(dto);

        verify(passwordEncoder).encode("senha123");
    }
}
