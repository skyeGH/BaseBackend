package com.skye.libra.config;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Setter
public class UserEntity implements UserDetails {

	private String username;

	private String password;

	private Collection<? extends GrantedAuthority> Authorities;

	private boolean AccountNonExpired = true;

	private boolean AccountNonLocked = true;

	private boolean CredentialsNonExpired = true;

	private boolean Enabled = true;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
