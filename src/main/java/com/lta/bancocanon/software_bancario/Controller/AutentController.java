/*
 * La clase controller expone las al API REST para realizar el debido proceso de registro e inicio de sesion
 * Adicional, expone el endpoint para cambiar la contraseña.
 */

package com.lta.bancocanon.software_bancario.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class AutentController {

    private final AutentService autentService;

    @PostMapping(value = "login")
    public ResponseEntity<AutentResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(autentService.login(loginRequest));
    }
    
    @PostMapping(value = "registro")
    public ResponseEntity<AutentResponse> registro(@RequestBody RegistroRequest registroRequest){
        AutentResponse autentResponse = autentService.registro(registroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(autentResponse);
    }
    
    @PostMapping("cambioContrasena")
    public ResponseEntity<?> cambioContrasena(@RequestBody CambioContrasena CambioContrasenaRequest){
        autentService.cambiarContrasena(CambioContrasenaRequest);
        return ResponseEntity.ok("Contraseña restablecida correctamente");
    }

}

