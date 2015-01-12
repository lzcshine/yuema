package com.thesis.yuema.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.LoginService;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc
 * 2015-1-9 下午9:04:28
 */
@Controller
public class LoginController {

	@Resource(name="loginServiceImpl")
	LoginService loginServiceImpl;
	
	@RequestMapping(value="/login")
	public void userToLogin(HttpServletResponse response, String username, String password){
		UserInfo user = loginServiceImpl.searchUser(username, password);
		if (user != null){
			ResponseUtil.sendBack(response, user.getNickname());
		}
		else{
			ResponseUtil.sendBack(response, "user not exist!");
		}
	}
	
	@RequestMapping(value="/register")
	public void registerUser(HttpServletResponse response, String username, String password, String nickname){
		if (loginServiceImpl.addNewUser(username, password, nickname)){
			ResponseUtil.sendBack(response, "true");
		}
		else{
			ResponseUtil.sendBack(response, "false");
		}
	}
	
	@RequestMapping(value="/usernameUsable")
	public void usernameUsable(HttpServletResponse response, String username){
		if (loginServiceImpl.searchUserByUsername(username) == null){
			ResponseUtil.sendBack(response, "true");
		}
		else{
			ResponseUtil.sendBack(response, "false");
		}
	}
	
	@RequestMapping(value="/nicknameUsable")
	public void nicknameUsable(HttpServletResponse response, String nickname){
		if (loginServiceImpl.searchUserByNickname(nickname) == null){
			ResponseUtil.sendBack(response, "true");
		}
		else{
			ResponseUtil.sendBack(response, "false");
		}
	}
	
	@RequestMapping(value="/addImei")
	public void addImei(HttpServletResponse response, int userId, String imei){
		if (loginServiceImpl.addImeiInfo(userId, imei)){
			ResponseUtil.sendBack(response, "true");
		}
		else{
			ResponseUtil.sendBack(response, "false");
		}
	}
}
