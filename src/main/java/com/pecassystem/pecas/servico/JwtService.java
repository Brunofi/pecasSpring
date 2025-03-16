// src/main/java/com/pecassystem/pecas/servico/JwtService.java
package com.pecassystem.pecas.servico;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    // Usando o novo padrão de construção de chaves
    private static final MacAlgorithm ALGORITMO = Jwts.SIG.HS256;
    private final SecretKey SECRET_KEY = ALGORITMO.key().build(); // Chave gerada corretamente

    public String generateToken(String username, String perfil, String nome) {
        return Jwts.builder()
                .subject(username)
                .claim("perfil", perfil) // Adiciona o perfil como claim
                .claim("nome", nome) 
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY, ALGORITMO)
                .compact();
    }

    public String validateToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(SECRET_KEY) // Novo método para verificação
                .build()
                .parseSignedClaims(token); // Novo nome do método

        return jws.getPayload().getSubject();
    }
}