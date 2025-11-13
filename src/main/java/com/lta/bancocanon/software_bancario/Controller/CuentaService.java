package com.lta.bancocanon.software_bancario.Controller;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lta.bancocanon.software_bancario.Cuentas.CuentaDTO;
import com.lta.bancocanon.software_bancario.Cuentas.CuentaRepository;
import com.lta.bancocanon.software_bancario.Cuentas.TipoCuenta;
import com.lta.bancocanon.software_bancario.Tarjeta.TarjetaService;
import com.lta.bancocanon.software_bancario.Usuario.Usuario;
import com.lta.bancocanon.software_bancario.Usuario.UsuarioRepository;


@Service
public class CuentaService {
    
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TarjetaService tarjetaService;
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
        String numeroGenerado = generarNumeroCuenta(TipoCuenta.AHORROS);
        
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
     * crearCuentaCorriente():
     * Se verifica la existencia de un usuario con tipo de cuenta corriente, se arroja un error en caso de existencia
     * Será necesario aceptar los terminos y condiciones para crear contraseña
     * Se valida que la creacion de la cuenta sea de cuenta corriente.
     * Se genera el nombre del titular al unir nombre y apellido del usuario autenticado al iniciar sesión.
     * El número de cuenta se genera automaticamente con las iniciales, ejemplo: CO-000000
     */

     public CuentaDTO crearCuentaCorriente(CuentaCorrienteRequest cuentaCorrienteRequest){
        Usuario usuario = obtenerNomUsuarioAutent();

        if (cuentaRepository.existsByUsuarioAndTipoCuenta(usuario, TipoCuenta.CORRIENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una cuenta con este usuario");
        }

        if (Boolean.FALSE.equals(cuentaCorrienteRequest.getAceptarTYC())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe aceptar terminos y condiciones");
        }

        if (!EnumSet.of(TipoCuenta.CORRIENTE).contains(TipoCuenta.CORRIENTE)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de cuenta no válido.");
        }
        
        String nombreTitular = usuario.getNomUsuario()+" "+usuario.getApellido();
        String numeroGenerado = generarNumeroCuenta(TipoCuenta.CORRIENTE);
        CuentaDTO cuenta = CuentaDTO.builder()
                .numeroCuenta(numeroGenerado)
                .nombreTitular(nombreTitular)
                .tipoCuenta(TipoCuenta.CORRIENTE)
                .saldo(cuentaCorrienteRequest.getSaldo())
                .cupo(cuentaCorrienteRequest.getCupo())
                .sobregiro(cuentaCorrienteRequest.getSobregiro())
                .cupoSobregiro(cuentaCorrienteRequest.getCupoSobregiro())
                .clave(cuentaCorrienteRequest.getClave())
                .aceptarTYC(cuentaCorrienteRequest.getAceptarTYC())
                .usuario(usuario)
                .build();

                CuentaDTO cuentaGuardada =  cuentaRepository.save(cuenta);
        

        if (TipoCuenta.CORRIENTE.equals(cuentaGuardada.getTipoCuenta())) {
            tarjetaService.crearTarjeta(cuentaGuardada);
        }
    return cuentaGuardada;
                
    }


    /*
     * Metodo que genera un numero de cuenta de ahorros usando un prefijo segun el tipo de cuenta 
     * seguido de 6 numeros aleatorios unicos.
     */
    private String generarNumeroCuenta(TipoCuenta tipoCuenta){
        String prefijo;

        switch (tipoCuenta) {
            case AHORROS:
                prefijo = "AH-";
                break;
        case CORRIENTE:
                prefijo = "CO-";
                break;
            default:
                prefijo = "CU-";
                break;
        }
        
        
        String numero;
        do {
            numero = prefijo+String.format("%06d", (int)(Math.random()*1_000_000));
        } while (cuentaRepository.existsByNumeroCuenta(numero));
        return numero;
    }


}
