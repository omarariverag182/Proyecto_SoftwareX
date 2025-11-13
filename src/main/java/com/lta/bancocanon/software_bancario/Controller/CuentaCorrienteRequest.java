package com.lta.bancocanon.software_bancario.Controller;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaCorrienteRequest {
    
    @DecimalMin(value = "0.0", inclusive = false)
    Double saldo;

    @DecimalMin(value = "0.0", inclusive = false)
    Double cupo;
    
    Double cupoSobregiro;    
    Boolean sobregiro;

    String clave;
    Boolean aceptarTYC;
    
}
