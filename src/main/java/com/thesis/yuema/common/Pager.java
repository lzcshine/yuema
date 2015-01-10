package com.thesis.yuema.common;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component()
public class Pager {
   
	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
	 
	// 排序方式（递增、递减）
	public enum Order {
		asc, desc
	}
	
	public enum OrderType{
		time, heat
	}
	
	// 操作（首页，上一页，下一页，尾页）
	public enum Operate {
		first, previous, next, last
	}
	
	public Pager(){
		setOrderType(OrderType.time);
	}
	
	/**
	 * 每页记录
	 */
	private int pageSize;
	
	/**
	 * 当前页码数
	 */
	private int pageNumber;

	/**
	 * 要查找的字段
	 */
	private String searchBy;
	
	/**
	 * 查找的关键字
	 */
	private String keyword;
	
	/**
	 * 要排序的字段
	 */
	private String orderBy;
	
	/**
	 * 默认的排序方式
	 */
	private Order order;
	
	/**
	 * 按照条件排序
	 */
	private OrderType orderType;
	
	/**
	 * 总记录数
	 */
	private int totalCount;
	
	/**
	 * 查询返回的结果
	 */
	private List<?> result;
	
	/**
	 * 是否有上一页
	 */
	private boolean previousFlag;
	
	/**
	 * 是否有下一页
	 */
	private boolean nextFlag;
	
	/**
	 * 操作（首页，上一页，下一页，尾页）
	 */
	private Operate operate;
	
	private Map<String, Order> orderMap;

	/**
	 * 分页操作
	 */
	public void operate() {
		if (operate == Operate.first) {
			firstPage();
		} else if (operate == Operate.previous) {
			previousPage();
		} else if (operate == Operate.next) {
			nextPage();
		} else if (operate == Operate.last) {
			lastPage();
		}
	}
	
	/**
	 * 首页
	 */
	private void firstPage() {
		pageNumber = 1;
		previousFlag = false;
		nextFlag = pageNumber < getPageCount();
	}
	
	/**
	 * 上一页
	 */
	private void previousPage() {
		if (previousFlag) {
			pageNumber--;
			previousFlag = pageNumber > 1;
			nextFlag = pageNumber < getPageCount();	
		}
	}
	
	/**
	 * 下一页
	 */
	private void nextPage() {
		if (nextFlag) {
			pageNumber++;
			previousFlag = pageNumber > 1;
			nextFlag = pageNumber < getPageCount();	
		}
	}
	
	/**
	 * 尾页
	 */
	private void lastPage() {
		pageNumber = getPageCount();
		previousFlag = pageNumber > 1;
		nextFlag = false;
	}
	
	/**
	 * 获取总页数的方法
	 * @return 总页数
	 */
	public int getPageCount() {
		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

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

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public boolean isPreviousFlag() {
		return previousFlag;
	}

	public void setPreviousFlag(boolean previousFlag) {
		this.previousFlag = previousFlag;
	}

	public boolean isNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(boolean nextFlag) {
		this.nextFlag = nextFlag;
	}

	public Operate getOperate() {
		return operate;
	}

	public void setOperate(Operate operate) {
		this.operate = operate;
	}
	
	
	public Map<String, Order> getOrderMap()
	{
		return orderMap;
	}

	public void setOrderMap(Map<String, Order> orderMap)
	{
		this.orderMap = orderMap;
	}
	
}