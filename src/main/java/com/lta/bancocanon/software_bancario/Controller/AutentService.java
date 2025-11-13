package com.lta.bancocanon.software_bancario.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


    /*
     * INICIO DE SESION (LOGIN):
     * Se pedirá al usuario su nombre de usuario y contraseña.
     * Al iniciar sesión se devolverá un token de autenticacion de usuario.
     * Si el usuario no existe se enviará el mensaje de error.
     */
    public AutentResponse login(LoginRequest loginRequest) {
    authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(loginRequest.getNomUsuario(), loginRequest.getContrasena()));

    UserDetails usuario = usuarioRepository.findByNomUsuario(loginRequest.getNomUsuario()).orElseThrow();
    String token = jwtService.getToken(usuario);    
       
    return AutentResponse.builder()
            .token(token)
            .build();    
    
        }
    /*
     * SERVICIOS PARA CAMBIAR CONTRASEÑA:
     * Se verifica la existencia del usuario, si no existe arroja el mensaje "usuario no encontrado"
     * Si las contraseñas no coinciden se enviará el mensaje de error
     * Si el usuario es encontrado y es confimada correctamente, se encriptará y se restablecerá la contraseña
     */

    public void cambiarContrasena(CambioContrasena cambioContrasena){
        Usuario usuario = usuarioRepository.findByNomUsuario(cambioContrasena.getNomUsuario())
        .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));

        if (!cambioContrasena.getNuevaContrasena().equals(cambioContrasena.getConfirmContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        usuario.setContrasena(passwordEncoder.encode(cambioContrasena.getNuevaContrasena()));
        usuarioRepository.save(usuario);
    }

    /*REGISTRO
    * El usuario deberá ingresar sus credenciales (cedula, nombre, apellido, correo, telefono, nombre de usuario, contraseña)
    * La contraseña será encriptada y el rol usuario será definido automaticamente.
    * El usuario deberá confirmar su contraseña para dar por terminada la creación de la cuenta.
    */

    public AutentResponse registro(RegistroRequest registroRequest) {
        if (!registroRequest.getContrasena().equals(registroRequest.getConfirmContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    Usuario usuario = Usuario.builder()
                    .cedula(registroRequest.getCedula())
                    .nombre(registroRequest.getNombre())
                    .apellido(registroRequest.getApellido())
                    .correo(registroRequest.getCorreo())
                    .telefono(registroRequest.getTelefono())
                    .nomUsuario(registroRequest.getNomUsuario())
                    .contrasena(passwordEncoder.encode(registroRequest.getContrasena()))
                    .rol(Roles.USUARIO)
                    .build();                 
                usuarioRepository.save(usuario);

                return AutentResponse.builder()
                        .build();
    }

}
