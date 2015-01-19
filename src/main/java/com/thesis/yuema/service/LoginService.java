package com.thesis.yuema.service;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.UserInfo;

/**
 * @author:lzc
 * 2015-1-9 下午9:18:14
 */

public interface LoginService {

	/**
	 * 添加新用户
	 */
	boolean addNewUser(String username, String password, String nickname);
	
	/**
	 * 添加用户手机imei值
	 */
	boolean addImeiInfo(int userId, String imei);
	
	/**
	 * 查找用户是否存在
	 */
	UserInfo searchUser(String username, String password);
	
	/**
	 * 查找用户名是否存在
	 */
	UserInfo searchUserByUsername(String username);
	
	/**
	 * 查找用户昵称是否存在
	 */
	UserInfo searchUserByNickname(String nickname);
	
	/**
	 * 获取用户信息
	 */
	Map<String,Object> getUserInfoByUsername(String username);
}
