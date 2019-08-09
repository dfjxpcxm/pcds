/**
 * FileName:     TestFn.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-4 下午4:29:47 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-4       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.function;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @Description:   TODO
 * <br>
 * 调用方式:<br>
 * //注册函数
	AviatorEvaluator.addFunction(new TestFn());
	System.out.println(AviatorEvaluator.execute("test(1,2)"));
	System.out.println(AviatorEvaluator.execute("test(add(1,2),100)"));
 * 
 * @author:         gongzhiyang
 */
public class TestFn extends AbstractFunction {
	
	public AviatorObject call(Map<String, Object> env, AviatorObject
			arg1, AviatorObject arg2) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
	 */
	public String getName() {
		return "test";
	}
}
