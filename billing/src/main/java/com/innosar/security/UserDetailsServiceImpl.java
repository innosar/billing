/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {

		return null;
	}

}
