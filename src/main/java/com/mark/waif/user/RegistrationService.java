package com.mark.waif.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
	
	@Autowired
	public AppUserService appUserService;
	
	
	public String register(AppUser request) {
		return appUserService.saveUser(request);
	}

}
