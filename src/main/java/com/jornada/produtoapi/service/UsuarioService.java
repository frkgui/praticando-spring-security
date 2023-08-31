package com.jornada.produtoapi.service;


import com.jornada.produtoapi.dto.AutenticacaoDTO;
import com.jornada.produtoapi.entity.UsuarioEntity;
import com.jornada.produtoapi.exceptions.BusinessException;
import com.jornada.produtoapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public String fazerLogin(AutenticacaoDTO autenticacaoDTO) throws BusinessException {

        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByLoginAndSenha(autenticacaoDTO.getLogin(), autenticacaoDTO.getSenha());

       if(usuarioEntityOptional.isEmpty()){
           throw new BusinessException("Usuario ou senha inválidos!");
       }
       UsuarioEntity usuario = usuarioEntityOptional.get();
       String tokenGerado = usuario.getLogin() + "-" + usuario.getSenha();

       return tokenGerado;

    }

    // Método de validação de Token -> TokenAuthentication
    public UsuarioEntity validarToken(String token) throws BusinessException {
        if(token == null){
            throw new BusinessException("Token inválido!");
        }
        String tokenClean = token.replace("Bearer", "");
        String[] usuarioESenha = tokenClean.split("-");
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByLoginAndSenha(usuarioESenha[0], usuarioESenha[1]);
        return usuarioEntityOptional.orElseThrow(() -> new BusinessException("Usuário e senha inválidos!"));
    }

}
