package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.thesis.yuema.dao.FeedbackDao;
import com.thesis.yuema.entity.FeedbackInfo;

/**
 * @author:lzc
 * 2015-1-12 下午9:15:44
 */
@Repository("feedbackDaoImpl")
public class FeedbackDaoImpl extends BaseDaoImpl<FeedbackInfo> implements FeedbackDao {

	@Override
	public boolean addFeedbackInfo(FeedbackInfo bean) {
		return this.save(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getFeedbackReply(int userId, int count) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" fb.feedback as feedback, ");
		hql.append(" fb.replyFeedback as reply, ");
		hql.append(" fb.replyTime as replyTime ");
		hql.append(") from FeedbackInfo fb where fb.userInfo.id=? order by fb.replyTime desc");
		if (count == 0){
			count = 3;
		}
		List<Map<String,Object>> list = null;
		try {
			list = (List<Map<String, Object>>) this.executeQuery(hql.toString(), count, userId);
		} catch (Exception e) {
		}
		return list;
	}

}
