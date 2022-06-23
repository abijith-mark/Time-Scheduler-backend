package com.mark.waif.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mark.waif.payload.LoginRequest;
import com.mark.waif.security.JwtResponse;
import com.mark.waif.security.JwtUtils;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(path="v1/auth")
public class RegistrationController {

	@Autowired
	public AuthenticationManager authenticationManager;
	@Autowired
	public RegistrationService registrationService;
	@Autowired
	public JwtUtils jwtUtils;
	@Autowired
	private AppUserRepo appUserRepo;
	
	@GetMapping(path="/test")
	public List<AppUser> testFunc() {
		return appUserRepo.findAll();
	}
	
	@PostMapping(path="/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    AppUser userDetails = (AppUser) authentication.getPrincipal();
	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
    	        .body(new JwtResponse (jwtUtils.generateTokenFromUsername(loginRequest.getUsername()),loginRequest.getUsername()));
	  }
	
	@PostMapping(path="/signup")
	public String register(@RequestBody AppUser request) {
		return registrationService.register(request);
	}
	
	@PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {
	    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body("You've been signed out!");
	  }
}
