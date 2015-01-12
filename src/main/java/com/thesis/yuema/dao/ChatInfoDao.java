package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.ChatInfo;

/**
 * @author:lzc
 * 2015-1-10 下午3:13:45
 */

public interface ChatInfoDao {

	boolean addChatInfo(ChatInfo chat);
	
	ChatInfo getChatInfoByChatId(int chatId);
	
	List<Map<String,Object>> getChatInfosBySponsorId(int sponsorId);
	
	boolean updateIsResponseByChatId(ChatInfo chat);
	
}
