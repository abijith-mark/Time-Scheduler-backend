package com.mark.waif.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
	
	private static final String USER_NOT_FOUND_MSG = "User with name \"%s\" not found";
	
	@Autowired
	private AppUserRepo appUserRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
	}
	
	public String saveUser(AppUser user) {
		boolean isExists = appUserRepo.findByUsername(user.getUsername()).isPresent();;
		if(isExists) {
			return new IllegalStateException("Username already taken!!").toString();
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		appUserRepo.save(user);
		return "Registered";
	}

}
