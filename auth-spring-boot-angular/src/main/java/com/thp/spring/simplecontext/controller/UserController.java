package com.thp.spring.simplecontext.controller;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.thp.spring.simplecontext.dto.UserDto;
import com.thp.spring.simplecontext.entity.Role;
import com.thp.spring.simplecontext.entity.User;
import com.thp.spring.simplecontext.security.UserPrincipal;
import com.thp.spring.simplecontext.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:4200")  
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService userService;

	private ModelMapper modelMapper = new ModelMapper();

	// Get USER credentials
	// return a map with the username, role, token and user_id related to this USER
	// return an exception in case of non existing USER with these credentials
	@PostMapping("/auth")
	public Map<String, String> GenerateToken(@RequestBody UserDto userDto) {

		List<UserDto> allUsers = userService.findAllUser();

		for (int i = 0; i < allUsers.size(); i++) {
			if ((userDto.getMail().equals(allUsers.get(i).getMail()))
					&& (userDto.getPassword().equals(allUsers.get(i).getPassword()))) {
				UserPrincipal principal = new UserPrincipal(modelMapper.map(userDto, User.class));
				String token = JWT.create().withSubject(principal.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()
								+ com.thp.spring.simplecontext.security.JwtProperties.EXPIRATION_TIME)) // EXPIRATION_TIME
																										// : property
																										// created
																										// earlier
						.sign(HMAC512(com.thp.spring.simplecontext.security.JwtProperties.SECRET.getBytes()));

				HashMap<String, String> map = new HashMap<>();

				long id = allUsers.get(i).getUserId();
				String idString = Long.toString(id);

				map.put("mail", principal.getUsername());

				map.put("role", "ROLE_" + allUsers.get(i).getUserRoles());
				map.put("token", token);
				map.put("user_id", idString);
				return map;
			}
		}

		return null;
	}

	@GetMapping(value = "/findRoles/{id}")
	public List<Role> findUserRolesByUserId(@PathVariable Long id) {
		return userService.findUserRolesByUserId(id);

	}

	@PostMapping(value = "/addUser")
	public UserDto addUser(@RequestBody UserDto userDto) {

		return userService.addUser(userDto);

	}

	@GetMapping(value = "/ListAllUser")
	public List<UserDto> findAllUsers() {

		return userService.findAllUser();

	}

	@GetMapping(value = "/findUser/{id}")
	public UserDto findUserById(@PathVariable Long id) {
		return userService.findUserById(id);

	}

	@DeleteMapping(value = "/deleteUser/{id}")
	public UserDto delete(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@PutMapping(value = "/updateUser/{id}")
	public UserDto updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {

		return userService.updateUser(id, userDto);

	}

}
