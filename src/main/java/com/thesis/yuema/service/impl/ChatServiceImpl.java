package com.thesis.yuema.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thesis.yuema.chatserver.ChatRoomServer;
import com.thesis.yuema.common.Const;
import com.thesis.yuema.dao.ChatHistoryDao;
import com.thesis.yuema.dao.ChatInfoDao;
import com.thesis.yuema.dao.ChatMemberDao;
import com.thesis.yuema.dao.UserInfoDao;
import com.thesis.yuema.entity.ChatInfo;
import com.thesis.yuema.entity.ChatMember;
import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.ChatService;
import com.thesis.yuema.util.JPushUtil;
import com.thesis.yuema.util.JsonUtil;

/**
 * @author:lzc
 * 2015-1-12 下午3:40:58
 */
@Service
public class ChatServiceImpl implements ChatService {

	@Resource(name="chatInfoDaoImpl")
	ChatInfoDao chatInfoDao;
	
	@Resource(name="chatMemberDaoImpl")
	ChatMemberDao chatMemberDao;
	
	@Resource(name="userInfoDaoImpl")
	UserInfoDao userInfoDao;
	
	@Resource(name="chatHistoryDaoImpl")
	ChatHistoryDao chatHistoryDao;
	
	/* 
	 * 创建活动（聊天室初始状态）
	 */
	@Override
	public boolean addChatInfo(String username, String title, String time, String currentTime) {
		ChatInfo chatInfo = new ChatInfo();
		UserInfo userInfo = userInfoDao.getUserInfoByUsername(username);
		if (userInfo == null){
			return false;
		}
		chatInfo.setUserInfo(userInfo);
		chatInfo.setTitle(title);
		chatInfo.setLimitTime(time);
		chatInfo.setCreateTime(currentTime);
		chatInfo.setIsResponse((short)0);
		
		return chatInfoDao.addChatInfo(chatInfo);
	}

	/* 
	 * 获取用户正在邀请中的活动
	 */
	@Override
	public List<Map<String,Object>> getInvitingChatInfosByUserId(int userId) {
		return chatInfoDao.getChatInfosBySponsorId(userId);
	}

	/* 
	 * 活动已有用户回应，修改活动中is_response标志量
	 */
	@Override
	public boolean updateChatInfoIsResponse(ChatInfo chatInfo) {
		chatInfo.setIsResponse((short)1);
		return chatInfoDao.updateIsResponseByChatId(chatInfo);
	}

	/* 
	 * 获取聊天室成员信息
	 */
	@Override
	public List<Map<String, Object>> getChatMembersInfo(int chatId) {
		return chatMemberDao.getChatMembersInfo(chatId);
	}

