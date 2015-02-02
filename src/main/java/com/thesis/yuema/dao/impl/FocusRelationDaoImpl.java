package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.thesis.yuema.dao.FocusRelationDao;
import com.thesis.yuema.entity.FocusRelation;

/**
 * @author:lzc 2015-1-19 下午12:11:41
 */
@Repository("focusRelationDaoImpl")
public class FocusRelationDaoImpl extends BaseDaoImpl<FocusRelation> implements
		FocusRelationDao {

	@Override
	public boolean addFocusRelation(FocusRelation bean) {
		return this.save(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getFocusListByUserId(int userId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" fr.userInfoByFocusId.username as focusUsername, ");
		hql.append(" fr.userInfoByFocusId.nickname as focusNickname, ");
		hql.append(" fr.userInfoByFocusId.photo as focusPhoto ");
		hql.append(") from FocusRelation fr where fr.userInfoByMyId.id=? order by fr.id desc");
		List<Map<String, Object>> list = null;
		try {
			list = this.getSession().createQuery(hql.toString())
					.setParameter(0, userId).list();
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public boolean deleteFocusRelation(int userId, String focusUsername) {
		String hql = "from FocusRelation fr where fr.userInfoByMyId.id=? and fr.userInfoByFocusId.username=?";
		FocusRelation bean = (FocusRelation) this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, focusUsername).uniqueResult();
		if (bean == null){
			return false;
		}
		return this.delete(bean);
	}

}
