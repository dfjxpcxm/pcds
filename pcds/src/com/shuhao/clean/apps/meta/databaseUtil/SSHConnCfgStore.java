package com.shuhao.clean.apps.meta.databaseUtil;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * SSH连接信息缓存 单例
 * @author cognos
 */
public class SSHConnCfgStore extends AbstractDataStore<SSHConnCfg>{
	
	private static SSHConnCfgStore sshConnCfgStore;
	
	private SSHConnCfgStore(DataSource dataSource,String storeDesc) throws Exception{
		super(dataSource,storeDesc);
	}
	
	public static SSHConnCfgStore getInstance(DataSource dataSource,String storeDesc) throws Exception {
		if(sshConnCfgStore == null) {
			sshConnCfgStore = new SSHConnCfgStore(dataSource,storeDesc);
		}
		return sshConnCfgStore;
	}

	@Override
	protected void load() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = " SELECT T1.CONN_ID," +
				     " T1.CONFIG_DATE," +
				     " T1.ENCODING," +
				     " T1.HOSTNAME," +
				     " T1.USERNAME," +
				     " T1.PWD" +
					 " FROM JK_PUB_MOD_CONN t1" +
					 " WHERE T1.CONN_TYPE = '2'";
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> dataList = jdbcTemplate.queryForList(sql);
		
		for(int i=0;i<dataList.size();i++) {
			Map<String, Object> record = dataList.get(i);
			SSHConnCfg sshConfig = new SSHConnCfg();
			/*sshConfig.setConnId(parse2String(record.get("CONN_ID")));
			sshConfig.setConfigDate(parse2Date(record.get("CONFIG_DATE")));
			sshConfig.setEncoding(parse2String(record.get("ENCODING")));
			sshConfig.setHostName(parse2String(record.get("HOSTNAME")));
			sshConfig.setUserName(parse2String(record.get("USERNAME")));
			sshConfig.setPassword(parse2String(record.get("PWD")));*/
			this.recordMap.put(sshConfig.getKey(), sshConfig);
		}
	}
}
