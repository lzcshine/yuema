package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import com.thesis.yuema.common.Const;
import com.thesis.yuema.dao.ChatInfoDao;
import com.thesis.yuema.entity.ChatInfo;

/**
 * @author:lzc 2015-1-10 下午4:09:38
 */
@Repository("chatInfoDaoImpl")
public class ChatInfoDaoImpl extends BaseDaoImpl<ChatInfo> implements
		ChatInfoDao {

	private final static Logger log = Logger.getLogger(ChatInfoDaoImpl.class);

	@Override
	public boolean addChatInfo(ChatInfo chat) {
		return this.save(chat);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getChatInfoByChatId(int chatId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" ci.userInfo.nickname as nickname, ");
		hql.append(" ci.title as title ");
		hql.append(") from ChatInfo ci where is_response=1 ");
		hql.append(" and ci.id = ? ");
		Map<String,Object> map = null;
		try {
			map = (Map<String, Object>) this.getSession().createQuery(hql.toString())
					.setParameter(0, chatId);
		} catch (Exception e) {
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getChatInfosBySponsorId(int sponsorId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" ci.id as chatId, ");
		hql.append(" ci.title as title, ");
		hql.append(" ci.createTime as createTime, ");
		hql.append(" ci.userInfo.nickname as sponsorNickname, ");
		hql.append(" ci.limitTime as limitTime ");
		hql.append(") from ChatInfo ci where is_response=0 ");
		hql.append(" and ci.userInfo.id = ? order by ci.createTime desc");
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), Const.EVENT_INVITING_COUNT, sponsorId);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public boolean updateIsResponseByChatId(ChatInfo chat) {
		return this.update(chat);
	}

	@Override
	public boolean deleteChatInfo(int chatId) {
		ChatInfo chat = getChatInfoById(chatId);
		if (chat == null){
			return false;
		}
		return this.delete(chat);
	}

	@Override
	public ChatInfo getChatInfoById(int chatId) {
		String hql = "from ChatInfo where id=?";
		ChatInfo chatInfo = (ChatInfo) this.getSession().createQuery(hql).setParameter(0, chatId).uniqueResult();
		return chatInfo;
	}

	@Override
	public List<Map<String, Object>> getChatInfosLimitBySponsorId(
			int sponsorId, int start) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" ci.id as chatId, ");
		hql.append(" ci.title as title, ");
		hql.append(" ci.createTime as createTime, ");
		hql.append(" ci.userInfo.nickname as sponsorNickname, ");
		hql.append(" ci.limitTime as limitTime ");
		hql.append(") from ChatInfo ci where is_response=0 ");
		hql.append(" and ci.id < ? and ci.userInfo.id = ? order by ci.createTime desc");
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), Const.EVENT_INVITING_COUNT, start,sponsorId);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public ChatInfo getChatInfoByUsernameAndTime(String username,
			String createTime) {
		String hql = "from ChatInfo c where c.userInfo.username=? and c.createTime=?";
		ChatInfo chatInfo = (ChatInfo) this.getSession().createQuery(hql).setParameter(0, username).setParameter(1, createTime).uniqueResult();
		return chatInfo;
	}

}
