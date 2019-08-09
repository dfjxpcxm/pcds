package com.shuhao.clean.apps.meta.databaseUtil;

import java.util.Date;


public abstract class ConnCfg extends AbstractStoreRecord {

	protected String connId;//连接代码
	protected String hostName;//主机名称
	protected String accessPort;//端口号
	protected String encoding;//字符编码
	protected String userName;//用户名
	protected String password;//密码
	protected Date configDate;//配置日期
	protected String connType;//连接方式

	public ConnCfg() {
		super();
	}

	public String getConnId() {
		return connId;
	}

	public void setConnId(String connId) {
		this.connId = connId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAccessPort() {
		return accessPort;
	}

	public void setAccessPort(String accessPort) {
		this.accessPort = accessPort;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getConfigDate() {
		return configDate;
	}

	public void setConfigDate(Date configDate) {
		this.configDate = configDate;
	}

	@Override
	public Object getKey() {
		return this.connId;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

}