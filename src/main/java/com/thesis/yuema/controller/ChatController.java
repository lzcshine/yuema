package com.thesis.yuema.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.dao.ChatInfoDao;
import com.thesis.yuema.dao.ChatMemberDao;
import com.thesis.yuema.entity.ChatInfo;
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
	
	@Resource(name="chatInfoDaoImpl")
	ChatInfoDao chatInfoDao;
	
	@Resource(name="chatMemberDaoImpl")
	ChatMemberDao chatMemberDao;
	
//	@RequestMapping(value="addChatFlag")
//	public void updateChatInfoIsResponse(HttpServletResponse response, int chatId){
//		if (chatServiceImpl.updateChatInfoIsResponse(chatId)){
//			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
//		}
//		else{
//			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
//		}
//	}
	
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
	public void getChatHistoriesByChatId(HttpServletResponse response, int userId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.getChatHistoriesByUserId(userId)));
	}
	
	@RequestMapping(value="deleteChatHistories")
	public void deleteChatHistories(HttpServletResponse response, int userId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.deleteChatHistories(userId)));
	}
	
	@RequestMapping(value="deleteChatMsg")
	public void deleteChatMsg(HttpServletResponse response, int chatId) throws IOException{
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.deleteChatMsg(chatId)));
	}
	
	@RequestMapping(value="deleteUserChannel")
	public void deleteUserChannel(HttpServletResponse response, int chatId, String username, String nickname) throws IOException{
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatServiceImpl.deleteUserChannel(chatId, username, nickname)));
	}
	
	@RequestMapping(value="test")
	public void test(HttpServletResponse response, int chatId, String username) throws IOException{
		ResponseUtil.sendBack(response, JsonUtil.toJson(chatMemberDao.deleteChatMember(chatId, username)));
	}
}
