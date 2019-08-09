package com.shuhao.clean.apps.validate.entity;

import java.io.Serializable;

/**
 * 
 * @Description:   upp_check_rule 校验规则, 所有元数据的校验规则都要转换为这个对应
 * <br>
 * 注意：校验使用这个类
 * 
 * @author:         gongzhiyang
 */
public class CheckRule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2617244191667098781L;
	
	private String metadata_id;//元数据ID
	
	private String chk_rule_code; //规则代码
	private String chk_rule_name;//规则名字
	private String chk_rule_desc;//规则描述
	private String chk_type_code; //规则类型
	private String chk_method_code;//规则方法
	private String rule_type_flag;//校验规则类型标记
	private String chk_formula;//校验公式
	private String chk_formula_desc;//校验公式描述
	private String chk_failure_tip;//校验失败提示
	private String chk_priority;//优先级
	private String status_cd;//状态
	
	public String getChk_rule_name() {
		return chk_rule_name;
	}
	public void setChk_rule_name(String chk_rule_name) {
		this.chk_rule_name = chk_rule_name;
	}
	public String getChk_rule_desc() {
		return chk_rule_desc;
	}
	public void setChk_rule_desc(String chk_rule_desc) {
		this.chk_rule_desc = chk_rule_desc;
	}
	
	public String getChk_rule_code() {
		return chk_rule_code;
	}
	public void setChk_rule_code(String chk_rule_code) {
		this.chk_rule_code = chk_rule_code;
	}
	public String getChk_type_code() {
		return chk_type_code;
	}
	public void setChk_type_code(String chk_type_code) {
		this.chk_type_code = chk_type_code;
	}
	public String getChk_method_code() {
		return chk_method_code;
	}
	public void setChk_method_code(String chk_method_code) {
		this.chk_method_code = chk_method_code;
	}
	public String getRule_type_flag() {
		return rule_type_flag;
	}
	public void setRule_type_flag(String rule_type_flag) {
		this.rule_type_flag = rule_type_flag;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getChk_formula() {
		return chk_formula;
	}
	public void setChk_formula(String chk_formula) {
		this.chk_formula = chk_formula;
	}
	public String getChk_formula_desc() {
		return chk_formula_desc;
	}
	public void setChk_formula_desc(String chk_formula_desc) {
		this.chk_formula_desc = chk_formula_desc;
	}
	public String getChk_priority() {
		return chk_priority;
	}
	public void setChk_priority(String chk_priority) {
		this.chk_priority = chk_priority;
	}
	public String getChk_failure_tip() {
		return chk_failure_tip;
	}
	public void setChk_failure_tip(String chk_failure_tip) {
		this.chk_failure_tip = chk_failure_tip;
	}
	public String getMetadata_id() {
		return metadata_id;
	}
	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}
}
