package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.UserInfo;

/**
 * @author:lzc
 * 2015-1-9 下午9:17:42
 */

public interface UserInfoDao {

	boolean addNewUser(UserInfo user);
	
	UserInfo getUserInfoByUserId(int userId);
	
	UserInfo searchUser(String username, String password);
	
	UserInfo getUserInfoByNickname(String nickname);
	
	UserInfo getUserInfoByUsername(String username);
	
	Map<String,Object> getUserInfoMapByUsername(String username);
	
	List<Map<String,Object>> getSearchUsers(String text);
}
