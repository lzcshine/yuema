package com.thesis.yuema.service;

import java.util.List;
import java.util.Map;

/**
 * @author:lzc
 * 2015-1-19 下午12:20:22
 */

public interface UserService {

	boolean addUserFocus(int userId, int focusId);
	
	List<Map<String,Object>> getFocusListByUserId(int userId);
}
