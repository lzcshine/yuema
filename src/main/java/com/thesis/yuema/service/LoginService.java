package com.thesis.yuema.service;

import com.thesis.yuema.entity.UserInfo;

/**
 * @author:lzc
 * 2015-1-9 下午9:18:14
 */

public interface LoginService {

	boolean addNewUser(String username, String password, String nickname);
	
	UserInfo searchUser(String username, String password);
}
