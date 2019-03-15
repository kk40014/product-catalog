package com.bean;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Entity
@Table(name = "userinfo")
public class UserInformation {
	
	@Id
	@Column(name="userid")
	private int userid;
	private String username;
	private String usercompany;
	private String email;
	private String cartinfo;
	private String pwd;
	private String status;
	@Transient
	private String passwordConfirm;
	@Transient
	private List<UserMenu> menu;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsercompany() {
		return usercompany;
	}
	public void setUsercompany(String usercompany) {
		this.usercompany = usercompany;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCartinfo() {
		return cartinfo;
	}
	public void setCartinfo(String cartinfo) {
		this.cartinfo = cartinfo;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public List<UserMenu> getMenu() {
		return menu;
	}
	public void setMenu(List<UserMenu> menu) {
		this.menu = menu;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
