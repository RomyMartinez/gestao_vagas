package com.romy.gestao_vagas.modules.canditate.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romy.gestao_vagas.modules.canditate.CanditateEntity;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/canditate")
public class CanditateController {
    
    @PostMapping("")
    public void create(@Valid @RequestBody CanditateEntity canditateEntity){
        System.out.println("Nome" + canditateEntity.getName());
    }

    @GetMapping("")
    public String getMethodName() {
        return new String("sada");
    }
    
}
