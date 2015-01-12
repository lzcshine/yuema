package com.thesis.yuema.service;

import java.util.List;
import java.util.Map;

/**
 * @author:lzc
 * 2015-1-12 下午9:25:39
 */

public interface FeedbackService {

	/**
	 * 添加用户反馈信息
	 */
	boolean addFeedback(int userId, String feedback);
	
	/**
	 * 获取系统回复反馈内容
	 */
	List<Map<String,Object>> getFeedbackReplyByUserId(int userId, int count);
}
