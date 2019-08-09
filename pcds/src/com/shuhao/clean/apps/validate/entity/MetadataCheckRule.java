package com.shuhao.clean.apps.validate.entity;
/**
 * 
 * @Description:   TODO
 *
 * @author Ning
 *
 */
public class MetadataCheckRule extends UppCheckRule {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -382569615255335541L;
	
	
	private String metadata_id;
	private String old_chk_rule_code;

	public String getOld_chk_rule_code() {
		return old_chk_rule_code;
	}

	public void setOld_chk_rule_code(String oldChkRuleCode) {
		old_chk_rule_code = oldChkRuleCode;
	}

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}
	
}
