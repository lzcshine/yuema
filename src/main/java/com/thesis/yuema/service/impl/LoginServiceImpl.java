package com.thesis.yuema.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thesis.yuema.dao.ImeiDao;
import com.thesis.yuema.dao.UserInfoDao;
import com.thesis.yuema.entity.ImeiInfo;
import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.LoginService;




/**
 * @author:lzc
 * 2015-1-9 下午9:14:38
 */
@Service
public class LoginServiceImpl implements LoginService{

	@Resource(name="userInfoDaoImpl")
	UserInfoDao userInfoDao;
	
	@Resource(name="imeiDaoImpl")
	ImeiDao imeiDao;
	
	/* 
	 * 添加新用户
	 */
	@Override
	public boolean addNewUser(String username, String password, String nickname) {
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(nickname); 
		user.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
		user.setIsFrost((short)0);
		return userInfoDao.addNewUser(user);
	}

	/* 
	 * 查找用户是否存在
	 */
	@Override
	public UserInfo searchUser(String username, String password) {
		return userInfoDao.searchUser(username, password);
	}

	/* 
	 * 添加用户手机imei值
	 */
	@Override
	public boolean addImeiInfo(int userId, String imei) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null){
			return false;
		}
		ImeiInfo imeiInfo = new ImeiInfo();
		imeiInfo.setUserInfo(userInfo);
		imeiInfo.setImei(imei);
		return imeiDao.addImeiInfo(imeiInfo);
	}

	/* 
	 * 查找用户名是否存在
	 */
	@Override
	public UserInfo searchUserByUsername(String username) {
		return userInfoDao.getUserInfoByUsername(username);
	}

	/* 
	 * 查找用户昵称是否存在
	 */
	@Override
	public UserInfo searchUserByNickname(String nickname) {
		return userInfoDao.getUserInfoByNickname(nickname);
	}
}
