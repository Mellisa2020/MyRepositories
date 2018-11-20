package com.dida.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@Component
@TableName(value="sys_user")
public class SysUser {
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String locked;
	private Integer del;
	
	private String photo;
	private String lasttime;
	private String usercode;
	
	@TableField(exist=false)
	List<Role> roles = new ArrayList<Role>();
	
	public SysUser() {
		super();
	}
	
	public String getUsercode() {
		return usercode;
	}


	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	

	public SysUser(String username, String password, String salt, String locked, Integer del) {
		super();
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.locked = locked;
		this.del = del;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", locked=" + locked + ", del=" + del + ", photo=" + photo + ", lasttime=" + lasttime + "]";
	}

	
	
}
