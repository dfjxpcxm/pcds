package com.shuhao.clean.apps.meta.databaseUtil;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

/**
 * 用于产生连接池和JdbcTemplate和transactionTemplate的工厂<br />单例
 * @author qisheng
 */
public class JdbcFactory {
	
	private Logger logger = Logger.getLogger(JdbcFactory.class);
	private static JdbcFactory jdbcFactory;
	private DbConnCfgStore dbConnCfgStore;
	private Map<Object,DataSource> dataSources= new HashMap<Object,DataSource>();
	
	private JdbcFactory(){
	}
	
	/**
	 * 如果是初始化，调用此方法，目前为了防止启动异常，在spring中初始化并且初始化时不初始化连接池.
	 * <br>
	 * 获取实例请使用 ： JdbcFactory.getInstance() 
	 * @param dbConnCfgStore
	 * @return
	 * @throws Exception
	 */
	public static JdbcFactory init(DbConnCfgStore dbConnCfgStore) throws Exception {
		if(jdbcFactory==null) {
			jdbcFactory = new JdbcFactory();
			jdbcFactory.dbConnCfgStore = dbConnCfgStore;
//			jdbcFactory.createDataSources();
//			jdbcFactory.logger.info("所有连接池建立完毕，总共建立" + jdbcFactory.dataSources.size() + "个....");
			jdbcFactory.logger.info("成功创建jdbcFactory对象...");
		}
		return jdbcFactory;
	}
	
	/**
	 * 获取jdbcFactory调用此方法
	 * @return
	 * @throws Exception
	 */
	public static JdbcFactory getInstance() throws Exception{
		if(jdbcFactory.dataSources.isEmpty()){
			jdbcFactory.createDataSources();
		}
		return jdbcFactory;
	}
	
	/**
	 * 建立连接池
	 * @throws PropertyVetoException 
	 */
	public void createDataSources() throws Exception {
		//加载连接配置缓存,初始化
		if(dbConnCfgStore.getDataStoreMap().isEmpty()){
			dbConnCfgStore.load();
		}
		Map<Object, DqmConnCfg> dbConnCfgMap = dbConnCfgStore.getDataStoreMap();
		for(Entry<Object, DqmConnCfg> dbConnCfgEntry:dbConnCfgMap.entrySet()) {
			DqmConnCfg dbConnCfg = dbConnCfgEntry.getValue();
			DataSource cpds = createDataSource(dbConnCfg);
			dataSources.put(dbConnCfgEntry.getKey(), cpds);
			logger.info("成功建立到" + dbConnCfg.getHostName() + "/" + dbConnCfg.getConnId() + "的连接池到连接池缓存....");
		}
	}
	
	
	/**
	 * 重新建立所有连接池
	 * @throws PropertyVetoException 
	 */
	public void recreateAllDataSources() throws Exception {
		for(Entry<Object,DataSource> entry : dataSources.entrySet()) {
			closeDataSource(entry.getValue());
		}
		logger.info("关闭所有数据库连接池");
		this.dataSources.clear();
		logger.info("清空所有数据库连接池缓存");
		createDataSources();
		logger.info("数据库连接池重新加载完毕,共建立" + dataSources.size() + "连接池");
	}
	
	/**
	 * 增加一个连接池
	 * @param dbConnCfg
	 * @throws PropertyVetoException
	 */
	public void addDataSource(DqmConnCfg dbConnCfg) throws Exception {
		dataSources.put(dbConnCfg.getConnId(), createDataSource(dbConnCfg));
		logger.info("增加到" +  dbConnCfg.getHostName() + "/" + dbConnCfg.getConnId()+ "的连接池到连接池缓存....");
	}
	
	/**
	 * 重新建立一个连接池
	 * @param dbConnCfg
	 * @throws PropertyVetoException
	 * @throws SQLException 
	 */
	public void recreateDataSource(DqmConnCfg dbConnCfg) throws Exception {
		DataSource dataSource = dataSources.get(dbConnCfg.getConnId());
		closeDataSource(dataSource);
		dataSources.remove(dbConnCfg.getConnId());
		dataSources.put(dbConnCfg.getConnId(), createDataSource(dbConnCfg));
		logger.info("重新建立到" +  dbConnCfg.getHostName() + "/" + dbConnCfg.getConnId() + "的连接池....");
	}
	
