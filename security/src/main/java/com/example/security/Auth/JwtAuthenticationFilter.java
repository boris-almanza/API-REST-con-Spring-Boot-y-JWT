package com.example.security.Auth;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
     

@Override
protected void doFilterInternal(HttpServletRequest request, 
                                HttpServletResponse response,
                                FilterChain filterChain) throws IOException, ServletException{
    
    final String tokenAuth = getTokenFromRequest(request);
    if(tokenAuth == null){
        filterChain.doFilter(request, response);
        return;
    }

    String tokenUsername = jwtService.extractUsername(tokenAuth);

    if(tokenUsername != null && SecurityContextHolder.getContext().getAuthentication() == null ){
        UserDetails userDetails =userDetailsService.loadUserByUsername(tokenUsername);
        if(jwtService.validateToken(tokenAuth ,userDetails)){
                   
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                                userDetails, null,userDetails.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authentication);
        }   
    }
  
    // aca debemos verificar y validar el token debido a que intenta acceder a un recurso protegido
    // aca deberiamos llamar a un jwtService o JwtProvider que va ser una clse quien se 
    // encague de  crear validar y revocar el token
    filterChain.doFilter(request, response);

 }


 private String getTokenFromRequest(HttpServletRequest request){
    final String authHeader = request.getHeader(HEADER);
    if(StringUtils.hasText(authHeader) && authHeader.startsWith(PREFIX) ){
         return  authHeader.substring(7);      
    }
    return null;   
}



}
