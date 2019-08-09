package com.shuhao.clean.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页结果类
 * @author gongzy
 *
 * @param <T> = Map,Object...
 */
public class PageResult<T> {
	
	public PageResult(){}
	
	public PageResult(int totalCount,List<T> results){
		this.totalCount=totalCount;
		this.results=results;
	}
	
	private int totalCount = 0;
	private List<T> results = new ArrayList<T>();
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
}
