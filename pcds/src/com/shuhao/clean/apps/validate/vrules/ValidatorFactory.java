/**
 * FileName:     ValidatorFactory.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-29 下午3:58:23 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-29       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.shuhao.clean.apps.validate.vrules.engine.JavaValidRule;
import com.shuhao.clean.apps.validate.vrules.engine.RegexValidRule;
import com.shuhao.clean.apps.validate.vrules.engine.SqlValidRule;

/**
 * @Description:   校验器工厂
 * 
 * @author:         gongzhiyang
 */
public class ValidatorFactory {
	
	protected static final Logger logger = Logger.getLogger(ValidatorFactory.class);
	
	private JdbcTemplate jdbcTemplate ;
	
	private static ValidatorFactory validatorFactory = null;
	
	private ValidatorFactory(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public static ValidatorFactory getInstance(JdbcTemplate jdbcTemplate){
		if(validatorFactory==null){
			validatorFactory = new ValidatorFactory(jdbcTemplate);
		}
		return validatorFactory;
	}
	
	public JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate;
	}
	
	/**
	 * 查找校验规则 Rule。
	 */
	public static IValidRule  lookupVRule(String vType) {
		IValidRule rule = null;
		try {
			if (vType.equals(IValidator.VTYPE_REXGX)){
				rule = new RegexValidRule();
			} else if(vType.equals(IValidator.VTYPE_JAVA)){
				rule = new JavaValidRule();
			} else if(vType.equals(IValidator.VTYPE_SQL)){
				rule = new SqlValidRule(ValidatorFactory.getInstance(null).getJdbcTemplate());
			} else{
				logger.error("不存在类型为["+vType+"]的校验器.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return rule;
	}

	public static void main(String[] args) {
		System.out.println( RegexValidRule.class.getName());
	}
}