package com.lta.bancocanon.software_bancario.Cuentas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lta.bancocanon.software_bancario.Usuario.Usuario;

public interface CuentaRepository extends JpaRepository <CuentaDTO, Integer> {
    
    //PERMITE VALIDAR SI EL NUMERO DE CUENTA EXISTE
    Boolean existsByNumeroCuenta(String numeroCuenta);
    
    //PERMITE VALIDAR QUE EXISTA UN USUARIO CON UN TIPO DE CUENTA
    Boolean existsByUsuarioAndTipoCuenta(Usuario usuario, TipoCuenta ahorros);
}
