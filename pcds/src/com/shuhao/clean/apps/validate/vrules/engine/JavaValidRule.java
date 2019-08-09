/**
 * FileName:     JavaValidRule.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-27 下午3:22:35 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-27       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.engine;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.apps.validate.vrules.function.AddFunction;
import com.shuhao.clean.apps.validate.vrules.function.IffFunction;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * @Description:   校验解析器工厂
 * <br>
 * #col1+#col2+#col3>#col4
 * #self_val!=null
 * @author         gongzhiyang
 */
public class JavaValidRule extends AbsValidRule  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6485154733397449105L;
	
	static{
		AviatorEvaluator.addFunction(new IffFunction());
		AviatorEvaluator.addFunction(new AddFunction());
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.IValidRule#validate(com.shuhao.clean.apps.validate.entity.CheckRule, java.lang.Object[])
	 */
	public ValidResult validate(CheckRule rule, Object... arg) {
		if(arg.length > 0){
			//单值校验
			if(arg[0] instanceof String){
				return doValid(rule, getValue(arg[0]));
			}else if(arg[0] instanceof HashMap){
				return doValid(rule, (HashMap<String, Object>)arg[0]);
			}else{
				logger.error("校验输入参数有误。");
			}
		}else{
			logger.error("校验输入参数有误，请传入至少1个参数。");
		}
		return null;
	}
	
	/**
	 * 单值校验,注意 #,$的区别
	 * @param rule
	 * @param value
	 * @return
	 */
	public ValidResult doValid(CheckRule rule,String value){
		String formula = null;
		try {
			value =GlobalUtil.isNull(value) ? null : value;
			//传入变量
			Map<String, Object> env = new HashMap<String, Object>();
			env.put(_SELF_JAVA, value);
			
			//替换[@self]
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("self", value);
			//替换变量[@...]
			formula = formatVars(rule.getChk_formula(), param, "nil");
			
			return new ValidResult((Boolean)AviatorEvaluator.execute(formula,env), formula) ;
		} catch (Exception e) {
			logger.error("["+rule.getChk_rule_code()+"]["+rule.getChk_formula()+"]["+formula+"]");
			return new ValidResult(false, e.getMessage());
		}
	}
	
	/**
	 * 行间校验   #a+#b+#c<=#d
	 * @param rule
	 * @param map
	 * @return
	 */
	public ValidResult doValid(CheckRule rule,HashMap<String, Object> map){
		try {
			String formula = rule.getChk_formula();
			//判断表达式是否为null
			if(GlobalUtil.isNull(formula)){
				return new ValidResult(false, "["+rule.getChk_type_code()+"]校验公式为null") ;
			}
			//转换，自定义的部分函数
			formula = transferExpr(formula);
			
			//全局替换
			formula = commonFormat(formula);
			
			//传入参数替换
			formula = formatVars(formula, map, "");
			
			boolean success = (Boolean)AviatorEvaluator.execute(formula,map);
			return new ValidResult(success, formula) ;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ValidResult(false, e.getMessage()) ;
		}
	}
	
	//部分表达式简单替换
	private String transferExpr(String formula){
		String p = GlobalUtil.getParam(formula, "isNull(", ")");
		while(!p.equals("")){
			formula = GlobalUtil.replace(formula,"isNull("+p+")", "("+p+") == nil");
			p = GlobalUtil.getParam(formula, "isNull(", ")");
		}
		return formula;
	}
}
