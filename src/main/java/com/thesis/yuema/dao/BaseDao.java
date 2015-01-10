package com.thesis.yuema.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.thesis.yuema.common.AndroidPager;
import com.thesis.yuema.common.Pager;

public interface BaseDao<T> {
	/**
	 * 向数据库写入记录的方法
	 * @param entity - 要操作的实体类
	 * @return 操作是否成功的标志值
	 */
	public boolean save(T entity);
	
	/**
	 * 用于级联的数据库写入记录
	 * @param entity --要操作的实体类
	 * @return 返回是否保存成功的标志
	 */
	public boolean persist(T entity);
	
	/**
	 * 向数据库更新数据的方法
	 * @param entity - 要操作的实体类
	 * @return Serializable
	 */
	public boolean update(T entity);
	
	/**
	 * 向数据库删除记录的方法
	 * @param entity - 要操作的实体类
	 * @return Serializable
	 */
	public boolean delete(T entity);
	
	/**
	 * 获得所有实体记录的方法
	 * @return 数据库表中所有的记录
	 */
	public List<T> findAllList();
	
	/**
	 * 根据分页查找记录的方法
	 * @param pager - 分页信息的实体类
	 * @return 分页的实体类，包含了最后返回的记录
	 */
	public Pager findByPager(Pager pager);
	
	/**
	 * 获取会话工厂的方法
	 * @return hibernate的会话工厂
	 */
	public SessionFactory getSessionFactory();
	
	
	/**
	 * 得到总的数据库的总数
	 * @return 返回所有的总数
	 */
	public int findAllCount();
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return 返回实体对象
	 */
	public T get(Integer id);
	
	/**
	 * 得到指定一批Id的实体记录
	 * @param ids
	 * @return 实体记录列表
	 */
	public List<T> findByIdSet(Integer ids[]);
	
	/**
	 * 向数据库批量更新数据的方法
	 * @param list - 要操作的实体集
	 * @return Serializable
	 */
	public boolean batchUpdate(List<T> list);
	
	/**
	 * 向数据库批量删除记录的方法
	 * @param list - 要操作的实体类集
	 * @return Serializable
	 */
	public boolean batchDelete(List<T> list);
	
	
	/**
	 * 向数据库批理添加记录的方法 
	 * @param list --要添加的数据库实体
	 * @return Serializable
	 */
	public boolean batchSave(List<T> list);
	
	/**
	  *  执行criteria条件的操作
	 * @param criterions --查询的条件
	 * @return 返回查询的结果
	 */
	public List<T> execute(Criterion... criterions);
	
	/**
	 * 执行criteria的操作
	 * @param orders --排序条件
	 * @return 返回查询结果
	 */
	public List<T> execute(Order... orders);
	
	/**
	 * 执行criteria查询的结果，进行分页查询
	 * @param androidPager --分页的条件，包括Criterion 和 Order 、还有结果的条数
	 * @return 返回查询的结果
	 */
	public List<T> execute(AndroidPager androidPager);
	
	/**
	 * 执行hql查询语句
	 * @param hql 需要执行的hql语句
	 * @param pageNumber 分页
	 * @param propertyMap 占位符参数键值对
	 * @return 返回查询的结果
	 */
	public List executeQuery(String hql, int pageNumber, Object... keys);
	
	/**
	 * 执行hql更新语句
	 * @param hql 需要执行的hql语句
	 * @param propertyMap 占位符参数键值对
	 * @return 返回更新的记录数
	 */
	public int executeUpdate(String hql, Object... keys);
	
}
