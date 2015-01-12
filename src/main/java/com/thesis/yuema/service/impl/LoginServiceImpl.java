package com.thesis.yuema.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thesis.yuema.dao.UserInfoDao;
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
	
	@Override
	public boolean addNewUser(String username, String password, String nickname) {
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(nickname); 
		System.out.println("++++++++");
		user.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
		user.setIsFrost((short)0);
		return userInfoDao.addNewUser(user);
	}

	@Override
	public UserInfo searchUser(String username, String password) {
		return userInfoDao.searchUser(username, password);
	}

	
}
