package com.thp.spring.simplecontext.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.thp.spring.simplecontext.entity.User;

public class RoleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long roleId;

	private String roleName = "";

	private List<User> users;

	public RoleDto() {
	}

	public RoleDto(Long roleId, String roleName, List<User> users) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
	}

	public List<String> getRoleList() {
		if (this.roleName.length() > 0) {
			return Arrays.asList(this.roleName.split(","));
		}
		return new ArrayList<>();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RoleDto [roleId=" + roleId + ", roleName=" + roleName + ", users=" + users + "]";
	}

}
