package com.thesis.yuema.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.service.LoginService;
import com.thesis.yuema.util.JsonUtil;
import com.thesis.yuema.util.ParamsUtil;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc 2015-1-9 下午9:04:28
 */
@Controller
public class LoginController {

	@Resource(name = "loginServiceImpl")
	LoginService loginServiceImpl;
	
	@RequestMapping(value = "/login")
	public void userToLogin(HttpServletResponse response, String username,
			String password) {
		if (!ParamsUtil.isValidParam(username,password)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		UserInfo user = loginServiceImpl.searchUser(username, password);
		if (user != null) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("username", username);
			map.put("nickname", user.getNickname());
			map.put("photo", user.getPhoto());
			map.put("isFrost", user.getIsFrost());
			map.put("introduce", user.getIntroduce());
			ResponseUtil.sendBack(response, JsonUtil.toJson(map));
		} else {
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}

	@RequestMapping(value = "/register")
	public void registerUser(HttpServletResponse response, String username,
			String password, String nickname) {
		if (!ParamsUtil.isValidParam(username, password, nickname)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (loginServiceImpl.addNewUser(username, password, nickname)) {
			ResponseUtil.sendBack(response, JsonUtil.toJson(loginServiceImpl
					.getUserInfoByUsername(username)));
		} else {
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}

	@RequestMapping(value = "/usernameUsable")
	public void usernameUsable(HttpServletResponse response, String username) {
		if (!ParamsUtil.isValidParam(username)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (loginServiceImpl.searchUserByUsername(username) == null) {
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		} else {
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}

	@RequestMapping(value = "/nicknameUsable")
	public void nicknameUsable(HttpServletResponse response, String nickname) {
		if (!ParamsUtil.isValidParam(nickname)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (loginServiceImpl.searchUserByNickname(nickname) == null) {
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		} else {
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}

	@RequestMapping(value = "/addImei")
	public void addImei(HttpServletResponse response, int userId, String imei) {
		if (!ParamsUtil.isValidParam(imei)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (loginServiceImpl.addImeiInfo(userId, imei)) {
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		} else {
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value = "/getUserInfoMapByUsername")
	public void getUserInfoMapByUsername(HttpServletResponse response, String username){
		if (!ParamsUtil.isValidParam(username)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		ResponseUtil.sendBack(response, JsonUtil.toJson(loginServiceImpl
				.getUserInfoByUsername(username)));
	}

}
