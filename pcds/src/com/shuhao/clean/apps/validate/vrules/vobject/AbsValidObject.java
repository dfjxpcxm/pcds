package com.shuhao.clean.apps.validate.vrules.vobject;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.IValidObject;
import com.shuhao.clean.apps.validate.vrules.IValidRule;
import com.shuhao.clean.apps.validate.vrules.ValidatorFactory;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;

/**
 * 
 * @Description:   校验对象顶级父类
 * <br>
 * 校验位置：
 * 1。页面上校验
 * 2。数据库校验
 * 
 * @author:         gongzhiyang
 */
public abstract class AbsValidObject implements IValidObject {
	
	protected static final Logger logger = Logger.getLogger(AbsValidObject.class);
	protected UppMetadata meta; //元数据
	protected Map<String, Object> context;//通用参数
	protected List<CheckRule> ckRules; //规则
	
	/**
	 * 执行单个规则校验
	 * @param rule
	 * @param value
	 * @return
	 */
	protected ValidResult doCheck(CheckRule rule,Object ... value) {
		IValidRule vrule = ValidatorFactory.lookupVRule(rule.getChk_method_code());
		if(context!=null){
			vrule.setContext(context);
		}
		return vrule.validate(rule, value);
	}

	public List<CheckRule> getCkRules() {
		return ckRules;
	}

	public void setCkRules(List<CheckRule> ckRules) {
		this.ckRules = ckRules;
	}
	
	public UppMetadata getMeta() {
		return meta;
	}

	public void setMeta(UppMetadata meta) {
		this.meta = meta;
	}
	
	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	/**
	 * 转换为小写
	 * @param str
	 * @return
	 */
	protected String toLowerCase(String str){
		return str.toLowerCase();
	}

	/**
	 * 执行校验
	 * @return
	 */
	public abstract List<IMessage> doCheck();
}
