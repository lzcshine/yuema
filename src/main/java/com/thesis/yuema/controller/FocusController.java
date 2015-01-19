package com.thesis.yuema.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesis.yuema.service.UserService;
import com.thesis.yuema.util.JsonUtil;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc
 * 2015-1-19 下午12:09:22
 */
@Controller
public class FocusController {

	@Resource(name="userServiceImpl")
	UserService userServiceImpl;
	
	@RequestMapping(value="/addFocus")
	public void addFocusRelation(HttpServletResponse response, int userId, int focusId){
		if (userServiceImpl.addUserFocus(userId, focusId)){
			ResponseUtil.sendBack(response, JsonUtil.toJson(true));
		}
		else{
			ResponseUtil.sendBack(response, JsonUtil.toJson(false));
		}
	}
	
	@RequestMapping(value="/getFocusListByUserId")
	public void getFocusListByUserId(HttpServletResponse response, int userId){
		ResponseUtil.sendBack(response, JsonUtil.toJson(userServiceImpl.getFocusListByUserId(userId)));
	}
}
