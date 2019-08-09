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
import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

public class AddFunction extends AbstractFunction {
	
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1,
			AviatorObject arg2) {
		Number left = FunctionUtils.getNumberValue(arg1, env);
		Number right = FunctionUtils.getNumberValue(arg2, env);
		return new AviatorDouble(left.doubleValue() + right.doubleValue());
	}

	public String getName() {
		return "add";
	}
}