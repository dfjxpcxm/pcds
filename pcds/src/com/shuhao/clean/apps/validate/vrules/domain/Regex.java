/**
 * FileName:     Regex.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-1 上午11:46:26 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-1       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.domain;

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * @Description:   TODO 正则表达式
 * 
 * @author:         gongzhiyang
 */
public class Regex {
	
	private String id;
	private String formula; //*不校验
	private String desc;
	
	
	public Regex(String id,String desc,String formula){
		this.id = id;
		this.desc = desc;
		this.formula = formula;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 修改正则表达式的参数
	 * @param str
	 */
	public void addParams(Object ... str){
		this.formula = MessageFormat.format(formula, str);
	}
	
	public boolean validate(String value){
		return this.formula.equals("*") ? true : Pattern.matches(this.formula, value);
	}
}
