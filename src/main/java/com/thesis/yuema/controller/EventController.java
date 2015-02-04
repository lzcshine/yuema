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
	public void addEvent(HttpServletResponse response, String title, String time, String nickname, String inviteNickname){
		if (chatServiceImpl.addChatInfo(nickname, title, time)){
			new Thread(new PushMessageThread(nickname,title,time,inviteNickname)).start();
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
	
	private void handleInvitingEventsList(HttpServletResponse response, List<Map<String,Object>> list){
		long current = System.currentTimeMillis() / 1000;
		long create = 0;
		long limit = 0;
		for (int i=0; i<list.size(); i++){
			create = (Long)list.get(i).get("createTime");
			limit = (Long)list.get(i).get("limitTime");
			if (current - create > limit){
				list.remove(i);
			}
		}
		ResponseUtil.sendBack(response, JsonUtil.toJson(list));
	}
	
	private class PushMessageThread implements Runnable{
		
		private String nickname;
		private String title;
		private String time;
		private String inviteNickname;
		
		public PushMessageThread(String nickname, String title, String time, String inviteNickname){
			this.nickname = nickname;
			this.title = title;
			this.time = time;
			this.inviteNickname = inviteNickname;
		}

		@Override
		public void run() {
			chatServiceImpl.pushEventInviteToUsers(nickname, title, time, inviteNickname);
		}
		
	}
}
