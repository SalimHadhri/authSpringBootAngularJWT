package com.thp.spring.simplecontext.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "role")

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roleId;

	@JsonProperty("roleName")
	@Column(name = "roleName")
	private String roleName = "";

	@ManyToMany(mappedBy = "userRoles",fetch = FetchType.EAGER)
	@JsonIgnore
	private List<User> users;

	public Role() {
	}

	public List<String> getRoleList() {
		if (this.roleName.length() > 0) {
			return Arrays.asList(this.roleName.split(","));
		}
		return new ArrayList<>();
	}

	public Role(Long roleId, String roleName, List<User> users) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
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
		return roleName;
	}

}
