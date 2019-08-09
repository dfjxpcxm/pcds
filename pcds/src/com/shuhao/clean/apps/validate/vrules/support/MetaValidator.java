/**
 * FileName:     MetaValidator.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-27 上午11:36:22 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-27       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.support;

import java.util.Map;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.IValidRule;
import com.shuhao.clean.apps.validate.vrules.IValidator;
import com.shuhao.clean.apps.validate.vrules.ValidatorFactory;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;

/**
 * @Description:   元数据校验
 * 
 * @author         gongzhiyang
 */
public class MetaValidator implements IValidator {
	
	/**
	 * 按规则校验
	 * @param rule
	 * @param value
	 * @param params 通用参数
	 * @return
	 */
	public static ValidResult validate(CheckRule rule,Map<String, Object> params,Object ... value){
		IValidRule vrule = ValidatorFactory.lookupVRule(rule.getChk_method_code());
		if(params!=null){
			vrule.setContext(params);
		}
		return vrule.validate(rule, value);
	}
}

