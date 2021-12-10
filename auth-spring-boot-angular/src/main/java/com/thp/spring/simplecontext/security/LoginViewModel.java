package com.thp.spring.simplecontext.security;


//first request we will send this model in the post method as json body
public class LoginViewModel {

	private String mail;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	

	public LoginViewModel(String mail, String password) {
		this.mail = mail;
		this.password = password;
	}

	public LoginViewModel() {
	}

}