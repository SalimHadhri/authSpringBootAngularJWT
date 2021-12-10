package com.thp.spring.simplecontext.service;

import java.util.List;
import com.thp.spring.simplecontext.dto.UserDto;
import com.thp.spring.simplecontext.entity.Role;

public interface UserService {

	public List<UserDto> findAllUser();

	public UserDto findUserByMail(String mail);

	public List<Role> findUserRolesByUserId(Long userId);

	public UserDto findUserById(Long id);

}
