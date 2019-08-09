/**
 * FileName:     ValidResult.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-2 上午10:59:02 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-2       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:   TODO 校验结果
 * <br>
 * default 为默认
 * @author:         gongzhiyang
 */
public class ValidResult {
	
	public boolean success;
	public Map<String, Object> result = new HashMap<String, Object>();
	
	public ValidResult(boolean success,Map<String, Object> result){
		this.success = success;
		this.result = result;
	}
	
	public ValidResult(boolean success,String formula){
		this.success = success;
		this.result.put("default", formula);
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Map<String, Object> getResult() {
		return result;
	}
	
	public String getDefaultValue() {
		return String.valueOf(result.get("default"));
	}
	
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
}
