package com.sr178.module.web.action;

import java.util.List;

import com.sr178.common.jdbc.bean.IPage;

public class BasePageActionSupport<T> extends BaseActionSupport {
	/**  */
	private static final long serialVersionUID = 1L;
	
	/** 默认分页大小 **/
	public static final int DEFAULT_PAGESIZE = 20;
	
	/** 分页大小 **/
	private int pageSize = DEFAULT_PAGESIZE;

	private int toPage;

	private long totalPage;

	private long totalSize;
	
	private List<T> dataList;

	public int getToPage() {
		return toPage;
	}

	public void setToPage(int toPage) {
		this.toPage = toPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void initPage(IPage<T> page){
		if(page!=null){
			dataList = (List<T>)page.getData();
			this.totalPage = page.getTotalPage();
			this.totalSize = page.getTotalSize();
			this.pageSize = page.getPageSize();
		}
	}
	
	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
