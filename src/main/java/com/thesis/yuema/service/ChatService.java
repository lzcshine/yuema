package com.thesis.yuema.service;

import java.util.List;
import java.util.Map;

/**
 * @author:lzc
 * 2015-1-12 下午3:37:41
 */

public interface ChatService {

	/**
	 *创建活动（聊天室初始状态）
	 */
	boolean addChatInfo(String nickname, String title, String time);
	
	/**
	 * 添加用户至聊天室
	 */
	boolean addChatMember(int chatId, int userId);
	
	/**
	 * 批量添加用户至聊天室
	 */
	boolean addChatMemberBatch(int chatId, List<Integer> userIdList);
	
	/**
	 * 获取用户正在邀请中的活动
	 */
	List<Map<String,Object>> getInvitingChatInfosByUserId(int userId);
	
	/**
	 * 获取用户有关活动列表
	 */
	List<Map<String,Object>> getChatInfosByUserId(int userId);
	
	/**
	 * 活动已有用户回应，修改活动中is_response标志量
	 */
	boolean updateChatInfoIsResponse(int chatId);
	
	/**
	 * 获取聊天室成员信息
	 */
	List<Map<String,Object>> getChatMembersInfo(int chatId);
	
	/**
	 * 获取聊天记录
	 */
	List<Map<String,Object>> getChatHistoriesByChatId(int chatId);
	
	/**
	 * 发送活动邀请给用户
	 */
	void pushEventInviteToUsers(String nickname, String title, String time, String inviteNames);
}
