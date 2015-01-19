package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.thesis.yuema.common.Const;
import com.thesis.yuema.dao.ChatHistoryDao;
import com.thesis.yuema.entity.ChatHistory;

/**
 * @author:lzc
 * 2015-1-12 下午8:14:56
 */
@Repository("chatHistoryDaoImpl")
public class ChatHistoryDaoImpl extends BaseDaoImpl<ChatHistory> implements
		ChatHistoryDao {

	@Override
	public boolean addChatHistory(ChatHistory bean) {
		return this.save(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChatHistories(int chatId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" ch.userInfo.nickname as nickname, ");
		hql.append(" ch.userInfo.photo as photo, ");
		hql.append(" ch.content as content, ");
		hql.append(" ch.chatTime as chatTime ");
		hql.append(") from ChatHistory ch where ch.chatInfo.id=? order by ch.chatTime");
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), Const.CHAT_HISTORIES_COUNT, chatId);
		} catch (Exception e) {
		}
		return list;	
	}

}
