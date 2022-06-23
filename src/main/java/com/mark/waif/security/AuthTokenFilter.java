package com.mark.waif.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mark.waif.user.AppUserRepo;
import com.mark.waif.user.AppUserService;
import com.mark.waif.user.UserConfig;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AppUserService appUserService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	    try {
	        String jwt = parseJwt(request);
	        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	          String username = jwtUtils.getUserNameFromJwtToken(jwt);		//Roles go here (if cookie auth fails....)
	          UserConfig.setUsername(username);
	          System.out.println(username);
	        }
	        filterChain.doFilter(request, response);
	      } catch (Exception e) {
	        logger.error(e);
	      } finally {
	    	  UserConfig.clear();
	      }
	}
	
	private String parseJwt(HttpServletRequest request) {
	    String jwt = jwtUtils.getJwtFromCookies(request);
	    return jwt;
	  }
}
