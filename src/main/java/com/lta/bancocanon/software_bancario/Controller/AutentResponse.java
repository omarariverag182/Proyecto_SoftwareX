package com.lta.bancocanon.software_bancario.Controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutentResponse {

    String token;
}
/*
 * Clase donde se almacena el token de autenticación
 */