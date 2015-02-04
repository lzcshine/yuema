package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.ChatMember;

/**
 * @author:lzc
 * 2015-1-12 下午5:20:48
 */

public interface ChatMemberDao {

	boolean addChatMember(ChatMember bean);
	
	boolean addChatMemberBatch(List<ChatMember> list);
	
	boolean deleteChatMember(int chatId, String username);
	
	List<Map<String,Object>> getChatMembersInfo(int chatId);
	
	List<Map<String,Object>> getChatInfosByUserId(int userId);
	
	List<Map<String,Object>> getScrollChatInfosByUserId(int userId, int start);
	
	List<String> getUsernamesByChatId(int chatId);
}
