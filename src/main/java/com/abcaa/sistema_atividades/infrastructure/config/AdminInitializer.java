package com.abcaa.sistema_atividades.infrastructure.config;

import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final VolunteerRepository volunteerRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name:Administrador}")
    private String adminName;

    public AdminInitializer(VolunteerRepository volunteerRepository, 
                           DepartmentRepository departmentRepository,
                           PasswordEncoder passwordEncoder) {
        this.volunteerRepository = volunteerRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (volunteerRepository.findByEmail(adminEmail).isEmpty()) {
            Department adminDept = departmentRepository.findAll().stream()
                .filter(d -> d.getName().contains("Administração"))
                .findFirst()
                .orElseGet(() -> departmentRepository.findById(1L).orElseThrow());

            Volunteer admin = new Volunteer();
            admin.setName(adminName);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setDepartment(adminDept);
            admin.setUserType(UserType.ADMIN);

            volunteerRepository.save(admin);
            System.out.println("✅ Admin criado: " + adminEmail);
        }
    }
}
