package com.shuhao.clean.apps.validate.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;
/**
 * 
 * @Description:   校验规则库实体类
 *
 * @author Ning
 *
 */
public class UppCheckRule implements TreeNode,Serializable {

	private static final long serialVersionUID = 1L;
	private String chk_rule_code;
	private String chk_rule_name;
	private String chk_rule_desc;
	private String chk_type_code;
	private String chk_type_name;
	private String rule_type_flag;
	private String chk_method_code;
	private String chk_method_name;
	private String chk_formula;
	private String chk_formula_desc;
	private String chk_priority;
	private String status_code;
	private String status_name;
	private String create_user_id;
	private String create_time;
	private String update_user_id;
	private String update_time;
	private String display_order;
	private String chk_failure_tip;
	private String chk_action;

	public String getChk_failure_tip() {
		return chk_failure_tip;
	}
	public void setChk_failure_tip(String chkFailureTip) {
		chk_failure_tip = chkFailureTip;
	}
	public String getRule_type_flag() {
		return rule_type_flag;
	}
	public void setRule_type_flag(String ruleTypeFlag) {
		rule_type_flag = ruleTypeFlag;
	}
	public String getChk_type_name() {
		return chk_type_name;
	}
	public void setChk_type_name(String chkTypeName) {
		chk_type_name = chkTypeName;
	}
	public String getChk_method_name() {
		return chk_method_name;
	}
	public void setChk_method_name(String chkMethodName) {
		chk_method_name = chkMethodName;
	}
	
	public String getNodeID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getParentNodeID() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getChk_rule_code() {
		return chk_rule_code;
	}
	public void setChk_rule_code(String chkRuleCode) {
		chk_rule_code = chkRuleCode;
	}
	public String getChk_rule_name() {
		return chk_rule_name;
	}
	public void setChk_rule_name(String chkRuleName) {
		chk_rule_name = chkRuleName;
	}
	public String getChk_rule_desc() {
		return chk_rule_desc;
	}
	public void setChk_rule_desc(String chkRuleDesc) {
		chk_rule_desc = chkRuleDesc;
	}
	public String getChk_type_code() {
		return chk_type_code;
	}
	public void setChk_type_code(String chkTypeCode) {
		chk_type_code = chkTypeCode;
	}
	public String getChk_method_code() {
		return chk_method_code;
	}
	public void setChk_method_code(String chkMethodCode) {
		chk_method_code = chkMethodCode;
	}
	public String getChk_formula() {
		return chk_formula;
	}
	public void setChk_formula(String chkFormula) {
		chk_formula = chkFormula;
	}
	public String getChk_formula_desc() {
		return chk_formula_desc;
	}
	public void setChk_formula_desc(String chkFormulaDesc) {
		chk_formula_desc = chkFormulaDesc;
	}
	public String getChk_priority() {
		return chk_priority;
	}
	public void setChk_priority(String chkPriority) {
		chk_priority = chkPriority;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String statusCode) {
		status_code = statusCode;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String createUserId) {
		create_user_id = createUserId;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String updateUserId) {
		update_user_id = updateUserId;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}
	public String getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(String displayOrder) {
		display_order = displayOrder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String statusName) {
		status_name = statusName;
	}
	public String getChk_action() {
		return chk_action;
	}
	public void setChk_action(String chk_action) {
		this.chk_action = chk_action;
	}
	
}
