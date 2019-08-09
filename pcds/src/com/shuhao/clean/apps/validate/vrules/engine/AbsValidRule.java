/**
 * FileName:     AbsValidRule.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-29 下午6:06:28 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-29       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.engine;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.IValidRule;
import com.shuhao.clean.utils.GlobalUtil;


/**
 * @Description:   校验规则管理器抽象类
 * 
 * @author:         gongzhiyang
 * 
 * @history:
 * 2015-3-8  优化变量、参数替换规则,使用  [D#日期变量],[B#boolean变量],[C#字符变量],[N#数字变量...],[@普通变量],[$参数]
 */
public abstract class AbsValidRule implements IValidRule {

	protected static final Logger logger = Logger.getLogger(AbsValidRule.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1947730180451470004L;
	
	protected Map<String, Object> context;//参数
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.IValidRule#isValid(com.shuhao.clean.apps.validate.entity.CheckRule, java.lang.Object[])
	 */
	public boolean isValid(CheckRule rule, Object... arg) {
		return false;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	/**
	 * 取值，有些需要替换
	 * @param value
	 * @return
	 */
	protected String getValue(String value){
		if(value.indexOf(",")>-1){
			return value.replaceAll(",", "");
		}
		return value;
	}
	/**
	 * 取值，有些需要替换
	 * @param value
	 * @return
	 */
	protected String getValue(Object value){
		return getValue(String.valueOf(value));
	}
	
	/**
	 * 从map中取值，不区分大小写
	 * @param map
	 * @param key
	 * @return
	 */
	protected String getValue(Map<String, Object> map,String key){
		//处理包含,的情况，转换成数字
		return getValue(GlobalUtil.getIgnoreCaseValue(map, key));
	}
	
	/**
	 * 从map中取值，为null取默认值defaultValue
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String getValue(Map<String, Object> map,String key,String defaultValue){
		return GlobalUtil.getIgnoreCaseValue(map, key).equals("") ? defaultValue : GlobalUtil.getIgnoreCaseValue(map, key);
	}
 
	/**
	 * 从map中取值，为null取默认值defaultValue
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String getStringValue(Map<String, Object> map,String key,String defaultValue){
		String s = GlobalUtil.getIgnoreCaseValue(map, key);
		if(s.equals("")){
			return defaultValue;
		}else if(s.indexOf(" 00:00:00.0")!=-1){ //截断日期类型
			return s.split(" ")[0];
		}
		return s;
	}
	
	/**
	 * 公共参数替换,放在第1位
	 * @param formula
	 * @return
	 */
	protected String commonFormat(String formula){
//		for (Entry<String, Object> entry : context.entrySet()) {
//			if(formula.indexOf("[@"+entry.getKey()+"]")>-1){
//				formula = CommonUtil.replace(formula ,"[@"+entry.getKey()+"]", getValue(entry.getValue()));
//			}
//		}
		
		List<String> a_param = GlobalUtil.getParams(formula,"[@", "]");
		for (String str : a_param) {
			if(GlobalUtil.containsKeyIgnoreCase(context, str)){
				formula = GlobalUtil.replace(formula, "[@"+str+"]",  getValue(context,str));
			}
		}
		
		return formula;
	}
	
	/**
	 * 替换公式中的变量
	 * 以下注意要取默认值
	 * @param formula
	 * @param map
	 * @return
	 */
	protected String formatAllVars(String formula,Map<String, Object> map,String defaultValue){
		//类型变量替换
		formula = formatDVars(formula, map, defaultValue);
		formula = formatBVars(formula, map, defaultValue);
		formula = formatCVars(formula, map, defaultValue);
		formula = formatNVars(formula, map, defaultValue);
		
		//替换普通变量[@...],默认值是null
		formula = formatVars(formula, map, defaultValue);
		
		//替换参数变量
		formula = formatPVars(formula, map, defaultValue);
		return formula;
	}
	
	//替换日期[D#...]
	protected String formatDVars(String formula,Map<String, Object> map,String defaultValue){
		List<String> d_param = GlobalUtil.getParams(formula,"[D#", "]");
		for (String str : d_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[D#"+str+"]", formatDate(getValue(map,str)));
			}
		}
		return formula;
	}
	//替换boolean[B#...],取不到值默认false
	protected String formatBVars(String formula,Map<String, Object> map,String defaultValue){
		List<String> b_param = GlobalUtil.getParams(formula,"[B#", "]");
		for (String str : b_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[B#"+str+"]", map.get(str)==null ? "false" : getValue(map,str));
			}
		}
		return formula;
	}
	//替换字符[C#...]
	protected String formatCVars(String formula,Map<String, Object> map,String defaultValue){
		List<String> c_param = GlobalUtil.getParams(formula,"[C#", "]");
		for (String str : c_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[C#"+str+"]", getValue(map,str,defaultValue));
			}
		}
		return formula;
	}
	//替换数字[N#...],默认值取 0
	protected String formatNVars(String formula,Map<String, Object> map,String defaultValue){
		List<String> n_param = GlobalUtil.getParams(formula,"[N#", "]");
		for (String str : n_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[N#"+str+"]", map.get(str) == null ? "0" : getValue(map,str));
			}
		}
		return formula;
	}
	
	//替换普通变量[@...],默认值是null
	protected String formatVars(String formula,Map<String, Object> map,String defaultValue){
		//以下注意要取默认值
		List<String> a_param = GlobalUtil.getParams(formula,"[@", "]");
		for (String str : a_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[@"+str+"]",  getStringValue(map,str,defaultValue));
			}
		}
		return formula;
	}
	
	//替换参数[$...]，目前参数没有单独维护功能，直接替换
	protected String formatPVars(String formula,Map<String, Object> map,String defaultValue){
		List<String> p_param = GlobalUtil.getParams(formula,"[$", "]");
		for (String str : p_param) {
			if(GlobalUtil.containsKeyIgnoreCase(map, str)){
				formula = GlobalUtil.replace(formula, "[$"+str+"]",  getValue(map,str,defaultValue));
			}
		}
		return formula;
	}
	
	
	/**
	 * 简单判断公式结构有异常
	 * @param str
	 * @return
	 */
	public int hasError(String str){
//		String[] v = {"[@","[$","[D#","[B#","[C#","[N#"};
//		for (String s : v) {
//			if(str.indexOf(s)>-1){
//				return true;
//			}
//		}
		return -1;
	}
	
	/**
	 * 以后备用，格式化日期
	 * @param date
	 */
	protected String formatDate(String date){
		return date.indexOf(" 00:00:00.0")!=-1 ? date.split(" ")[0] : date;
	}
	
	/**
	 * 获取double值
	 * @param map
	 * @param key
	 * @return
	 */
	protected double getDoubleValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		if (obj == null)
			obj = map.get(key.toUpperCase());
		
		return Double.parseDouble(String.valueOf(obj));
	}
	/**
	 * 获取int值
	 * @param map
	 * @param key
	 * @return
	 */
	protected int getIntValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		if (obj == null)
			obj = map.get(key.toUpperCase());
		
		return null == obj ? 0 :Integer.parseInt(obj.toString());
	}
}
