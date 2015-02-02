package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.thesis.yuema.common.Const;
import com.thesis.yuema.dao.UserInfoDao;
import com.thesis.yuema.entity.UserInfo;
import com.thesis.yuema.dao.impl.BaseDaoImpl;

/**
 * @author:lzc 2015-1-9 下午9:17:01
 */
@Repository("userInfoDaoImpl")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements
		UserInfoDao {

	@Override
	public boolean addNewUser(UserInfo user) {
		return this.save(user);
	}

	@Override
	public UserInfo searchUser(String username, String password) {
		String hql = "from UserInfo where username=? and password=?";
//		String hql = "from UserInfo where username=:username and password=:password";
//		Query query = this.getSession().createQuery(hql);
//		query.setString("username", username);
//		query.setString("password", password);
//		UserInfo user = (UserInfo)query.uniqueResult();
		UserInfo user = (UserInfo) this.getSession().createQuery(hql)
				.setParameter(0, username).setParameter(1, password)
				.uniqueResult();
		return user;
	}

	@Override
	public UserInfo getUserInfoByUserId(int userId) {
		String hql = "from UserInfo where id=?";
		UserInfo user = null;
		try{
			user = (UserInfo) this.getSession().createQuery(hql)
					.setParameter(0, userId).uniqueResult();
		}catch(Exception e){
			
		}
		return user;
	}

	@Override
	public UserInfo getUserInfoByNickname(String nickname) {
		String hql = "from UserInfo where nickname=?";
		UserInfo user = null;
		try{
			user = (UserInfo) this.getSession().createQuery(hql)
					.setParameter(0, nickname).uniqueResult();
		}catch(Exception e){
			
		}
		return user;
	}

	@Override
	public UserInfo getUserInfoByUsername(String username) {
		String hql = "from UserInfo where username=?";
		UserInfo user = null;
		try{
			user = (UserInfo) this.getSession().createQuery(hql)
					.setParameter(0, username).uniqueResult();
		}catch(Exception e){
			
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserInfoMapByUsername(String username) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" u.id as userId, ");
		hql.append(" u.username as username, ");
		hql.append(" u.nickname as nickname, ");
		hql.append(" u.photo as photo ");
		hql.append(") from UserInfo u where u.username=?");
		Map<String,Object> map = null;
		try {
			map =  (Map<String, Object>) this.getSession().createQuery(hql.toString()).setParameter(0, username).uniqueResult();
		} catch (Exception e) {
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getSearchUsers(String text) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select new map( ");
		hql.append(" u.id as userId, ");
		hql.append(" u.username as username, ");
		hql.append(" u.nickname as nickname, ");
		hql.append(" u.photo as photo ");
		hql.append(") from UserInfo u where u.username like '%");
		hql.append(text);
		hql.append("%' or u.nickname like '%");
		hql.append(text);
		hql.append("%'");
		List<Map<String,Object>> list = null;
		try {
			list = this.getSession().createQuery(hql.toString()).list();
		} catch (Exception e) {
		}
		return list;
	}

}
