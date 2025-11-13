package com.lta.bancocanon.software_bancario.Tarjeta;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;
import com.lta.bancocanon.software_bancario.Cuentas.TipoCuenta;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository ){
        this.tarjetaRepository = tarjetaRepository;
    }

    /*
     * CREAR TARJETA:
     * SOLO LAS CUENTAS CORRIENTES GENERAN TARJETA DE CRÉDITO AUTOMATICAMENTE.
     * SE GENERA UN NUMERO DE TARJETA ALEATORIAMENTE, TD = Tarjeta Debito, TC = Tarjeta Credito
     * 
     */
    public TarjetaDTO crearTarjeta(CuentaDTO cuentaDTO){
        if (!TipoCuenta.CORRIENTE.equals(cuentaDTO.getTipoCuenta())){
            throw new IllegalArgumentException("Solo las cuentas corrientes generan tarjeta de crédito.");
            
        }

        String numeroGenerado = generarNumeroTarjeta(TipoCuenta.CORRIENTE);
        TarjetaDTO tarjetaDTO = TarjetaDTO.builder()
                    .numeroTarjeta(numeroGenerado)
                    .tipoTarjeta(TipoTarjeta.CREDITO)
                    .cupoTotal(cuentaDTO.getCupo())
                    .cupoDisponible(cuentaDTO.getCupo())
                    .fechaExpiración(LocalDate.now().plusYears(5))
                    .cuenta(cuentaDTO)
                    .build();

                    return tarjetaRepository.save(tarjetaDTO);
    }


      private String generarNumeroTarjeta(TipoCuenta tipoCuenta){
        String prefijo;

        switch (tipoCuenta) {
            case AHORROS:
                prefijo = "TD-"; 
                break;
        case CORRIENTE:
                prefijo = "TC-";
                break;
            default:
                prefijo = "TX-";
                break;
        }
        
        String numero;
        do {
            numero = prefijo+String.format("%06d", (int)(Math.random()*1_000_000));
        } while (tarjetaRepository.existsByNumeroTarjeta(numero));

        return numero;
    }
    
}
