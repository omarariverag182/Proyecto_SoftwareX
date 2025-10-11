package com.lta.bancocanon.software_bancario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;
import com.lta.bancocanon.software_bancario.Cuentas.CuentaRepository;
import com.lta.bancocanon.software_bancario.Cuentas.TipoCuenta;
import com.lta.bancocanon.software_bancario.Usuario.Usuario;
import com.lta.bancocanon.software_bancario.Usuario.UsuarioRepository;


@Service
public class CuentaService {
    
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
     * Metodo para obtener el nombre de usuario, sí el usuario esta autenticado al iniciar sesion permitirá crear la cuenta.
     */
    public Usuario obtenerNomUsuarioAutent(){
   String nomUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByNomUsuario(nomUsuario)
          .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO."));
    }

    /*
     * crearCuentaAhorros()
     * permite crear la cuenta de ahorros, si el usuario ya esta autenticado (obtenerNomUsuarioAutent()) y sí la cuenta ya
     * existe enviará un mensaje de error indicando que ya existe una cuenta con ese usuario, sí no es asi el metodo creará
     * la cuenta, le enviará los datos a CuentaDTO y esta clase hará la inserción de los datos en la BD.
     */
    public CuentaDTO crearCuentaAhorros(CuentaAhorrosRequest cuentaAhorrosRequest){
        
        Authentication autent = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Autenticación actual: " + autent);

        Usuario usuario = obtenerNomUsuarioAutent();
        Boolean cuentaExiste = cuentaRepository.existsByUsuarioAndTipoCuenta(usuario, TipoCuenta.AHORROS);

        //Bloque para verificar si la cuenta existe
        if (cuentaExiste) {
            System.out.println("CUENTA EXISTE");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"YA EXISTE UNA CUENTA DE AHORROS PARA ESTE USUARIO");
        }

        String nombreTitular = usuario.getNomUsuario()+" "+usuario.getApellido();
        String numeroGenerado = generarNumeroCuenta();
        
        //Bloque que captura los datos del usuario loguedo para crear la cuenta
        CuentaDTO cuenta = CuentaDTO.builder()
            .numeroCuenta(numeroGenerado)//utiliza el metodo generarNumeroCuenta para crear el numero automaticamente
            .nombreTitular(nombreTitular)//Usa el nombre y el apellido del usuario logueado para crear el nombre del titular
            .tipoCuenta(TipoCuenta.AHORROS)//Definicion del tipo de cuenta
            .saldo(cuentaAhorrosRequest.getSaldo())//Agrega el saldo inicial de la cuenta
            .usuario(usuario)//Obtiene la id del usuario en la BD y la inserta en este campo para relacionar la cuenta con el usuario
            .build();

        return cuentaRepository.save(cuenta);
    }

    /*
     * Metodo que genera un numero de cuenta de ahorros usando AH seguido de 6 numeros aleatorios unicos.
     */
    private String generarNumeroCuenta(){
        String numero;
        do {
            numero = "AH-"+String.format("%06d", (int)(Math.random()*1_000_000));
        } while (cuentaRepository.existsByNumeroCuenta(numero));
        return numero;
    }


}
