package com.cafe.erp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cafe.erp.member.MemberDTO;

public class UserDTO implements UserDetails{

	private MemberDTO member;
	
	public UserDTO(MemberDTO memberDTO) {
		this.member = memberDTO;
	} 
	
	public MemberDTO getMember() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return java.util.Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return member.getMemPassword();
	}

	@Override
	public String getUsername() {
		return String.valueOf(member.getMemberId());
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
