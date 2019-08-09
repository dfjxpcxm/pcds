/**
 * FileName:     RegexValieRule.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-27 下午3:16:50 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-27       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.engine;

import java.util.regex.Pattern;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;

/**
 * @Description:   TODO
 * 
 * @author         gongzhiyang
 */
public class RegexValidRule extends AbsValidRule  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2807876644243973439L;

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.engine.AbsValidRule#validate(com.shuhao.clean.apps.validate.entity.CheckRule, java.lang.Object[])
	 */
	public ValidResult validate(CheckRule rule, Object... arg) {
		if(arg.length > 0){
			//logger.debug("执行校验公式["+rule.getChk_method_code()+"]["+rule.getChk_rule_name()+"]["+rule.getChk_formula()+"]");
			return doValid(rule, getValue(arg[0]));
		}else{
			logger.error("校验输入参数有误，请传入至少1个参数。");
		}
		return null;
	}
	
	private ValidResult doValid(CheckRule rule,String value) {
		boolean status = isValid(rule, value);
		return new ValidResult(status, value);
	}
	
	private boolean isValid(CheckRule rule,String value) {
		return Pattern.matches(rule.getChk_formula(), value);
	}
}
