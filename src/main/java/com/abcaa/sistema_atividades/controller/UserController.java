package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.entities.TbUser;
import com.abcaa.sistema_atividades.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tb_user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<TbUser> saveUser(TbUser tb_user){
        return ResponseEntity.ok(userService.saveUser(tb_user));
    }
}