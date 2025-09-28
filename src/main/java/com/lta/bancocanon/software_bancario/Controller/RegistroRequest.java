package com.lta.bancocanon.software_bancario.Controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data       //Agrega los getters y setters automaticamente
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
    String nomUsuario;
    String contrasena;
    String cedula;
    String nombre;
    String apellido;
    String correo;
    String telefono;
}
