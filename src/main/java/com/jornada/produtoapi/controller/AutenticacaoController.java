package com.jornada.produtoapi.controller;


import com.jornada.produtoapi.dto.AutenticacaoDTO;
import com.jornada.produtoapi.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jornada.produtoapi.service.UsuarioService;


@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    //login + token

    private final UsuarioService usuarioService;


    @PostMapping("/login")
    public String fazerLogin(@RequestBody AutenticacaoDTO autenticacaoDTO) throws BusinessException {
        return usuarioService.fazerLogin(autenticacaoDTO);
    }

}
