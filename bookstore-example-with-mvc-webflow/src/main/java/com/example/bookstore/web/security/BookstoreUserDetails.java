package com.example.bookstore.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.bookstore.domain.Account;

/**
 * Customer {@link UserDetails} holding our authentication object {@link Account} and keeping track of the list of
 * {@link GrantedAuthority} for the current authenticated user
 * 
 * 
 * 
 * 
 */
public class BookstoreUserDetails implements UserDetails {

	private Account account;
	private List<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public BookstoreUserDetails(Account account, List<? extends GrantedAuthority> authorities) {
		this.account = account;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	public Account getAccount() {
		return account;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
