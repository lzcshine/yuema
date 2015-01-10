package com.thesis.yuema.dao;

import com.thesis.yuema.entity.UserInfo;

/**
 * @author:lzc
 * 2015-1-9 下午9:17:42
 */

public interface UserInfoDao {

	boolean addNewUser(UserInfo user);
	
	UserInfo searchUser(String username, String password);
}
