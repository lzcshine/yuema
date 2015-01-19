package com.thesis.yuema.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thesis.yuema.service.FeedbackService;
import com.thesis.yuema.util.JsonUtil;
import com.thesis.yuema.util.ParamsUtil;
import com.thesis.yuema.util.ResponseUtil;

/**
 * @author:lzc
 * 2015-1-12 下午9:32:58
 */
@Controller
public class FeedbackController {
	
	@Resource(name="feedbackServiceImpl")
	FeedbackService feedbackServiceImpl;

	@RequestMapping(value="/addFeedback")
	public void addFeedbackInfo(HttpServletResponse response, int userId, String feedback){
		if (!ParamsUtil.isValidParam(feedback)){
			ResponseUtil.sendBack(response, "request param is not valid");
		}
		if (feedbackServiceImpl.addFeedback(userId, feedback)){
			ResponseUtil.sendBack(response, "true");
		}
		else{
			ResponseUtil.sendBack(response, "false");
		}
	}
	
	@RequestMapping(value="/getFeedbackReply")
	public void getFeedbackReply(HttpServletResponse response, int userId, @RequestParam(value="count",required=false)int count){
		System.out.print(111);
		ResponseUtil.sendBack(response, JsonUtil.toJson(feedbackServiceImpl.getFeedbackReplyByUserId(userId, count)));
	}
}
