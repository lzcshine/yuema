package com.thesis.yuema.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thesis.yuema.dao.FeedbackDao;
import com.thesis.yuema.dao.UserInfoDao;
import com.thesis.yuema.entity.FeedbackInfo;
import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.FeedbackService;

/**
 * @author:lzc
 * 2015-1-12 下午9:28:33
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Resource(name="userInfoDaoImpl")
	UserInfoDao userInfoDao;
	
	@Resource(name="feedbackDaoImpl")
	FeedbackDao feedbackDao;
	
	/* 
	 * 添加用户反馈信息
	 */
	@Override
	public boolean addFeedback(int userId, String feedback) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null){
			return false;
		}
		FeedbackInfo bean = new FeedbackInfo();
		bean.setFeedback(feedback);
		bean.setUserInfo(userInfo);
		bean.setFeedbackTime(String.valueOf(System.currentTimeMillis() / 1000));
		bean.setIsReply((short)0);
		return feedbackDao.addFeedbackInfo(bean);
	}

	/* 
	 * 获取系统回复反馈内容
	 */
	@Override
	public List<Map<String, Object>> getFeedbackReplyByUserId(int userId,
			int count) {
		return feedbackDao.getFeedbackReply(userId, count);
	}

}
