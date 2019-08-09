package com.shuhao.clean.apps.validate.vrules.vobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.IValidator;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;
import com.shuhao.clean.apps.validate.vrules.msg.InvalidMessage;
import com.shuhao.clean.apps.validate.vrules.msg.MyMessage;
/**
 * 
 * @Description:   map校验
 * 
 * @author:         gongzhiyang
 */
public class VRow extends AbsValidObject {
	
	private Map<String, Object> value ;
	
	public VRow(UppMetadata meta,Map<String, Object> value){
		this.meta = meta;
		this.value =value;
	}
	
	public Map<String, Object> getValue() {
		return value;
	}

	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.IValidObject#getType()
	 */
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.domain.AbsValidObject#doCheck()
	 */
	public List<IMessage> doCheck() {
		
		List<IMessage> msgs = new ArrayList<IMessage>();
		for (CheckRule rule : this.getCkRules()) {
			ValidResult result = doCheck(rule, value);
			if(result==null){
				msgs.add(new InvalidMessage(getMeta().getFullId(),getMeta().getMetadata_name(),rule.getChk_rule_code()));
				logger.debug("["+getMeta().getFullId()+"]配置的规则["+rule.getChk_rule_code()+"]不存在");
				break;
			}
			else  {
				logger.debug("["+getMeta().getFullId()+"]执行["+rule.getChk_rule_code()+"]["+rule.getChk_rule_name()+"]校验 ["+result.success+"]["+result.getResult()+"]");
				if(!result.success){
					//sql校验失败
					if(rule.getChk_method_code().equals(IValidator.VTYPE_SQL)){
						msgs.add(new MyMessage(getMeta().getFullId(),getMeta().getMetadata_name(),  rule.getChk_failure_tip() + ",["+result.getResult()+"]"));
					}else{
						msgs.add(new MyMessage(getMeta().getFullId(),getMeta().getMetadata_name(),  rule.getChk_failure_tip()));
					}
					break;
				}
			}
		}
		return msgs;
	}
}
