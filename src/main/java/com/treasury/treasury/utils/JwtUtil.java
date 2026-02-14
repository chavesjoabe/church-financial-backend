package com.treasury.treasury.utils;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.treasury.treasury.user.constants.UserRoles;
import com.treasury.treasury.user.dto.LoginResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
  private static final String SECRET_KEY = "this-is-my-secret-access-key-tentando-aumentar-o-valor-dessa-chave";
  private static final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours
  private static final String BEARER = "Bearer ";

  public Key getSignKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public LoginResponse generateToken(String document, UserRoles role, String name) {
    String jwt = Jwts.builder()
        .setSubject(document)
        .claim("role", role)
        .claim("name", name)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(getSignKey())
        .compact();

    return new LoginResponse(BEARER + jwt);
  }

  public static UsernamePasswordAuthenticationToken decodeToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    token = token.replace(BEARER, "");

    Jws<Claims> claims = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY.getBytes())
        .build()
        .parseClaimsJws(token);
    String userDocument = claims.getBody().getSubject();
    String userRole = claims.getBody().get("role", String.class);
    String userName = claims.getBody().get("name", String.class);

    request.setAttribute("loggedUserDocument", userDocument);
    request.setAttribute("loggedUserRole", userRole);
    request.setAttribute("loggedUserName", userName);

    var authority = new SimpleGrantedAuthority("ROLE_" + userRole);

    if (userDocument.length() > 0) {
      return new UsernamePasswordAuthenticationToken(
          userDocument,
          null,
          Collections.singletonList(authority));
    }

    return null;
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token, String username) {
    String extractedUsername = extractUsername(token);
    return extractedUsername.equals(username) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
}
