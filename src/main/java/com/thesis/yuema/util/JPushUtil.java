package com.thesis.yuema.util;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.thesis.yuema.common.Const;

/**
 * @author:lzc 2015-1-24 下午8:07:14
 */

public class JPushUtil {

	private static JPushClient jpush = new JPushClient(Const.MASTER_SECRET,
			Const.APP_KEY);

	/**
	 * 发送系统通知给所有用户
	 */
	public static boolean pushNotificationToAll(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Const.NOTIFICATION_TYPE, Const.NOTIFICATION_SYSTEM);
		try {
			MessageResult result = jpush.sendCustomMessageWithAppKey(
					RandomUtils.getRandomSendNo(), Const.NOTIFICATION_MESSAGE,
					msg, "0", map);
			if (result.getResponse_status() == 200) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 发送系统通知给指定用户
	 */
	public static boolean pushNotificationToOne(String alias, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Const.NOTIFICATION_TYPE, Const.NOTIFICATION_SYSTEM);
		try {
			MessageResult result = jpush.sendNotificationWithAlias(
					RandomUtils.getRandomSendNo(), alias,
					Const.NOTIFICATION_MESSAGE, msg, 0, map);
			if (result.getResponse_status() == 200) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 发送系统通知给指定用户
	 */
	public static boolean pushCustomMessageToOne(String alias, Map<String, Object> map) {
		try {
			MessageResult result = jpush.sendCustomMessageWithAlias(RandomUtils.getRandomSendNo(), alias, "消息", "", null, map);
			if (result.getResponse_status() == 200) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 发送活动邀请指定用户
	 */
	public static boolean pushEventInviteToUser(String alias,
			Map<String, Object> map) {
		map.put(Const.NOTIFICATION_TYPE, Const.NOTIFICATION_EVENT);
		try {
			MessageResult result = jpush.sendNotificationWithAlias(
					RandomUtils.getRandomSendNo(), alias,
					Const.NOTIFICATION_EVENT_INVITE, (String)map.get("title"), 0, map);
			if (result.getResponse_status() == 200) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 发送活动邀请指定用户
	 */
	public static boolean pushInviteSuccessToUser(String alias,
			Map<String, Object> map) {
		map.put(Const.NOTIFICATION_TYPE, Const.NOTIFICATION_EVENT_SUCCESS);
		try {
			MessageResult result = jpush.sendNotificationWithAlias(
					RandomUtils.getRandomSendNo(), alias,
					Const.NOTIFICATION_MESSAGE, "有人回应啦！", 0, map);
			if (result.getResponse_status() == 200) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}