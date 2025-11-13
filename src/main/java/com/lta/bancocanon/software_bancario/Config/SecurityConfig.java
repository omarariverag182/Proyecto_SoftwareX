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

/*
 * IMPLEMENTACION DE CORS PARA EXPOSICION DEL BACKEND A PAGINA WEB
 */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
                corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type", "X-XSRF-TOKEN"));
                corsConfig.setAllowCredentials(true);

                var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfig);
                return corsConfig;
            }))
/*
 * CONFIGURACION DE ENDPOINTS DE ACCESO PERMITIDOS
 * autheticated: es requerida una autenticacion para acceder al endpoint
 */
            .authorizeHttpRequests(authRequest -> 
              authRequest   
                .requestMatchers("/controller/registro","/controller/login","/controller/cambioContrasena").permitAll()
                .requestMatchers("/usuario/**").hasRole("USUARIO")
                .requestMatchers("/cuentas/ahorros").authenticated()
                .requestMatchers("/cuentas/corriente").authenticated()
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