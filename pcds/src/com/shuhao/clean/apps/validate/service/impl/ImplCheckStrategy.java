/**
 * FileName:     ImplCheckStrategy.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-6 下午3:00:44 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-6       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shuhao.clean.apps.meta.entity.ErrorField;
import com.shuhao.clean.apps.validate.service.ICheckService;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;

/**
 * @Description:   TODO 导入校验策略
 * 
 * 
 * @author:         gongzhiyang
 */
public class ImplCheckStrategy {
	
	protected static final Logger logger = Logger.getLogger(ImplCheckStrategy.class);
	
	private ICheckService checkService;
	private Map<String, Object> params;
	private Map<String, Object> context;
	private Map<String, Object> data;
	private boolean isTrans;//行级校验时,转换key
	
	private int index = 0;
	
	/**
	 * 每步校验异常后立即返回
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doCheck()throws Exception{
		Map<String, Object> errorMap = new HashMap<String, Object>();
		List<IMessage> msgs = new ArrayList<IMessage>();
		//1.字段
		msgs = checkService.cellsValid("", data,getContext());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		//2.功能页面 : PAG
		msgs = checkService.validFunctionMeta(getParams(), getData(), getContext(), isTrans());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		//3.表 : TAB
		msgs = checkService.validTableMeta(getParams(), getData(), getContext(), isTrans());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		}
		
		return errorMap;
	}
	
	/**
	 * 执行excel元数据校验
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doExcelCheck()throws Exception{
		Map<String, Object> errorMap = new HashMap<String, Object>();
		List<IMessage> msgs = new ArrayList<IMessage>();
		//1.excel字段 : XCL
		msgs = checkService.cellsValid("", data,getContext());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		
		//2.Excel工作簿 : XST - sheet 
		msgs = checkService.validExcelSheet(getParams(), getData(), getContext(), isTrans());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		
		//3.sheet关联表 : TAB  - table
		msgs = checkService.validExcelSheetTable(getParams(), getData(), getContext(), isTrans());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		}
		
		return errorMap;
	}
	
	public List<ErrorField> getGridErrors(List<IMessage> messages,boolean show){
		List<ErrorField> errors = new ArrayList<ErrorField>();
		for (IMessage msg : messages) {
			errors.add(new ErrorField("校验导入文件第["+this.index+"]条记录失败", msg.getMsg(),show));
		}
		return errors;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * 添加一个参数
	 * @param key
	 * @param value
	 */
	public void addParam(String key,String value) {
		if(this.params ==null){
			this.params = new HashMap<String, Object>();
		}
		this.params.put(key, value);
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public ICheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(ICheckService checkService) {
		this.checkService = checkService;
	}

	public boolean isTrans() {
		return isTrans;
	}

	public void setTrans(boolean isTrans) {
		this.isTrans = isTrans;
	}
}
