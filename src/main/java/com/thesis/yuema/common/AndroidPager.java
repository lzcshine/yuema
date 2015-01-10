package com.thesis.yuema.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public class AndroidPager {

	public static final Integer MAX_PAGE_SIZE = 50;// 每页最大记录数限制
	 
	private int pageSize = 10;// 每页记录
	private List<Criterion> criterions = new ArrayList<Criterion>(); //条件
	private List<Order> orders = new ArrayList<Order>();; // 排序条件
	private Map<String,String> alias = new LinkedHashMap<String, String>();
	private List<Projection> projections = new ArrayList<Projection>();
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Map<String, String> getAlias() {
		return alias;
	}

	public void setAlias(Map<String, String> alias) {
		this.alias = alias;
	}
	

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections=projections;
	}

	/**
	 * @description 
	 * @param projection
	 * @return
	 */
	public AndroidPager addProjections(Projection projection) {
		this.projections.add(projection);
		return this;
	}
	
	/**
	 * 
	 * @param criterions
	 */
	
	public AndroidPager addCriterions(Criterion criterion) {
		 this.criterions.add(criterion);
		 return this;
	}
	
	/**
	 * 
	 * @param orders
	 */
	public AndroidPager addOrders(Order order) {
		this.orders.add(order);
		return this;
	}

	/**
	 * 
	 * @param property
	 * @param alia
	 */
	public AndroidPager addAlias(String property, String alia ) {
			this.alias.put(property, alia);
			return this;
	}
		
}
