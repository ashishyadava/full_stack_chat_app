package com.ashish.UserService.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JWTService {

    @Value("${ashish.chatapp.jwt.secretKey}")
    private String secretKey;

    @Value("${ashish.chatapp.jwt.cookie.name}")
    private String tokenCookieName;

    @Value("${ashish.chatapp.jwt.expiration}")
    private int tokenExpiration;

    @Value("${ashish.chatapp.jwt.maximumAge}")
    private int tokenMaxAge;

    public String createTokenUsingUsername(String username) {
        
        long timeNow = (new Date()).getTime();

        byte[] keyBytes = secretKey.getBytes();
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(new Date(timeNow + tokenExpiration));
        jwtBuilder.setSubject(username);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, encodedKey);

        String token = jwtBuilder.compact();
        
        return token;
    }
    
    public String extractTokenInCookies(HttpServletRequest request) {

        Cookie tokenCookie = WebUtils.getCookie((HttpServletRequest) request, tokenCookieName);

        return (tokenCookie!=null) ?  tokenCookie.getValue() : null;

    }

    public ResponseCookie createCookieWithTokenFromUsername(UserDetailsImpl principal) {

        String token = createTokenUsingUsername(principal.getUsername());

        ResponseCookieBuilder tokenCookieBuilder = ResponseCookie.from(tokenCookieName, token);
        tokenCookieBuilder.maxAge(tokenMaxAge);
        tokenCookieBuilder.path("/security");
        tokenCookieBuilder.httpOnly(true);

        ResponseCookie tokenCookie = tokenCookieBuilder.build();

        return tokenCookie;
    }

    public ResponseCookie createCookieWithNullToken() {

            ResponseCookieBuilder tokenCookieBuilder = ResponseCookie.from(tokenCookieName, null);
            tokenCookieBuilder.path("/security");
            
            ResponseCookie tokenCookie = tokenCookieBuilder.build();

            return tokenCookie;
    }

    public String getUserNameFromJwtToken(String token) {

        try {
            byte[] keyBytes = secretKey.getBytes();
            String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(encodedKey);

            return jwtParser.parseClaimsJws(token).getBody().getSubject();

        } catch (Exception e) {
            return null;
        }

    }

    public boolean validateToken(String token) {

        try {

            JwtParser jwtParser = Jwts.parser();

            jwtParser.setSigningKey(secretKey);
            jwtParser.parseClaimsJws(token);
            
            return true;

        } catch (Exception e) {

        }

        return false;
    }
  
  
}