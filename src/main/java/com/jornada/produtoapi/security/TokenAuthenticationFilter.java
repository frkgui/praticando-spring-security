package com.jornada.produtoapi.security;

import com.jornada.produtoapi.entity.UsuarioEntity;
import com.jornada.produtoapi.exceptions.BusinessException;
import com.jornada.produtoapi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Recuperar Request

        String tokenBearer = request.getHeader("Authorization");

        // Validar o Token
        try {
            UsuarioEntity usuarioEntity = usuarioService.validarToken(tokenBearer);
            UsernamePasswordAuthenticationToken tokenSpring = new UsernamePasswordAuthenticationToken(usuarioEntity, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(tokenSpring);
        } catch (BusinessException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        // Executa os próximos filtros;
        filterChain.doFilter(request,response);

        }



    }

