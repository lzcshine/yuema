package com.thesis.yuema.service;

import java.util.List;
import java.util.Map;

/**
 * @author:lzc
 * 2015-1-19 下午12:20:22
 */

public interface UserService {

	/**
	 * 关注用户
	 */
	boolean addUserFocus(int userId, int focusId);
	
	/**
	 * 获取关注用户列表
	 */
	List<Map<String,Object>> getFocusListByUserId(int userId);
	
	/**
	 * 取消关注
	 */
	boolean deleteFocus(int userId, String focusUsername);
	
	/**
	 * 查找用户
	 */
	List<Map<String,Object>> getSearchUsers(String text);
}
