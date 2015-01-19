package com.thesis.yuema.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author:lzc 2015-1-19 下午2:40:12
 */

public class ParamsUtil {

	public static boolean isValidParam(String username) {
		if (StringUtils.isBlank(username)) {
			return false;
		}
		return true;
	}

	public static boolean isValidParam(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return false;
		}
		return true;
	}

	public static boolean isValidParam(String username, String password,
			String nickname) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(nickname)) {
			return false;
		}
		return true;
	}
}
