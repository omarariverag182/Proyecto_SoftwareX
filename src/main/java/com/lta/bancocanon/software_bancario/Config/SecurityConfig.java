package com.lta.bancocanon.software_bancario.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;

import com.lta.bancocanon.software_bancario.Jwt.JwtAuthentificationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthentificationFilter jwtAuthentificationFilter;
    private final AuthenticationProvider authenticationProvider; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> 
              authRequest   
                .requestMatchers("/controller/registro","/controller/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/usuario/**").hasRole("USUARIO")
                .anyRequest().authenticated()
                )  
            .sessionManagement(sessionManager
                -> sessionManager
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
        }       
    
}


/*
 * csrf (Cross-Site Request Forguery): se usa para validar autenticacion por token
 *      en las peticiones POST del servicio API, en este caso
 *      se desabilito usando: .csrf(csrf -> csrf.disable())
 * 
 * requestMatchers: realiza la conexion a la ruta identificada y permite todas, tambien indica los roles del usuario
 * posteriormente realiza la autentificacion del token 
 * 
 */