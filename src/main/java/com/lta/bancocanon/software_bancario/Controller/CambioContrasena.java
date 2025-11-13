package com.lta.bancocanon.software_bancario.Controller;

import lombok.Data;

@Data
public class CambioContrasena {
    String nomUsuario;
    String nuevaContrasena;
    String confirmContrasena;
}
/*
 * Clase donde se obtienen datos de usuario para cambio de contraseña.
 */