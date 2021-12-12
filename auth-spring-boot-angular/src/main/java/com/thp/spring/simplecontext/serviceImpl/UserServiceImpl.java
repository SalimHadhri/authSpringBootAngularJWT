package com.thp.spring.simplecontext.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thp.spring.simplecontext.dto.UserDto;
import com.thp.spring.simplecontext.entity.Role;
import com.thp.spring.simplecontext.entity.User;
import com.thp.spring.simplecontext.repository.UserRepository;
import com.thp.spring.simplecontext.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDto> findAllUser() {

		List<User> usersToDisplay = userRepository.findAll();
		List<UserDto> usersDtoToDisplay = new ArrayList<UserDto>();

		for (int i = 0; i < usersToDisplay.size(); i++) {
			usersDtoToDisplay.add(modelMapper.map(usersToDisplay.get(i), UserDto.class));
		}

		return usersDtoToDisplay;
	}

	@Override
	public UserDto findUserByMail(String mail) {

		List<User> usersToDisplay = userRepository.findAll();
		List<UserDto> usersDtoToDisplay = new ArrayList<UserDto>();

		for (int i = 0; i < usersToDisplay.size(); i++) {
			usersDtoToDisplay.add(modelMapper.map(usersToDisplay.get(i), UserDto.class));
		}

		for (int i = 0; i < usersDtoToDisplay.size(); i++) {
			if (usersDtoToDisplay.get(i).getMail().equals(mail)) {
				return usersDtoToDisplay.get(i);
			}
		}
		return null;
	}

	@Override
	public UserDto findUserById(Long id) {

		return modelMapper.map(userRepository.findById(id).get(), UserDto.class);

	}

	@Override
	public List<Role> findUserRolesByUserId(Long userId) {

		UserDto userDto = findUserById(userId);
		return userDto.getUserRoles();

	}

	@Override
	public UserDto addUser(UserDto userDto) {

		User userToAdd = modelMapper.map(userDto, User.class);
		User userAdded = userRepository.save(userToAdd);

		return modelMapper.map(userAdded, UserDto.class);

	}

	@Override
	public UserDto deleteUser(Long id) {

		UserDto userDtoDeleted = findUserById(id);
		userRepository.deleteById(id);

		return userDtoDeleted;
	}


	@Override
	public UserDto updateUser(Long id, UserDto userDto) {

		userDto.setUserId(id);;

		return addUser(userDto);
	}
	
	

}
