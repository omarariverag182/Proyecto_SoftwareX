package com.lta.bancocanon.software_bancario.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

    private static final String SECRET_KEY="u8Fv9sXzQpLr3tYw5aBcDeFgHiJkLmNoPqRsTuVwXyZ1234567890==";

    public String getToken(UserDetails usuario) {
    return getToken(new HashMap<>(), usuario);   
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails usuario){
        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(usuario.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    private Key getKey() {
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes); 
    }

    public String extractUsername(String token) {
        return getClaims(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String nomUsuario = extractUsername(token); 
        return (nomUsuario.equals(userDetails.getUsername())&& !isTokenExpired(token)) ;
    }

    private Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){

        return getClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){

        return getExpiration(token).before(new Date());
    }
}
