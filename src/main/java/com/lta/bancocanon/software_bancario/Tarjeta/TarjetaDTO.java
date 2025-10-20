package com.lta.bancocanon.software_bancario.Tarjeta;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Id
    @GeneratedValue
    Integer idTarjeta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    String numeroTarjeta;

    TipoTarjeta tipoTarjeta;

    Double cupoTotal;

    Double cupoDisponible;

    LocalDate fechaExpiración;

    @ManyToOne
    @JoinColumn(name = "id_cuenta", nullable = false)
    CuentaDTO cuenta;
    
}
