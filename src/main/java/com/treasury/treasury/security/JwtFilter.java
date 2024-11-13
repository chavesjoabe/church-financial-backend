package com.treasury.treasury.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.treasury.treasury.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

  public JwtFilter() { }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

      if(request.getHeader("Authorization") != null) {
         UsernamePasswordAuthenticationToken auth = JwtUtil.decodeToken(request);
         SecurityContextHolder.getContext().setAuthentication(auth);
      }

    // jogando o request para frente
    filterChain.doFilter(request, response);
  }
}
