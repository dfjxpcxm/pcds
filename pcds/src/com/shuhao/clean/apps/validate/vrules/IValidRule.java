package com.shuhao.clean.apps.validate.vrules;

import java.io.Serializable;
import java.util.Map;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
/**
 * 
 * @Description:   校验规则  [@SELF] + 100 <1000
 * 
 * @author         gongzhiyang
 */
public interface IValidRule extends Serializable{
	/**
	 * 引用自己的站位符号,占位处理
	 */
	public static final String _SELF = "[@self]";
	
	/**
	 * 转成java可识别的变量名,对应 __SELF_VAL
	 */
	public static final String _SELF_JAVA = "__self_val";
	
	/**
	 * null对象，逻辑预算时使用
	 */
	public static final String _NULL = "nil";
 
	/**
	 * 参数，，定义使用范围（方式）：暂定,适用于正则表达式和java表达式
	 */
	//public static final String _PARAM = "$";
	
	/**
	 * 逻辑校验
	 * @param rule 规则
	 * @param object 对象
	 * @return
	 */
	public boolean isValid(CheckRule rule,Object ... arg);
	
	/**
	 * 一般校验，返回校验结果
	 * @param rule 规则
	 * @param object 对象
	 * @return
	 */
	public ValidResult validate(CheckRule rule,Object ... arg);
	
	/**
	 * 设置规则参数
	 */
	public void setContext(Map<String, Object> map);
}
