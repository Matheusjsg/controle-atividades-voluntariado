package com.abcaa.sistema_atividades.business.service;


import com.abcaa.sistema_atividades.business.entities.TbUser;
import com.abcaa.sistema_atividades.business.repositories.UserRepository;
import com.abcaa.sistema_atividades.infrastructure.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TbUser saveUser(TbUser user) {

        try {
            emailExiste(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());

        }
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);

            if (existe) {
                throw new ConflictException("Email já adastrado" + email);
            }

        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente (String email){
        return userRepository.existsByEmail(email);
    }
}
