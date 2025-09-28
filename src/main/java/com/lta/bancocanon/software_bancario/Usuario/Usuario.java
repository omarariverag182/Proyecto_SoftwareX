package com.lta.bancocanon.software_bancario.Usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data       //Agrega los getters y setters automaticamente
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity     //Para el uso del JPA
@Table (name = "Usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"nomUsuario"})})
public class Usuario implements UserDetails {
    
    @Id     //anotacion que identifica al dato como id
    @GeneratedValue //genera el valor id de manera automatica
    Integer id;
    
    @Column(nullable = false) //no permite realizar INSERT sin el dato nomUsuario 
    String nomUsuario;
    
    String cedula;

    String contrasena;
    
    String nombre;
    
    String apellido;
    
    String correo;
    
    String telefono;

    @Enumerated(EnumType.STRING)
    Roles rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+rol.name()));
    } 
    
    
    @Override
    public String getPassword() {
    return contrasena;
    }
    @Override
    public String getUsername() {
        return nomUsuario;
    }


    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }


}
