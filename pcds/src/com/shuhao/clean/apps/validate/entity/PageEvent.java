package com.shuhao.clean.apps.validate.entity;

import java.io.Serializable;

/**
 * 页面事件
 * @author bixb
 * 
 */
public class PageEvent  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String file_id;
	
	private String file_desc;
	
	private String update_time;
	
	private String update_user;
	
	private String event_file;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_desc() {
		return file_desc;
	}

	public void setFile_desc(String file_desc) {
		this.file_desc = file_desc;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getEvent_file() {
		return event_file;
	}

	public void setEvent_file(String event_file) {
		this.event_file = event_file;
	}

	 
}
