package com.lta.bancocanon.software_bancario.Cuentas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lta.bancocanon.software_bancario.Usuario.Usuario;

public interface CuentaRepository extends JpaRepository <CuentaDTO, Integer> {
    Boolean existsByNumeroCuenta(String numeroCuenta);
    
    Boolean existsByUsuarioAndTipoCuenta(Usuario usuario, TipoCuenta ahorros);
}
