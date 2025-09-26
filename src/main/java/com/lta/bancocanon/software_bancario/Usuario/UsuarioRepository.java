package com.lta.bancocanon.software_bancario.Usuario;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByNomUsuario(String nomUsuario);
}
