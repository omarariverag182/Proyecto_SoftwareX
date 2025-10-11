/*
 * CuentaDTO:
 * Permite crear la entidad o tabla en la base de datos con los campos requeridos.
 */
package com.lta.bancocanon.software_bancario.Cuentas;
import com.lta.bancocanon.software_bancario.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUENTA", uniqueConstraints = {@UniqueConstraint(columnNames = {"numeroCuenta"})})
public class CuentaDTO {
   
   @Id
   @GeneratedValue
   @Column(name = "id_cuenta")
   Integer idCuenta;
   
   @Column(nullable = false)
   String numeroCuenta;

   String nombreTitular;

    @Enumerated(EnumType.STRING)
   TipoCuenta tipoCuenta;
   
   @Column(nullable = false)
   @DecimalMin(value ="0.0", inclusive = false) //No permite valores negativos
   Double saldo;


   @ManyToOne
   @JoinColumn(name = "cliente_id", nullable = false)//almacena la id del usuario logueado
   Usuario usuario;

   } 
