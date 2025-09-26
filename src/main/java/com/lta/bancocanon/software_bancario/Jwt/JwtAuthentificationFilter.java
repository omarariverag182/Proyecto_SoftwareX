package com.lta.bancocanon.software_bancario.Jwt;

import java.io.IOException;


import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String nomUsuario;
       
    
        if (token ==null) {
                System.out.println("VALIDANDO TOKEN");
       
            filterChain.doFilter(request, response);
            System.out.println("TOKEN NULO");
            return;
        }
            System.out.println("INICIANDO VARIABLE NOMUSUARIO");
        nomUsuario = jwtService.extractUsername(token);

        if (nomUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           System.out.println("USANDO USERDETAILS");
            UserDetails userDetails = userDetailsService.loadUserByUsername(nomUsuario);

            if (jwtService.isTokenValid(token, userDetails)) {
                System.out.println("VALIDANDO TOKEN");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    null, 
                    userDetails.getAuthorities());
                System.out.println("AUTORIZANDO ACCESO");
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        System.out.println("HACIENDO FILTERCHAIN");
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}