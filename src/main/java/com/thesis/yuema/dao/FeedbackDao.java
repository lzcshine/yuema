package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.FeedbackInfo;

/**
 * @author:lzc
 * 2015-1-12 下午9:12:12
 */

public interface FeedbackDao {

	boolean addFeedbackInfo(FeedbackInfo bean);
	
	List<Map<String,Object>> getFeedbackReply(int userId, int count);
}
