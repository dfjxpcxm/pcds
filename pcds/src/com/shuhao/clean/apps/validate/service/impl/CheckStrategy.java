/**
 * FileName:     CheckStrategy.java
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
 * @Description:   TODO 校验策略
 * 
 * @author:         gongzhiyang
 */
public class CheckStrategy {
	
	protected static final Logger logger = Logger.getLogger(CheckStrategy.class);
	
	private ICheckService checkService;
	private Map<String, Object> params;
	private Map<String, Object> context;
	private Map<String, Object> data;
	private boolean isTrans;//行级校验时,转换key
	
	/**
	 * 每步校验异常后立即返回
	 * chk_action = ['0', '通用'],['2', '独立校验']
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doCheck()throws Exception{
		Map<String, Object> errorMap = new HashMap<String, Object>();
		List<IMessage> msgs = new ArrayList<IMessage>();
		addParam("chk_action", "0"); //通用校验
		//1.字段
		msgs = checkService.cellsValid("", data,getContext());
		if(msgs.size()>0){
			errorMap.put("errors", getFormErrors(msgs,false));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		
		//2.功能页面
		errorMap = doPageCheck();
		//3.表
		errorMap = doTableCheck();
		return errorMap;
	}
	
	/**
	 * 后校验(独立校验)，提交时校验,只校验sql，针对所有数据补完之后的校验
	 * params : templateId,metadataType,metaId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doAfterCheck()throws Exception{
		Map<String, Object> errorMap = new HashMap<String, Object>();
		List<IMessage> msgs = new ArrayList<IMessage>();
		//设置参数,元数据类型:表
		addParam("chk_type_cd", "02"); //表间
		addParam("chk_action", "2"); //独立校验
		//导入完成后整表校验
		msgs = checkService.validAfterImport(getParams(),getData(),getContext());
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		return errorMap;
	}
	
	//校验功能页面
	public Map<String, Object> doPageCheck() {
		List<IMessage> msgs = checkService.validFunctionMeta(getParams(), getData(), getContext(), isTrans());
		return formatMsg(msgs);
	}
		
	//校验表
	public Map<String, Object> doTableCheck() {
		List<IMessage> msgs = checkService.validTableMeta(getParams(), getData(), getContext(), isTrans());
		return formatMsg(msgs);
	}
	
	//格式化消息
	public Map<String, Object> formatMsg(List<IMessage> msgs) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		if(msgs.size()>0){
			errorMap.put("errors", getGridErrors(msgs,true));
			errorMap.put("success", Boolean.valueOf(false));
			return errorMap;
		} 
		return errorMap;
	}
	
	public List<ErrorField> getFormErrors(List<IMessage> messages,boolean show){
		List<ErrorField> errors = new ArrayList<ErrorField>();
		for (IMessage msg : messages) {
			errors.add(new ErrorField(msg.getId(),msg.getShortMsg(),show));
		}
		return errors;
	}
	
	public List<ErrorField> getGridErrors(List<IMessage> messages,boolean show){
		List<ErrorField> errors = new ArrayList<ErrorField>();
		for (IMessage msg : messages) {
			errors.add(new ErrorField(msg.getName(),msg.getMsg(),show));
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
