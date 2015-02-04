package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.thesis.yuema.common.Const;
import com.thesis.yuema.dao.ChatMemberDao;
import com.thesis.yuema.entity.ChatMember;

/**
 * @author:lzc
 * 2015-1-12 下午5:30:59
 */
@Repository("chatMemberDaoImpl")
public class ChatMemberDaoImpl extends BaseDaoImpl<ChatMember> implements ChatMemberDao {

	@Override
	public boolean addChatMember(ChatMember bean) {
		return this.save(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChatMembersInfo(int chatId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" cm.userInfo.nickname as nickname, ");
		hql.append(" cm.userInfo.photo as photo, ");
		hql.append(" cm.userInfo.id as userId ");
		hql.append(") from ChatMember cm where cm.chatInfo.id = ? ");
		List<Map<String,Object>> list = null;
		try {
			list = this.getSession().createQuery(hql.toString())
					.setParameter(0, chatId).list();
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public boolean addChatMemberBatch(List<ChatMember> list) {
		return this.batchSave(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChatInfosByUserId(int userId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" cm.chatInfo.id as chatId, ");
		hql.append(" cm.chatInfo.title as title, ");
		hql.append(" cm.chatInfo.userInfo.id as sponsorId, ");
		hql.append(" cm.chatInfo.userInfo.nickname as sponsorNickname, ");
		hql.append(" cm.chatInfo.createTime as createTime ");
		hql.append(") from ChatMember cm where cm.userInfo.id = ? order by cm.chatInfo.createTime desc");
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), Const.EVENT_COUNT, userId);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public List<String> getUsernamesByChatId(int chatId) {
		String hql = "select cm.userInfo.username as username from ChatMember cm where cm.id=?";
		List<String> list = this.getSession().createQuery(hql).setParameter(0, chatId).list();
		return list;
	}

	@Override
	public boolean deleteChatMember(int chatId, String username) {
		String hql = "from ChatMember cm where cm.chatInfo.id=? and cm.userInfo.username=?";
		ChatMember bean = (ChatMember) this.getSession().createQuery(hql).setParameter(0, chatId).setParameter(1, username).uniqueResult();
		if (bean == null){
			return false;
		}
		return this.delete(bean);
	}

	@Override
	public List<Map<String, Object>> getScrollChatInfosByUserId(int userId,
			int start) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" cm.chatInfo.id as chatId, ");
		hql.append(" cm.chatInfo.title as title, ");
		hql.append(" cm.chatInfo.userInfo.id as sponsorId, ");
		hql.append(" cm.chatInfo.userInfo.nickname as sponsorNickname, ");
		hql.append(" cm.chatInfo.createTime as createTime ");
		hql.append(") from ChatMember cm where cm.chatInfo.id > ? and cm.userInfo.id = ? order by cm.chatInfo.createTime desc");
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), Const.EVENT_COUNT, start, userId);
		} catch (Exception e) {
		}
		return list;
	}

}
