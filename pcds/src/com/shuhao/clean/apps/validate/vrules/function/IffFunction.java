/**
 * FileName:     AddFunction.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-1-19 下午7:11:23 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-1-19       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.function;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
/**
 * iff(表达式 , number类型或返回number的表达式,  number类型或返回number的表达式) 条件函数
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class IffFunction extends AbstractFunction {
	
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1,
			AviatorObject arg2,AviatorObject arg3) {
		if(arg1.booleanValue(env)){
			return new AviatorDouble(FunctionUtils.getNumberValue(arg2, env));
		}else{
			return new AviatorDouble(FunctionUtils.getNumberValue(arg3, env));
		}
	}

	public String getName() {
		return "iff";
	}
	
	public static void main(String[] args) {
		AviatorEvaluator.addFunction(new IffFunction());
		Map<String, Object> env = new HashMap<String, Object>();
		env.put("__self_val",null);
		System.out.println(AviatorEvaluator.execute("iff( __self_val == nil,iff(5>1,1,0) ,2)",env));
	}
}