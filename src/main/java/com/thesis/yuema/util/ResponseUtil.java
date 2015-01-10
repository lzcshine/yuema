package com.thesis.yuema.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:lzc
 * 2015-1-9 下午10:09:44
 */

public class ResponseUtil {

	private static final Logger log = LoggerFactory.getLogger(ResponseUtil.class);
	
	/**
	 * ajax发送返回信息
	 * 
	 * @param data
	 */
	public static void sendBack(HttpServletResponse res, String data) {
		PrintWriter out = null;
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		try {
			out = res.getWriter();
		} catch (Exception e) {
			log.error("ajax发生异常:" + e.getMessage());
		}
		out.println(data);
		out.flush();
		out.close();
	}
}
