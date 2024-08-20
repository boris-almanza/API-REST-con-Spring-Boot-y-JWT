package com.example.security.Auth;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.security.entity.User;
import com.example.security.service.AuthUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
   
    
    public String  createToken(User  user){
        return generateToken(new HashMap<>(), user);  
    }

    private String generateToken(Map<String,Object> extraClaims, User user) {
        
        return Jwts.builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .claim("role", user.getAuthorities())
            .signWith(getKey())
            .compact();
    }

    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);

    }

    public Claims extractAllClaims( String token){
        return Jwts
                .parser()
                .verifyWith(getKey()) // 
                .build()
                .parseSignedClaims(token) 
                .getPayload();

}
    

  // una manera de validar

  /*   public Boolean validateToken(String token){
        Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token);
    return true;
        
    } */

    public Boolean validateToken(String token, UserDetails userDetails){

        String userToken = extractUsername(token);
        return (userToken.equals(userDetails.getUsername()) );
    }

    public String extractUsername( String token){
        return extractClaim( token , Claims::getSubject);
    }

    private <T> T extractClaim( String token , Function<Claims, T > resolver ){

        Claims claims = extractAllClaims(token);
       return  resolver.apply(claims);
    }
    
}
 





















  /* public String extractUsername(String token){
        return extractClaim( token , Claims::getSubject);
    }

    private <T> T extractClaim(String  token, Function<Claims, T> resolver ){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);


    } */