	/**
	 * 删除一个连接池
	 * @throws SQLException 
	 */
	public void deleteDataSources(DqmConnCfg dbConnCfg) throws SQLException {
		DataSource dataSource = dataSources.get(dbConnCfg.getConnId());
		closeDataSource(dataSource);
		dataSources.remove(dbConnCfg.getConnId());
		logger.info("删除到" +  dbConnCfg.getHostName() + "/" + dbConnCfg.getConnId() + "的连接池....");
	}
	
	/**
	 * 按照数据库连接配置dbConnCfg创建JdbcTemlate
	 * @param dbConnCfg 数据库连接配置
	 * @return
	 */
	public JdbcTemplate createJdbcTemplate(DqmConnCfg dqmConnCfg) {
		DataSource dataSource = dataSources.get(dqmConnCfg.getConnId());
		if(dataSource==null) {
			throw new RuntimeException("连接代码" + dqmConnCfg.getConnId() + "数据源缓存中未找到!");
		}
		return new JdbcTemplate(dataSource);
	}
	
	/**
	 * 按照数据库连接配置dbConnCfg创建编程式事物处理模板TransactionTemplate
	 * @param dbConnCfg 数据库连接配置
	 * @return
	 */
	public TransactionTemplate createTransactionTemplate(DqmConnCfg dbConnCfg) {
		DataSource dataSource = dataSources.get(dbConnCfg.getConnId());
		if(dataSource==null) {
			throw new RuntimeException("连接代码" + dbConnCfg.getConnId() + "数据源缓存中未找到!");
		}
		return new TransactionTemplate(new DataSourceTransactionManager(dataSource));
	}

	/**
	 * 获取缓存中的所有连接池（Map）
	 * @return
	 */
	public Map<Object, DataSource> getDataSources() {
		return dataSources;
	}
	
	/**
	 * 按照dbConnCfg 查找对应数据库连接池
	 * @param dbConnCfg
	 * @return
	 */
	public DataSource getDataSource(DqmConnCfg dqmConnCfg) {
		return dataSources.get(dqmConnCfg.getConnId());
	}
	
	/**
	 * 通过dbConnCfg建立连接池
	 * @param dbConnCfg
	 * @return
	 * @throws PropertyVetoException
	 * @throws SQLException 
	 */
	private DataSource createDataSource(DqmConnCfg dbConnCfg ) throws Exception{
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(GlobalConstant.C3P0_ORACLE_DRIVER);  //驱动类
		cpds.setJdbcUrl(dbConnCfg.getConn_url());//数据库连接Url
		cpds.setUser(dbConnCfg.getUserName()); // 用户名
		cpds.setPassword(dbConnCfg.getPassword()); // 密码
		cpds.setMinPoolSize(GlobalConstant.C3P0_MIN_POOL_SIZE); //最小连接数
		cpds.setMaxPoolSize(GlobalConstant.C3P0_MAX_POOL_SIZE); //最大连接数
		cpds.setAcquireIncrement(GlobalConstant.C3P0_ACQUIRE_INCREMENT); //连接增加步长
		cpds.setInitialPoolSize(GlobalConstant.C3P0_INITIAL_POOLSIZE);//初始化连接数
		cpds.setMaxIdleTime(GlobalConstant.C3P0_MAX_IDLE_TIME);//最大闲置时间 秒
		cpds.setAcquireRetryAttempts(GlobalConstant.C3P0_ACQUIRE_RETRY_ATTEMPTS);//接失败后重复尝试的次数
		cpds.setAcquireRetryDelay(GlobalConstant.C3P0_ACQUIRE_RETRY_DELAY);//两次连接中间隔时间，单位毫秒
		
/*		//尝试获取一条连接，促使c3p0连接池初始化
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (Exception e) {
			logger.error("初始化连接池失败,connid:" + dbConnCfg.getConnId() + ",message:" + e.getMessage());
			throw e;
		} finally {
			if(conn != null) {
				conn.close();
			}
		}*/
		return cpds;
	}
	
	/**
	 * 关闭连接池
	 * @param dataSource 连接池
	 * @throws SQLException
	 */
	public void closeDataSource(DataSource dataSource) throws SQLException {
		if(dataSource instanceof PooledDataSource ) {
			PooledDataSource pooledDataSource = (PooledDataSource) dataSource;
			pooledDataSource.close();
		}
	}
}
