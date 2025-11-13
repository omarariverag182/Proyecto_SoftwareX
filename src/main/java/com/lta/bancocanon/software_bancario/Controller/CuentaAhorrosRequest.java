package com.lta.bancocanon.software_bancario.Controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAhorrosRequest {
    
    String nombreTitular;
    Double saldo;
    
}
/*
 * Clase donde se obtienen datos para creación cuenta de ahorros.
 * El nombre del titular se genera a partir de los datos de usuario al iniciar sesión.
 */