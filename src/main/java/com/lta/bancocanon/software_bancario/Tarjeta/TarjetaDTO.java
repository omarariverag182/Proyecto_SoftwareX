package com.lta.bancocanon.software_bancario.Tarjeta;

import java.time.LocalDate;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TARJETA", uniqueConstraints = {@UniqueConstraint(columnNames = {"numeroTarjeta"})})
public class TarjetaDTO {

    /*
     * DATOS QUE SE INSERTARÁN EN LA TABLA TARJETA EN SQL SERVER
     */

    @Id
    @GeneratedValue
    Integer idTarjeta;

    @Column(nullable = false, unique = true)
    String numeroTarjeta;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoTarjeta tipoTarjeta;

    Double cupoTotal;

    Double cupoDisponible;

    LocalDate fechaExpiración;

    @ManyToOne
    @JoinColumn(name = "id_cuenta", nullable = false)
    CuentaDTO cuenta;
    
}
