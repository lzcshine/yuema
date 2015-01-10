package com.thesis.yuema.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.thesis.yuema.common.AndroidPager;
import com.thesis.yuema.common.Pager;
import com.thesis.yuema.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {

	public SessionFactory sessionFactory;
	protected Class<T> entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDaoImpl() {
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean save(T entity) {
		try {
			getSession().save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean persist(T entity) {
		try {
			getSession().persist(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(T entity) {
		try {
			getSession().update(entity);
			getSession().flush();
			/* getSession().clear(); */
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(T entity) {
		try {
			getSession().delete(entity);
			getSession().flush();
			getSession().clear();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager findByPager(Pager pager) {
		Criteria criteria = getSession().createCriteria(entityClass);
		// 获得此查询条件下的总纪录条数
		criteria.setProjection(Projections.rowCount());
		int totalCount = ((Long) criteria.uniqueResult()).intValue();
		pager.setTotalCount(totalCount);
		// 删除对查询结果的统计限制
		criteria.setProjection(null);

		pager.operate();

		if (pager.getOrderMap() != null) {
			for (String key : pager.getOrderMap().keySet()) {
				if (pager.getOrder() == com.thesis.yuema.common.Pager.Order.asc) {
					criteria.addOrder(Order.asc(key));
				} else if (pager.getOrder() == com.thesis.yuema.common.Pager.Order.desc) {
					criteria.addOrder(Order.desc(key));
				}
			}
		} else {
			criteria.addOrder(Order.desc("id"));
		}

		criteria.setFirstResult((pager.getPageNumber() - 1)
				* pager.getPageSize());
		criteria.setMaxResults(pager.getPageSize());
		pager.setResult((List<T>) criteria.list());
		return pager;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllList() {
		return getSession().createCriteria(entityClass).list();
	}

	@Override
	public int findAllCount() {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		int totalcount = Integer.parseInt(criteria.uniqueResult().toString());
		return totalcount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Integer id) {
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByIdSet(Integer[] ids) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.in("id", ids));
		return (List<T>) criteria.list();
	}

	@Override
	public boolean batchUpdate(List<T> list) {
		try {
			for (T entity : list) {
				getSession().update(entity);
			}
			getSession().flush();
			getSession().clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean batchDelete(List<T> list) {
		try {
			for (T entity : list) {
				getSession().delete(entity);
			}
			getSession().flush();
			getSession().clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean batchSave(List<T> list) {
		try {
			for (T entity : list) {
				getSession().save(entity);
			}
			getSession().flush();
			getSession().clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> execute(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> execute(Order... orders) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> execute(AndroidPager androidPager) {
		Criteria criteria = getSession().createCriteria(entityClass);
		Map<String, String> alias = androidPager.getAlias();
		for (String key : alias.keySet()) {
			criteria.createAlias(key, alias.get(key));
		}
		for (Projection Projection : androidPager.getProjections()) {
			criteria.setProjection(Projection);
		}
		for (Criterion criterion : androidPager.getCriterions()) {
			criteria.add(criterion);
		}
		for (Order order : androidPager.getOrders()) {
			criteria.addOrder(order);
		}
		criteria.setMaxResults(androidPager.getPageSize());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> executeQuery(String hql, int pageNumber, Object... keys) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < keys.length; i++) {
			query.setParameter(i, keys[i]);
		}
		if (pageNumber > 0) {
			query.setMaxResults(pageNumber);
		}
		return  query.list();
	}

	@Override
	public int executeUpdate(String hql, Object... keys) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < keys.length; i++) {
			query.setParameter(i, keys[i]);
		}
		return  query.executeUpdate();
	}
}
