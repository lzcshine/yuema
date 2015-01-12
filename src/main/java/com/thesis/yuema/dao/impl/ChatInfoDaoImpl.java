package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

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

	@Override
	public ChatInfo getChatInfoByChatId(int chatId) {
		String hql = "from ChatInfo where id=?";
		ChatInfo chatInfo = null;
		try {
			chatInfo = (ChatInfo) this.getSession().createQuery(hql)
					.setParameter(0, chatId).uniqueResult();

		} catch (Exception e) {
		}
		return chatInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getChatInfosBySponsorId(int sponsorId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" ci.id as chatId, ");
		hql.append(" ci.title as title, ");
		hql.append(" ci.createTime as createTime, ");
		hql.append(" ci.limitTime as limitTime ");
		hql.append(") from ChatInfo ci where is_response=0 ");
		hql.append(" and ci.userInfo.id = ? ");
		List<Map<String,Object>> list = null;
		try {
			list = this.getSession().createQuery(hql.toString())
					.setParameter(0, sponsorId).list();
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public boolean updateIsResponseByChatId(ChatInfo chat) {
		return this.update(chat);
	}

}
