package com.mark.waif.user;

import org.springframework.stereotype.Component;

@Component
public class UserConfig{
	
	private static final ThreadLocal<String> USER = new ThreadLocal<>();
     
    public static void setUsername(String username) {
       USER.set(username);
    }
    
    public static String getUsername() {
    	return USER.get();
    }
    
    public static void clear() {
    	USER.remove();
    }
    
}