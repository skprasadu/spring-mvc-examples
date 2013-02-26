package com.example.bookstore.web.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.Permission;
import com.example.bookstore.domain.Role;
import com.example.bookstore.service.AccountService;

/**
 * Custom {@link UserDetailsService} which retrieves the data for the user authenticatiing from the database. When the
 * user exists returns a {@link BookstoreUserDetails} containing all inforamtion for further authentication
 * 
 * 
 * 
 * 
 */
@Component
public class BookstoreUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Username was empty");
		}

		Account account = accountService.getAccount(username);

		if (account == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		for (Role role : account.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
			for (Permission permission : role.getPermissions()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermission()));
			}
		}
		return new BookstoreUserDetails(accountService.getAccount(username), grantedAuthorities);
	}
}
