package com.lta.bancocanon.software_bancario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {
    
    @Autowired
    private CuentaService cuentaService;

/*
 * EXPOSICION DEL ENDPOINT PARA CREAR CUENTA DE AHORROS
 */
    @PostMapping("/ahorros")
    public ResponseEntity<CuentaDTO> crearCuentaAhorros(@Valid @RequestBody CuentaAhorrosRequest cuentaAhorrosRequest){
        CuentaDTO cuentaCreada = cuentaService.crearCuentaAhorros(cuentaAhorrosRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaCreada);
    }

    @PostMapping("/corriente")
    public ResponseEntity<CuentaDTO> crearCuentaCorriente(@Valid @RequestBody CuentaCorrienteRequest cuentaCorrienteRequest){
        CuentaDTO cuentaCreada = cuentaService.crearCuentaCorriente(cuentaCorrienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaCreada);
    }

}
