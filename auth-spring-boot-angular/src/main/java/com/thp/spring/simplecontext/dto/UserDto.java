package com.thp.spring.simplecontext.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.thp.spring.simplecontext.entity.Role;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String firstName;

	private String lastName;

	private String mail;

	private String password;

	private List<Role> userRoles;

	public UserDto() {
	}

	public List<String> getUserNameRoles() {

		List<String> listNames = new ArrayList<>();
		for (Role nameRole : this.userRoles) {

			listNames.addAll(nameRole.getRoleList());
		}

		return listNames;

	}

	public UserDto(Long userId, String firstName, String lastName, String mail, String password, List<Role> userRoles) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.userRoles = userRoles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", mail=" + mail
				+ ", password=" + password + ", userRoles=" + userRoles + "]";
	}

}
