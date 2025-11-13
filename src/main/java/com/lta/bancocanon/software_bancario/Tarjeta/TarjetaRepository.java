package com.lta.bancocanon.software_bancario.Tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<TarjetaDTO, Integer>{
    
    //Valida la existencia de un numero de tarjeta
    Boolean existsByNumeroTarjeta(String numeroTarjeta);
    
}
