package com.shuhao.clean.apps.sys.entity;

import java.io.Serializable;

import com.rx.security.domain.IUser;

/**
 * 用户实体对象
 * @author chenxd
 *
 */
public class SysUserInfo implements IUser,Serializable{
	private static final long serialVersionUID = 6335059669947870693L;

	private String user_id;
	private String password;
	private String user_name;
	private String bank_org_id;
	private String owner_org_id;
	private String id_card;
	private String gender_code;
	private String telephone;
	private String email;
	private String address;
	private String postal;
	private String status_code;
	private String begin_date;
	private String end_date;
	private String busi_line_id;
	//===========
	/*public String busi_line_code;*/
	private String job_title_code;
	private String bank_org_name;
	private String owner_org_name;
	
	private String job_seq_code;
	private String technical_title_code;
	private String ethnicity_code;
	private String join_date;
	private String employee_id;
	private String leave_date;
	private String born_date;
	private String edu_back_code;
	private String post_type_code;
	private String remark;
	
	private String khdx_role_id;
	
	
	public String getEthnicity_code() {
		return ethnicity_code;
	}
	public void setEthnicity_code(String ethnicity_code) {
		this.ethnicity_code = ethnicity_code;
	}
	public String getTechnical_title_code() {
		return technical_title_code;
	}
	public void setTechnical_title_code(String technical_title_code) {
		this.technical_title_code = technical_title_code;
	}
	public String getJob_seq_code() {
		return job_seq_code;
	}
	public void setJob_seq_code(String job_seq_code) {
		this.job_seq_code = job_seq_code;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getLeave_date() {
		return leave_date;
	}
	public void setLeave_date(String leave_date) {
		this.leave_date = leave_date;
	}
	public String getBorn_date() {
		return born_date;
	}
	public void setBorn_date(String born_date) {
		this.born_date = born_date;
	}
	
	public String getEdu_back_code() {
		return edu_back_code;
	}
	public void setEdu_back_code(String edu_back_code) {
		this.edu_back_code = edu_back_code;
	}
	
	
	public String getPost_type_code() {
		return post_type_code;
	}
	public void setPost_type_code(String post_type_code) {
		this.post_type_code = post_type_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getOwner_org_name() {
		return owner_org_name;
	}
	public void setOwner_org_name(String owner_org_name) {
		this.owner_org_name = owner_org_name;
	}
	public String getBank_org_name() {
		return bank_org_name;
	}
	public void setBank_org_name(String bank_org_name) {
		this.bank_org_name = bank_org_name;
	}
	
	public String getJob_title_code() {
		return job_title_code;
	}
	public void setJob_title_code(String job_title_code) {
		this.job_title_code = job_title_code;
	}
	//====================
	public String getBusi_line_id() {
		return busi_line_id;
	}
	public void setBusi_line_id(String busi_line_id) {
		this.busi_line_id = busi_line_id;
	}

	
	public String getUser_id() {
		return user_id;
	}
	//=======================
	/*public String getBusi_line_code() {
		return busi_line_code;
	}
	public void setBusi_line_code(String busi_line_code) {
		this.busi_line_code = busi_line_code;
	}*/
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getBank_org_id() {
		return bank_org_id;
	}
	public void setBank_org_id(String bank_org_id) {
		this.bank_org_id = bank_org_id;
	}
	public String getOwner_org_id() {
		return owner_org_id;
	}
	public void setOwner_org_id(String owner_org_id) {
		this.owner_org_id = owner_org_id;
	}
	
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getGender_code() {
		return gender_code;
	}
	public void setGender_code(String gender_code) {
		this.gender_code = gender_code;
	}
	
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getBankOrgID() {
		return this.bank_org_id;
	}
	public String[] getRoles() {
		return null;
	}
	public String getUserID() {
		return this.user_id;
	}
	public String getUserName() {
		return this.user_name;
	}
	public String getKhdx_role_id() {
		return khdx_role_id;
	}
	public void setKhdx_role_id(String khdx_role_id) {
		this.khdx_role_id = khdx_role_id;
	}
	
	
}
