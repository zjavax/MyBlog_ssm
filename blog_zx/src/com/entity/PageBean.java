package com.entity;

/**
 * 分页Model类
 * @author 
 *
 */
public class PageBean {

	private int page; // 第几页
	private final static int pageSize = 2; // 每页记录数
	private int start;  // 起始页
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getStart() {
		return (page-1)*pageSize;
	}
	
	
}
