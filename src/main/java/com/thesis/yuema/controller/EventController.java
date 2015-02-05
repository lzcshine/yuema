package com.thesis.yuema.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.service.ChatService;
import com.thesis.yuema.util.JsonUtil;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc
 * 2015-1-12 下午2:56:59
 */
@Controller
public class EventController {
	
	@Resource(name="chatServiceImpl")
	ChatService chatServiceImpl;

	@RequestMapping(value="/addEvent")
	public void addEvent(HttpServletResponse response, String title, String username, String time, String nickname, String inviteUsername){
		String currentTime = String.valueOf(System.currentTimeMillis() / 1000);
		if (chatServiceImpl.addChatInfo(username, title, time, currentTime)){
			new Thread(new PushMessageThread(nickname,title,time,inviteUsername,currentTime,username)).start();
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		}
		else{
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value="/getEventsList")
	public void getEventsList(HttpServletResponse response, int userId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.getChatInfosByUserId(userId)));
	}
	
	@RequestMapping(value="/getScrollEventsList")
	public void getScrollEventsList(HttpServletResponse response, int userId, int start){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.getScrollChatInfosByUserId(userId, start)));
	}
	
	@RequestMapping(value="/getInvitingEventsList")
	public void getInvitingEventsList(HttpServletResponse response, int userId){
		handleInvitingEventsList(response,chatServiceImpl.getInvitingChatInfosByUserId(userId));
	}
	
	@RequestMapping(value="/getScrollInvitingEventsList")
	public void getScrollInvitingEventsList(HttpServletResponse response, int userId, int start){
		handleInvitingEventsList(response,chatServiceImpl.getScrollInvitingChatInfosByUserId(userId, start));
	}
	
	@RequestMapping(value="/createChatSuccess")
	public void createChatRoomSuccess(HttpServletResponse response, int chatId, String username, String nickname){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.handleChatCreateSuccess(chatId, username, nickname)));
	}
	
	private void handleInvitingEventsList(HttpServletResponse response, List<Map<String,Object>> list){
		long current = System.currentTimeMillis() / 1000;
		long create = 0;
		long limit = 0;
		for (int i=0; i<list.size(); i++){
			create = Long.parseLong(list.get(i).get("createTime").toString());
			limit = Long.parseLong(list.get(i).get("limitTime").toString());
			if (current - create > limit){
				list.remove(i);
//				chatServiceImpl.deleteChatInfo((Integer)list.get(i).get("chatId"));
			}
		}
		ResponseUtil.sendBack(response, JsonUtil.toJson(list));
	}
	
	private class PushMessageThread implements Runnable{
		
		private String nickname;
		private String title;
		private String time;
		private String inviteUsername;
		private String currentTime;
		private String username;
		
		public PushMessageThread(String nickname, String title, String time, String inviteUsername, String currentTime, String username){
			this.nickname = nickname;
			this.title = title;
			this.time = time;
			this.inviteUsername = inviteUsername;
			this.currentTime = currentTime;
			this.username = username;
		}

		@Override
		public void run() {
			chatServiceImpl.pushEventInviteToUsers(username,nickname, title, time, inviteUsername, currentTime);
		}
		
	}
}
