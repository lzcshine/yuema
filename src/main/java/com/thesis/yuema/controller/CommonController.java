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
 * 2015-1-31 下午8:19:51
 */
@Controller
public class CommonController {

	@Resource(name = "userServiceImpl")
	UserService userServiceImpl;
	
	@RequestMapping(value="/searchUsers")
	public void getSearchUsers(HttpServletResponse response, String text){
		ResponseUtil.sendBack(response, JsonUtil.toJson(userServiceImpl.getSearchUsers(text)));
	}
}
