package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 数据库实体对象
 * 
 * @author bixb
 * 
 */
public class UppDatabase extends UppMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String database_id;
	private String database_type_cd;
	private String database_name;
	private String database_desc;
	private String server_addr;
	private String connect_str;
	private String char_encoding;
	private String access_port;
	private String test_user_name;
	private String test_user_password;
	
	public String getMetadata_id() {
		return database_id;
	}
	
	public String getMetadata_name() {
		return database_name;
	}
	
	public String getMetadata_desc() {
		return database_name;
	}
	
	public String getDatabase_id() {
		return database_id;
	}

	public void setDatabase_id(String databaseId) {
		database_id = databaseId;
	}

	public String getDatabase_type_cd() {
		return database_type_cd;
	}

	public void setDatabase_type_cd(String databaseTypeCd) {
		database_type_cd = databaseTypeCd;
	}

	public String getDatabase_name() {
		return database_name;
	}

	public void setDatabase_name(String databaseName) {
		database_name = databaseName;
	}

	public String getDatabase_desc() {
		return database_desc;
	}

	public void setDatabase_desc(String databaseDesc) {
		database_desc = databaseDesc;
	}

	public String getServer_addr() {
		return server_addr;
	}

	public void setServer_addr(String serverAddr) {
		server_addr = serverAddr;
	}

	public String getConnect_str() {
		return connect_str;
	}

	public void setConnect_str(String connectStr) {
		connect_str = connectStr;
	}

	public String getChar_encoding() {
		return char_encoding;
	}

	public void setChar_encoding(String charEncoding) {
		char_encoding = charEncoding;
	}

	public String getAccess_port() {
		return access_port;
	}

	public void setAccess_port(String accessPort) {
		access_port = accessPort;
	}

	public String getTest_user_name() {
		return test_user_name;
	}

	public void setTest_user_name(String testUserName) {
		test_user_name = testUserName;
	}

	public String getTest_user_password() {
		return test_user_password;
	}

	public void setTest_user_password(String testUserPassword) {
		test_user_password = testUserPassword;
	}

}
