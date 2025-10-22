package com.lta.bancocanon.software_bancario.Tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<TarjetaDTO, Integer>{
    Boolean existsByNumeroTarjeta(String numeroTarjeta);
    
}