	/* 
	 * 添加用户至聊天室
	 */
	@Override
	public boolean addChatMember(int chatId, int userId) {
		ChatMember bean = new ChatMember();
		ChatInfo chatInfo = chatInfoDao.getChatInfoById(chatId);
		if (chatInfo == null){
			return false;
		}
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null){
			return false;
		}
		bean.setChatInfo(chatInfo);
		bean.setUserInfo(userInfo);
		bean.setIsSponsor((short)0);
		return chatMemberDao.addChatMember(bean);
	}

	/* 
	 * 批量添加用户至聊天室
	 */
	@Override
	public boolean addChatMemberBatch(int chatId, List<Integer> userIdList) {
		ChatInfo chatInfo = chatInfoDao.getChatInfoById(chatId);
		if (chatInfo == null){
			return false;
		}
		UserInfo userInfo = null;
		ChatMember bean = null;
		List<ChatMember> list = new ArrayList<ChatMember>();
		for (int userId : userIdList){
			userInfo = userInfoDao.getUserInfoByUserId(userId);
			if (userInfo == null){
				return false;
			}
			bean = new ChatMember();
			bean.setChatInfo(chatInfo);
			bean.setUserInfo(userInfo);
			bean.setIsSponsor((short)0);
			list.add(bean);
		}
		return chatMemberDao.addChatMemberBatch(list);
	}

	@Override
	public List<Map<String, Object>> getChatInfosByUserId(int userId) {
		return chatMemberDao.getChatInfosByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getChatHistoriesByChatId(int chatId) {
		return chatHistoryDao.getChatHistories(chatId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void pushEventInviteToUsers(String username, String nickname, String title, String time,
			String inviteUsername,String createTime) {
		List<String> list = JsonUtil.toObject(inviteUsername, List.class);
		if (list == null){
			return;
		}
		ChatInfo chatInfo = chatInfoDao.getChatInfoByUsernameAndTime(username, createTime);
		if (chatInfo == null){
			return;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("chatId", chatInfo.getChatId());
		map.put("nickname", nickname);
		map.put("title", title);
		map.put(Const.EVENT_LIMIT_TIME, time);
		Long currentTime = System.currentTimeMillis() / 1000;
		map.put(Const.NOTIFICATION_SEND_TIME, String.valueOf(currentTime));
		for (String name : list){
//			if(!JPushUtil.pushEventInviteToUser(name, map)){
//				for (int i=0; i<2; i++){
//					if(JPushUtil.pushEventInviteToUser(name, map)){
//						break;
//					}
//				}
//			}
			JPushUtil.pushEventInviteToUser(name, map);
		}
	}

	@Override
	public List<String> getUsernamesByChatId(int chatId) {
		return chatMemberDao.getUsernamesByChatId(chatId);
	}

	@Override
	public boolean deleteChatMsg(int chatId) {
		Map<String,Object> map = chatInfoDao.getChatInfoByChatId(chatId);
		if (map == null || map.size() == 0){
			return false;
		}
		List<String> users = getUsernamesByChatId(chatId);
		map.put("chatId", chatId);
		if (chatInfoDao.deleteChatInfo(chatId)){
			try {
				ChatRoomServer.getInstance().deleteChatMsg(users, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUserChannel(int chatId, String username, String nickname) throws IOException {
		List<String> users = getUsernamesByChatId(chatId);
		Map<String,Object> map = new HashMap<String, Object>();
		if (users != null){
			try {
				users.remove(username);
			} catch (Exception e) {
				if (chatMemberDao.deleteChatMember(chatId, username)){
					ChatRoomServer.getInstance().deleteUserChannel(users, map);
					return true;
				}
				else{
					return false;
				}
			}
			if (chatMemberDao.deleteChatMember(chatId, username)){
				ChatRoomServer.getInstance().deleteUserChannel(users, map);
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getScrollInvitingChatInfosByUserId(
			int userId, int start) {
		return chatInfoDao.getChatInfosLimitBySponsorId(userId, start);
	}

	@Override
	public List<Map<String, Object>> getScrollChatInfosByUserId(int userId,
			int start) {
		return chatMemberDao.getScrollChatInfosByUserId(userId, start);
	}

	@Override
	public boolean deleteChatInfo(int chatId) {
		return chatInfoDao.deleteChatInfo(chatId);
	}

	@Override
	public boolean deleteChatMember(int chatId, String username) {
		return chatMemberDao.deleteChatMember(chatId, username);
	}

	@Override
	public boolean handleChatCreateSuccess(int chatId, String username, String nickname) {
		List<String> usernames = chatMemberDao.getUsernamesByChatId(chatId);
		UserInfo userInfo = userInfoDao.getUserInfoByNickname(nickname);
		if (userInfo == null){
			return false;
		}
		ChatInfo chatInfo = chatInfoDao.getChatInfoById(chatId);
		if (chatInfo == null){
			return false;
		}
		if (usernames != null && usernames.size() > 0){
			userInfo = userInfoDao.getUserInfoByNickname(nickname);
			chatInfo = chatInfoDao.getChatInfoById(chatId);
			if(!addChatMembers(userInfo,chatInfo)){
				return false;
			}
			pushInviteSuccessToUser(username,chatId,chatInfo.getTitle());
			return true;
		}
		UserInfo sponsor = userInfoDao.getUserInfoByUsername(username);
		if(!addChatMembers(sponsor,userInfo,chatInfo)){
			return false;
		}
		updateChatInfoIsResponse(chatInfo);
		pushInviteSuccessToUser(username,chatId,chatInfo.getTitle());
		return true;
	}
	
	private boolean addChatMembers(UserInfo userInfo, ChatInfo chatInfo){
		ChatMember bean = new ChatMember();
		bean.setUserInfo(userInfo);
		bean.setChatInfo(chatInfo);
		bean.setIsSponsor((short)0);
		return chatMemberDao.addChatMember(bean);
	}
	
	private boolean addChatMembers(UserInfo sponsor, UserInfo inviter, ChatInfo chatInfo){
		ChatMember bean1 = new ChatMember();
		bean1.setUserInfo(sponsor);
		bean1.setChatInfo(chatInfo);
		bean1.setIsSponsor((short)1);
		ChatMember bean2 = new ChatMember();
		bean2.setUserInfo(inviter);
		bean2.setChatInfo(chatInfo);
		bean2.setIsSponsor((short)0);
		List<ChatMember> list = new ArrayList<ChatMember>();
		list.add(bean1);
		list.add(bean2);
		return chatMemberDao.addChatMemberBatch(list);
	}
	
	private void pushInviteSuccessToUser(String username, int chatId, String title){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("chatId", chatId);
		map.put("title",title);
		JPushUtil.pushInviteSuccessToUser(username, map);
	}

	@Override
	public boolean deleteChatHistories(int userId) {
		return chatHistoryDao.deleteChatHistoriesByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getChatHistoriesByUserId(int userId) {
		return chatHistoryDao.getChatHistoriesByUserId(userId);
	}

}
