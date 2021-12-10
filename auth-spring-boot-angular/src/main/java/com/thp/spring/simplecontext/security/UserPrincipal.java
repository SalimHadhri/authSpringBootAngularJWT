package com.thp.spring.simplecontext.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.thp.spring.simplecontext.entity.User;

public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */

	private static final long serialVersionUID = -7950445567638168232L;

	private User user;

	public UserPrincipal(User user) {
		this.user = user;
	}

	public UserPrincipal() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<>();

		user.getUserRoles().forEach(r -> {
			String nameRole = r.getRoleName();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + nameRole);
			authorities.add(authority);
		});

		return authorities;

	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getMail();
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
		// TODO Auto-generated method stub
		return false;
	}

}