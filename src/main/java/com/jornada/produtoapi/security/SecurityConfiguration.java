package com.jornada.produtoapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean // Filtra as requisições
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Configuração das permissões/filtros

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        // Definindo permissão de acesso (autenticação).
        http.authorizeHttpRequests((authz) ->
                authz.requestMatchers("/autenticacao/**").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }

    // Liberando Swagger
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs",
                "v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/");
    }


}
