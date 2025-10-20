package com.lta.bancocanon.software_bancario.Tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<TarjetaDTO, Integer>{
    boolean existByNumeroTarjeta(String numeroTarjeta);
    
}
