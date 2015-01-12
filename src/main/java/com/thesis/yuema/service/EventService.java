package com.thesis.yuema.service;

import java.util.List;

import com.thesis.yuema.entity.ChatInfo;


/**
 * @author:lzc
 * 2015-1-10 下午4:10:38
 */

public interface EventService {

	boolean addEvent(int userId, String title, String limitTime);
	
	List<ChatInfo> getChatInfosByUserId(int userId);
	
}
