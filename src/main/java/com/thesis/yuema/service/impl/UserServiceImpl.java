package com.thesis.yuema.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thesis.yuema.dao.FocusRelationDao;
import com.thesis.yuema.dao.UserInfoDao;
import com.thesis.yuema.entity.FocusRelation;
import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.UserService;

/**
 * @author:lzc
 * 2015-1-19 下午12:22:46
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource(name="focusRelationDaoImpl")
	FocusRelationDao focusRelationDao;
	
	@Resource(name="userInfoDaoImpl")
	UserInfoDao userInfoDao;
	
	@Override
	public boolean addUserFocus(int userId, int focusId) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null){
			return false;
		}
		UserInfo focusInfo = userInfoDao.getUserInfoByUserId(focusId);
		if (focusInfo == null){
			return false;
		}
		FocusRelation bean = new FocusRelation();
		bean.setUserInfoByMyId(userInfo);
		bean.setUserInfoByFocusId(focusInfo);
		return focusRelationDao.addFocusRelation(bean);
	}

	@Override
	public List<Map<String, Object>> getFocusListByUserId(int userId) {
		return focusRelationDao.getFocusListByUserId(userId);
	}

	@Override
	public boolean deleteFocus(int userId, String focusUsername) {
		return focusRelationDao.deleteFocusRelation(userId, focusUsername);
	}

	@Override
	public List<Map<String, Object>> getSearchUsers(String text) {
		return userInfoDao.getSearchUsers(text);
	}

}
