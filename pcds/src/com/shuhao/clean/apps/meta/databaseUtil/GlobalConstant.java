package com.shuhao.clean.apps.meta.databaseUtil;
/**
 * 定义本系统全局变量、系统加载项
 * @author gongzhiyang
 *
 */
public class GlobalConstant {

	/************* c3p0连接池默认属性配置 *****************/
	public static final int C3P0_MIN_POOL_SIZE = 3; //最小连接数
	public static final int C3P0_ACQUIRE_INCREMENT = 3;//增加连接步长
	public static final int C3P0_MAX_POOL_SIZE = 30;//最大连接数
	public static final int C3P0_INITIAL_POOLSIZE = 3;//初始化连接数
	public static final int C3P0_MAX_IDLE_TIME = 120;//最大闲置时间 秒
	public static final int C3P0_MAX_STATEMENTS = 100;//最大闲置时间 秒
	public static final int C3P0_ACQUIRE_RETRY_ATTEMPTS = 1;//接失败后重复尝试的次数
	public static final int C3P0_ACQUIRE_RETRY_DELAY = 1000;//两次连接中间隔时间，单位毫秒
	
	public static String C3P0_ORACLE_DRIVER="oracle.jdbc.driver.OracleDriver";
	public static String C3P0_DB2_DRIVER="com.ibm.db2.jdbc.app.DB2Driver";
	public static String C3P0_INFORMIX_DIRVER="com.informix.jdbc.IfxDriver";
	public static String C3P0_SQLSERVER_DRIVER="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public static String C3P0_AS400DB2="com.ibm.as400.access.AS400JDBCDriver";
	
	//数据源在spring中的bean ID
	public static final String SPRING_DATASOURCE_ID = "dataSource";
	
	//连接方式代码
	public static final String CONNN_TYPE_DATABASE = "1";
	public static final String CONNN_TYPE_SSH = "2";
	
	//定点执行 超时时间 6小时
	public static Integer Trigger_TIMEPOIT_TIMEOUT = 6*3600;
	
	//jobDetial name前缀
	public static final String MOD_PREFIX = "mod-";
	public static final String TASK_PREFIX = "task-";
	public static final String TASK_GORUP_PREFIX = "taskGroup-";
	public static final String SYS_SP_FREQUENCY = "sysSp-";
	
	//jobDetial 组/组前缀
	public static final String GROUP_MOD_KIND_PREFIX = "modKind-";
	public static final String GROUP_SYS_SP_FREQUENCY = "sysSp";
	
	//task执行sql超时时间(s)
	public static final int TASK_QUERY_TIMEOUT = 60*20;
	
	//SSH 同一条连接的最大session数目
	public static final Integer SSH_MAX_SESSION_COUNT = 8;
	//SSH执行命令超时时间(ms)
	public static final long SSH_CMD_TIMEOUT = 60*1000;
}
