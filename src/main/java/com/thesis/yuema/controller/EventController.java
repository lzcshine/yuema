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
	public void addEvent(HttpServletResponse response, int userId, String title, String time, String nickname, String inviteNickname){
		//TODO send message to inviteNickname
		if (chatServiceImpl.addChatInfo(userId, title, time)){
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
	
	@RequestMapping(value="/getInvitingEventsList")
	public void getInvitingEventsList(HttpServletResponse response, int userId){
		List<Map<String,Object>> list = chatServiceImpl.getInvitingChatInfosByUserId(userId);
		ResponseUtil.sendBack(response, JsonUtil.toJson(list));
	}
}
