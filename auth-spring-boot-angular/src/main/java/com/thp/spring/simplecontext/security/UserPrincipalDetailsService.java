package com.thp.spring.simplecontext.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thp.spring.simplecontext.dto.UserDto;
import com.thp.spring.simplecontext.entity.User;
import com.thp.spring.simplecontext.service.UserService;

//UserPrincipalDetailsService extract user from an xl file /database/in memory
//convert user to userPrincipal that implements the userDetails class
//@service ===>>> enable this component to be eligible for auto wiring
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	@Autowired
	public UserService userService;

	private ModelMapper modelMapper = new ModelMapper();

	public UserPrincipalDetailsService() {
	}

	public UserPrincipalDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		UserDto userDto = userService.findUserByMail(mail);
		UserPrincipal userPrincipal = new UserPrincipal(modelMapper.map(userDto, User.class));

		return userPrincipal;
	}
}