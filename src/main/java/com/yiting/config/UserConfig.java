package com.yiting.config;

import com.yiting.annotation.ConfigFile;
import com.yiting.annotation.ConfigFileItem;

/**
 * Created by admin on 2017/4/27.
 */
@ConfigFile(fileName = "user-config.properties",env = "liantiao",version = "1-1",app = "user")
public class UserConfig {

	private String userName;
	private String password;

	@ConfigFileItem(name = "username",associateField = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ConfigFileItem(name = "pwd",associateField = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
