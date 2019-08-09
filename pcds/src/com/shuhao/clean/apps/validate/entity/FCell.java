/**
 * FileName:     FCell.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-5 下午4:05:36 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-5       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.entity;

/**
 * @Description:   TODO
 * <br>
 * meta_id_name : value
 * <br>分隔符是"_"
 * 
 * @author:         gongzhiyang
 */
public class FCell {

	private String id;
	private String name;
	private Object value;
	
	public FCell(String id,String name,Object value){
		this.id  = id;
		this.name = name;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return String.valueOf(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getFullId(){
		return this.id+"_"+this.name;
	}
}
