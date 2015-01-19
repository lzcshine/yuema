package com.thesis.yuema.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.service.ChatService;
import com.thesis.yuema.util.JsonUtil;
import com.thesis.yuema.util.ParamsUtil;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc
 * 2015-1-12 下午5:09:15
 */
@Controller
public class ChatController {

	@Resource(name="chatServiceImpl")
	ChatService chatServiceImpl;
	
	@RequestMapping(value="addChatFlag")
	public void updateChatInfoIsResponse(HttpServletResponse response, int chatId){
		if (chatServiceImpl.updateChatInfoIsResponse(chatId)){
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		}
		else{
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value="getChatMembers")
	public void getChatMembersByChatId(HttpServletResponse response, int chatId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.getChatMembersInfo(chatId)));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addChatMembers")
	public void addChatMembers(HttpServletResponse response, int chatId, String userIdList){
		if (!ParamsUtil.isValidParam(userIdList)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		List<Integer> list = JsonUtil.toObject(userIdList, List.class);
		if (list == null){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (chatServiceImpl.addChatMemberBatch(chatId, list)){
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		}
		else{
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value="addChatMember")
	public void addChatMember(HttpServletResponse response, int chatId, int userId){
		if (chatServiceImpl.addChatMember(chatId, userId)){
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		}
		else{
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value="getChatHistories")
	public void getChatHistoriesByChatId(HttpServletResponse response, int chatId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.getChatHistoriesByChatId(chatId)));
	}
}
