package com.shuhao.clean.apps.validate.vrules;

import java.util.List;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;

/**
 * 
 * @Description:   校验对象顶级父类
 * 
 * @author:         gongzhiyang
 */
public interface IValidObject {

	/**
	 * 执行校验
	 * @return
	 */
	public List<IMessage> doCheck();
	/**
	 * 设置校验规则
	 * @param ckRules
	 */
	public void setCkRules(List<CheckRule> ckRules) ;
	/**
	 * 返回类型描述
	 * @return
	 */
	public String getType();
}
