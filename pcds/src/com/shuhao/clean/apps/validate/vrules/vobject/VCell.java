package com.shuhao.clean.apps.validate.vrules.vobject;

import java.util.ArrayList;
import java.util.List;

import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;
import com.shuhao.clean.apps.validate.vrules.msg.InvalidMessage;
import com.shuhao.clean.apps.validate.vrules.msg.MyMessage;
/**
 * 
 * @Description:   单值校验
 * <br>
 * 问题：1.校验对象 基本校验 <br>
 * 2.封装校验结果
 * 
 * @author:         gongzhiyang
 */
public class VCell extends AbsValidObject {
	
	private String value;
	
	public VCell(UppMetadata meta,String value){
		this.meta = meta;
		this.value =value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.ValidObject#getType()
	 */
	public String getType() {
		return "单值校验";
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.domain.AbsValidObject#doCheck()
	 */
	public List<IMessage> doCheck() {
		List<IMessage> msgs = new ArrayList<IMessage>();
		for (CheckRule rule : this.getCkRules()) {
			//2.校验器校验
			ValidResult result = doCheck(rule, value);
			if(result==null){
				msgs.add(new InvalidMessage(getMeta().getFullId(),getMeta().getMetadata_name(),rule.getChk_rule_code()));
				logger.debug("["+getMeta().getFullId()+"]配置的规则["+rule.getChk_rule_code()+"]不存在");
				break;
			}
			else {
				logger.debug("["+getMeta().getFullId()+"]执行["+rule.getChk_rule_code()+"]["+rule.getChk_rule_name()+"]校验 ["+result.success+"]["+result.getResult()+"]");
				if(!result.success){
					msgs.add(new MyMessage(getMeta().getFullId(),getMeta().getMetadata_name(), rule.getChk_failure_tip()));
					break;
				}
			}
		}
		return msgs;
	}
	
	
	/**
	 * 一般性，内部校验实现
	 * @return
	 */
	/*public IMessage doBaseCheck(){
		//非空
		boolean isNull = GlobalUtil.isNull(value);
		if(isNull){
			return new SimpleMessage(object.getColumn_id(), "非空");
		}
		
		//校验字段类型
		Regex r = BaseRegexManager.lookRegex(object.getColumn_type_code());
		boolean reValue = r.validate(value);
		if(!reValue){
			return new SimpleMessage(object.getColumn_id(), r.getDesc());
		}
		
		//文本校验长度
		if(object.getColumn_type_code().equals(BaseRegexManager.REGEX_01)){
			//长度,未判断中文
			if( value.length() > Integer.parseInt(object.getData_length())){
				return new SimpleMessage(object.getColumn_id(), "字段长度<="+object.getData_length()); 
			}
		}
		
		//精度
		if(object.getColumn_type_code().equals(BaseRegexManager.REGEX_02)){
			int size =Integer.parseInt(object.getData_scale());
			String regex1 = "^[+\\-]?\\d+(.[0-9]{1,"+size+"})?$";
			if(!Pattern.matches(regex1, value)){
				return new SimpleMessage(object.getColumn_id(), "字段长度<="+object.getData_length()); 
			}
		}
		return null;
	}*/
}
