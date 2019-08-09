package com.shuhao.clean.apps.meta.databaseUtil;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shuhao.clean.utils.GlobalUtil;


/**
 *  数据库连接信息缓存 单例
 * @author qisheng
 */
public class DbConnCfgStore extends AbstractDataStore<DqmConnCfg>{
	
	private static DbConnCfgStore dbConnCfgStore;
	
	/**
	 * 设置数据源，缓存描述， 初始化缓存
	 * @param dataSource
	 * @param storeDesc
	 * @throws Exception 
	 */
	private DbConnCfgStore(DataSource dataSource,String storeDesc) throws Exception {
		super(dataSource,storeDesc);//设置数据源
	}
	
	/**
	 * Spring 注入数据源并 初始化缓存
	 * @param dataSource 数据源
	 */
	public static DbConnCfgStore getInstance(DataSource dataSource,String storeDesc) throws Exception {
		if(dbConnCfgStore==null){
			dbConnCfgStore = new DbConnCfgStore(dataSource,storeDesc);
		}
		return dbConnCfgStore;
	}
 
	public void load(){
		JdbcTemplate jdbcTempate = new JdbcTemplate(dataSource);
		String sql = "select database_id,"
		      +" database_name,"
		      +" database_type_id,"
		      +" database_desc,"
		      +" username,"
		      +" password,"
		      +" database_ip,"
		      +" display_order,"
		      +" conn_url,"
		      +" is_display,"
		      +" config_date,"
		      +" encoding,"
		      +" access_post,"
		      +" conn_type"
		      +" from upp_database where database_type_id='01'";
		
		List<Map<String,Object>> dataList = jdbcTempate.queryForList(sql);
		dataList = GlobalUtil.lowercaseListMapKey(dataList);
		for(Map<String,Object> record:dataList) {
			DqmConnCfg dqmConnCfg = new DqmConnCfg();
			dqmConnCfg.setDatabase_id(GlobalUtil.getStringValue(record, "database_id"));
			dqmConnCfg.setConnId(GlobalUtil.getStringValue(record, "database_id"));
			dqmConnCfg.setDatabase_name(GlobalUtil.getStringValue(record, "database_name"));
			dqmConnCfg.setDatabase_type_code(GlobalUtil.getStringValue(record, "database_type_id"));
			dqmConnCfg.setDatabase_desc(GlobalUtil.getStringValue(record, "database_desc"));
			dqmConnCfg.setHostName(GlobalUtil.getStringValue(record, "database_ip"));
			dqmConnCfg.setConn_url(GlobalUtil.getStringValue(record, "conn_url"));
			dqmConnCfg.setEncoding(GlobalUtil.getStringValue(record, "encoding"));
			dqmConnCfg.setConnType(GlobalUtil.getStringValue(record, "conn_type"));
			dqmConnCfg.setUserName(GlobalUtil.getStringValue(record, "username"));
			dqmConnCfg.setPassword(GlobalUtil.getStringValue(record, "password"));
			dqmConnCfg.setAccessPort(GlobalUtil.getStringValue(record, "access_post"));
			this.recordMap.put(record.get("database_id"), dqmConnCfg);
		}
		
	}

	/**
	 * 刷新缓存后，刷新JdbcFactory中的连接池
	 * @throws Exception 
	 */
	@Override
	public void reloadCallback() throws Exception {
		JdbcFactory jdbcFactory = (JdbcFactory)this.beanFactory.getBean("jdbcFactory");
		jdbcFactory.recreateAllDataSources();
	}
	
	/**
	 * 增加一条缓存后，增加JdbcFactory一个连接池
	 */
	@Override
	public void addRecordCallback(DqmConnCfg dbConnCfg) throws Exception {
		JdbcFactory jdbcFactory = (JdbcFactory)this.beanFactory.getBean("jdbcFactory");
		jdbcFactory.addDataSource(dbConnCfg);
	}

	/**
	 * 删除一条缓存后，删除JdbcFactory对应连接池
	 */
	@Override
	public void deleteRecordCallback(DqmConnCfg dbConnCfg) throws Exception {
		JdbcFactory jdbcFactory = (JdbcFactory)this.beanFactory.getBean("jdbcFactory");
		jdbcFactory.deleteDataSources(dbConnCfg);
	}

	/**
	 * 更新一条缓存后，重新建立JdbcFactory中对应的连接池
	 */
	@Override
	public void updateRecordCallback(DqmConnCfg dbConnCfg) throws Exception {
		JdbcFactory jdbcFactory = (JdbcFactory)this.beanFactory.getBean("jdbcFactory");
		jdbcFactory.recreateDataSource(dbConnCfg);
	}

}
