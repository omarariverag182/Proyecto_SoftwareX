package com.lta.bancocanon.software_bancario.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class Controller {

    private final AutentService autentService;

    @PostMapping(value = "login")
    public ResponseEntity<AutentResponse> login(@RequestBody LoginRequest loginRequest){
        System.out.println("ENTRANDO AL MÉTODO LOGIN DEL CONTROLADOR");
        return ResponseEntity.ok(autentService.login(loginRequest));
    }
    
    @PostMapping(value = "registro")
    public ResponseEntity<AutentResponse> registro(@RequestBody RegistroRequest registroRequest){
        return ResponseEntity.ok(autentService.registro(registroRequest));
    }  
}

/*
 * El controller expone los metodos para generar la autentificacion
 */
