package com.thesis.yuema.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	public boolean addChatInfo(String nickname, String title, String time) {
		ChatInfo chatInfo = new ChatInfo();
		UserInfo userInfo = userInfoDao.getUserInfoByNickname(nickname);
		if (userInfo == null){
			return false;
		}
		chatInfo.setUserInfo(userInfo);
		chatInfo.setTitle(title);
		chatInfo.setLimitTime(time);
		chatInfo.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
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
	public boolean updateChatInfoIsResponse(int chatId) {
		ChatInfo chatInfo = chatInfoDao.getChatInfoByChatId(chatId);
		if (chatInfo == null){
			return false;
		}
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
		ChatInfo chatInfo = chatInfoDao.getChatInfoByChatId(chatId);
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
		ChatInfo chatInfo = chatInfoDao.getChatInfoByChatId(chatId);
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
	public void pushEventInviteToUsers(String nickname, String title, String time,
			String inviteNames) {
		List<String> list = JsonUtil.toObject(inviteNames, List.class);
		if (list == null){
			return;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		map.put("title", title);
		map.put(Const.EVENT_LIMIT_TIME, time);
		map.put(Const.NOTIFICATION_SEND_TIME, System.currentTimeMillis() / 1000);
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

}
