package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.ChatHistory;

/**
 * @author:lzc
 * 2015-1-12 下午8:09:41
 */

public interface ChatHistoryDao {

	boolean addChatHistory(ChatHistory bean);
	
	List<Map<String,Object>> getChatHistories(int chatId);
	
	List<Map<String,Object>> getChatHistoriesByUserId(int userId);
	
	boolean deleteChatHistoriesByUserId(int userId);
}
