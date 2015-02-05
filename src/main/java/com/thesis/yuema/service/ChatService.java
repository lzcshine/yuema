package com.thesis.yuema.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.ChatInfo;

/**
 * @author:lzc
 * 2015-1-12 下午3:37:41
 */

public interface ChatService {

	/**
	 *创建活动（聊天室初始状态）
	 */
	boolean addChatInfo(String username, String title, String time, String currentTime);
	
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
	 * 加载用户正在邀请中的活动
	 */
	List<Map<String,Object>> getScrollInvitingChatInfosByUserId(int userId, int start);
	
	/**
	 * 获取用户有关活动列表
	 */
	List<Map<String,Object>> getChatInfosByUserId(int userId);
	
	/**
	 * 加载用户有关活动列表
	 */
	List<Map<String,Object>> getScrollChatInfosByUserId(int userId, int start);
	
	/**
	 * 活动已有用户回应，修改活动中is_response标志量
	 */
	boolean updateChatInfoIsResponse(ChatInfo chatInfo);
	
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
	void pushEventInviteToUsers(String username,String nickname, String title, String time, String inviteNames, String currentTime);
	
	/**
	 * 根据聊天室id获取成员用户名
	 */
	List<String> getUsernamesByChatId(int chatId);
	
	/**
	 * 群主删除聊天室
	 */
	boolean deleteChatMsg(int chatId);
	
	/**
	 * 用户退出聊天室
	 */
	boolean deleteUserChannel(int chatId, String username, String nickname) throws IOException;
	
	/**
	 * 删除聊天室
	 */
	boolean deleteChatInfo(int chatId);
	
	/**
	 * 删除聊天室成员
	 */
	boolean deleteChatMember(int chatId, String username);
	
	/**
	 * 处理有人回应的消息
	 */
	boolean handleChatCreateSuccess(int chatId, String username, String nickname);
}
