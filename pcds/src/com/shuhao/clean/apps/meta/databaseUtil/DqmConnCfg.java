package com.shuhao.clean.apps.meta.databaseUtil;



public class DqmConnCfg extends ConnCfg{
	private String instanceName;					//实例名称
	private String database_id;                            //数据库的id                                       
	private String database_type_code;               // 数据库类型代码                                   
	private String database_name ;                   //数据库名称                                       
	private String database_desc;                   //数据库描述                                       
	private String conn_url;                        //数据库连接串                                     
	public String getDatabase_id() {
		return database_id;
	}
	public void setDatabase_id(String database_id) {
		this.database_id = database_id;
	}
	public String getDatabase_type_code() {
		return database_type_code;
	}
	public void setDatabase_type_code(String database_type_code) {
		this.database_type_code = database_type_code;
	}
	public String getDatabase_name() {
		return database_name;
	}
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}
	public String getDatabase_desc() {
		return database_desc;
	}
	public void setDatabase_desc(String database_desc) {
		this.database_desc = database_desc;
	}
	public String getConn_url() {
		return conn_url;
	}
	public void setConn_url(String conn_url) {
		this.conn_url = conn_url;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	
}
