package com.lta.bancocanon.software_bancario.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lta.bancocanon.software_bancario.Jwt.JwtService;
import com.lta.bancocanon.software_bancario.Usuario.Roles;
import com.lta.bancocanon.software_bancario.Usuario.Usuario;
import com.lta.bancocanon.software_bancario.Usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutentService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AutentResponse login(LoginRequest loginRequest) {
System.out.println("ENTRANDO AL SERVICIO DE AUTENTICACIÓN");
 
    authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(loginRequest.getNomUsuario(), loginRequest.getContrasena()));
        System.out.println("USUARIO AUTENTICADO");

    UserDetails usuario = usuarioRepository.findByNomUsuario(loginRequest.getNomUsuario()).orElseThrow();
    String token = jwtService.getToken(usuario);    

    System.out.println("GENERANDO TOKEN");
       
    return AutentResponse.builder()
            .token(token)
            .build();    
    
        }

    public AutentResponse registro(RegistroRequest registroRequest) {
    Usuario usuario = Usuario.builder()
                    .nombre(registroRequest.getNombre())
                    .apellido(registroRequest.apellido)
                    .correo(registroRequest.getCorreo())
                    .telefono(registroRequest.getTelefono())
                    .nomUsuario(registroRequest.getNomUsuario())
                    .contrasena(passwordEncoder.encode(registroRequest.getContrasena()))
                    .rol(Roles.USUARIO)
                    .build();     
                
                usuarioRepository.save(usuario);

                return AutentResponse.builder()
                        .token(jwtService.getToken(usuario))
                        .build();
    }

}
