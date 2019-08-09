package com.shuhao.clean.apps.meta.service.impl;


import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.shuhao.clean.apps.meta.databaseUtil.DqmConnCfg;
import com.shuhao.clean.apps.meta.databaseUtil.GlobalConstant;
import com.shuhao.clean.apps.meta.databaseUtil.JdbcFactory;
import com.shuhao.clean.apps.meta.databaseUtil.StoreManager;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.service.IOracleDatabaseService;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * oracle类型数据库操作实现类
 * @author Administrator
 *
 */
@SuppressWarnings("unused")
public class OracleDatabaseServiceImpl  implements IOracleDatabaseService {
	
	static Logger logger = Logger.getLogger(OracleDatabaseServiceImpl.class);
	
	//数据库配置信息
	private DqmConnCfg dbf;
	//数据库操作模板
	private JdbcTemplate jdbcTemplate;
	
	public OracleDatabaseServiceImpl(DqmConnCfg dbf){
		this.dbf=dbf;
		try {
			jdbcTemplate=JdbcFactory.getInstance().createJdbcTemplate(dbf);
		} catch (Exception e) {
			logger.error("获取数据库连接信息失败."+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public DataSource getDataSource(){
		return jdbcTemplate.getDataSource();
	}
	
	public static void  createDataSource(UppDatabase database) throws Exception{	
		DqmConnCfg conncfg=new DqmConnCfg();
//		conncfg.setConnId(database.getDatabase_id());
//		conncfg.setAccessPort(database.getAccess_post());
//		conncfg.setConfigDate(new Date());
//		conncfg.setConn_url(database.getConn_url());
//		conncfg.setConnType(database.getConn_type());
//		conncfg.setHostName(database.getDatabase_ip());
//		conncfg.setUserName(database.getUsername());
//		conncfg.setPassword(database.getUsername());
//		conncfg.setEncoding(database.getEncoding());
		conncfg.setInstanceName(database.getDatabase_name());
		DataSource ds=JdbcFactory.getInstance( ).getDataSources().get(database.getDatabase_id());
		if(ds != null){
			StoreManager.getInstance().getDbConnCfgStore().deleteRecord(conncfg);
			StoreManager.getInstance().getDbConnCfgStore().addRecord(conncfg);
			JdbcFactory.getInstance().recreateDataSource(conncfg);
		}
		else{
			StoreManager.getInstance().getDbConnCfgStore().addRecord(conncfg);
			JdbcFactory.getInstance().addDataSource(conncfg);
		}
		
	}
	
	public static void deleteDataSource(UppDatabase badConfigInfo) throws Exception {
		DqmConnCfg dbconnCfg =new DqmConnCfg();
		dbconnCfg.setConnId(badConfigInfo.getDatabase_id());
		dbconnCfg.setHostName(badConfigInfo.getDatabase_name());
		JdbcFactory.getInstance().deleteDataSources(dbconnCfg);
	}
	
	//包含获取数据源方法
	/**
	 * 查询指定数据库下的连接
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryDbLinkList() throws Exception {
		StringBuffer dblinkSql=new StringBuffer();
		dblinkSql.append("select");
		dblinkSql.append(" t.DB_LINK as dblink_name,");
		dblinkSql.append(" '' as  dblink_desc,");
		dblinkSql.append(" '"+dbf.getDatabase_id()+"' as  database_id,"); 
		dblinkSql.append(" rownum  as  display_order,  ");
		dblinkSql.append("'1' as display_flag ");  
		dblinkSql.append(" from");
		dblinkSql.append(" user_db_links t");
		List<Map<String, Object>> dblinkList = queryForList(dblinkSql.toString());
		return dblinkList;
	}

	/**
	 * 查询指定数据库下的用户
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> dataPrivUserList() throws Exception {
		StringBuffer userSql=new StringBuffer();
		userSql.append("select");
		userSql.append(" t.username  as owner_name,");       //数据库下属的用户
		userSql.append("  '' as owner_password,"); 
		userSql.append(" '"+dbf.getDatabase_id()+"' as  database_id,");      //引用的数据库ID，需前台维护
		userSql.append(" '' as owner_desc,");     					//数据库用户描述
		userSql.append(" rownum as display_order,");  				//数据库用户显示顺序；需要前台维护
		userSql.append(" '1' as is_display");    				//数据库用户是否可选；需要前台维护
		userSql.append(" from");
		userSql.append(" all_users t");
		List<Map<String, Object>> dbuserList = queryForList(userSql.toString());
		return dbuserList;
	}

	/**
	 * 查询指定数据库的表空间
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> tableSpaceInfoList() throws Exception {
		StringBuffer tablespaceSql=new StringBuffer();
		tablespaceSql.append("select");
		tablespaceSql.append(" t.tablespace_name  as  tablespace_name,");//表空间
		tablespaceSql.append(" '"+dbf.getDatabase_id()+"' as  database_id,");    //引用的数据库id，需前台维护
		tablespaceSql.append(" '' as  tablespace_desc,");				//表空间描述
		tablespaceSql.append(" rownum as  display_order,");  			//表空间显示顺序；需要前台维护；在此仅展现,下同
		tablespaceSql.append(" '1' as  is_display");    				//表空间是否可选；需要前台维护
		tablespaceSql.append(" from");
		tablespaceSql.append(" user_tablespaces t");
		return queryForList(tablespaceSql.toString());
	}
	
	/**
	 * 查询指定数据库和用户下的表信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTableList(String owner_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" t.table_name as table_name,");							//表名
		sql.append(" '' as table_desc,"); 									//表描述
		sql.append(" '"+dbf.getDatabase_id()+"' as database_id,");			//引用的数据库ID
		sql.append(" t.owner as owner_name,");								//用户名
		sql.append(" t.tablespace_name as tablespace_name,");  				//表空间名称
		//sql.append(" '' as table_style_code,");								//定义表分类代码
		//sql.append(" '' as source_system_code,");							//源系统代码
		sql.append(" '1' as maintain_flag,");								//表数据是否可以维护
		//sql.append(" '' as con_table_type_code,");  						//配置表类型
		//sql.append(" '' as sub_sql,");										//子查询SQL
		sql.append(" case when s.partitioning_type is null then 'N/A'");
		sql.append(" when s.subpartitioning_type!='NONE' then s.partitioning_type || '_' || s.subpartitioning_type");
		sql.append(" else s.partitioning_type end as partition_type_code");	//分区类型
		sql.append(" from all_tables t");
		sql.append(" left join all_part_tables s on t.owner=s.owner and t.table_name=s.table_name");
		if(GlobalUtil.isNotNull(owner_name))
		    sql.append(" where t.owner='"+owner_name+"'");
		sql.append(" order by t.table_name");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表分区信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTablePartitionInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" s.table_name as table_name,");					//表名
		sql.append(" s.partition_name as partition_name,"); 		//分区名称
		sql.append(" '0' as sub_part_flag,");						//主分区标记
		sql.append(" s.tablespace_name as tablespace_name,");		//表空间
		sql.append(" '"+dbf.getDatabase_id()+"' as database_id,");	//引用的数据库ID
		sql.append(" s.partition_position as position");  			//分区位置
		sql.append(" from all_tab_partitions s");
		sql.append(" where s.table_owner='"+owner_name+"'");
		sql.append(" and s.table_name='"+table_name+"'");
		sql.append(" union all");
		sql.append(" select");
		sql.append(" t.table_name as table_name,");					//表名
		sql.append(" t.subpartition_name as partition_name,"); 		//分区名称
		sql.append(" '1' as sub_part_flag,");						//主分区标记
		sql.append(" t.tablespace_name as tablespace_name,");		//表空间
		sql.append(" '"+dbf.getDatabase_id()+"' as database_id,");	//引用的数据库ID
		sql.append(" t.subpartition_position as position"); 		//分区位置
		sql.append(" from all_tab_subpartitions t");
		sql.append(" where t.table_owner='"+owner_name+"'");
		sql.append(" and t.table_name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表字段信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTableFieldInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" s.column_name as field_name,"); 								//列名
		sql.append(" '1' as display_flag,");										//是否显示
		sql.append(" '' as data_type_code,");										//字段类型代码
		sql.append(" s.data_type as data_type,");									//字段类型
		sql.append(" s.data_length as data_length,");  								//数据长度
		sql.append(" s.data_precision as data_precision,");							//数据精度
		sql.append(" s.data_scale as data_scale,"); 								//数据刻度
		sql.append(" case when s.nullable = 'Y' then 0 else 1 end as null_flag,");	//是否可为空
		sql.append(" s.data_default as default_value,");							//默认值
		sql.append(" s.column_id as field_order");									//字段顺序号
		sql.append(" from all_tab_columns s");
		sql.append(" where s.owner='"+owner_name+"'");
		sql.append(" and s.table_name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表约束
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTableConsInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" s.constraint_name as constraints_name,"); 			//约束名
		sql.append(" s.r_constraint_name as ref_constraints_name,");	//参照约束名
		sql.append(" s.constraint_type as constraints_type_code");		//约束类型代码
		sql.append(" from all_constraints s");
		sql.append(" where s.owner='"+owner_name+"'");
		sql.append(" and s.table_name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表字段关系（外键）信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTableFieldRelaInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" s.constraint_name as constraints_name,"); 	//约束名
		sql.append(" s.column_name as field_name,");			//字段名称
		sql.append(" r.column_name as ref_field_name,");		//参照字段名称
		sql.append(" '' as ref_table_id,");						//参照表ID
		sql.append(" s.position as position");					//位置顺序号
		sql.append(" from all_cons_columns s");
		sql.append(" left join all_constraints c ");
		sql.append(" on s.constraint_name=c.constraint_name and c.constraint_type='R'");
		sql.append(" left join all_cons_columns r ");
		sql.append(" on  c.constraint_name=r.constraint_name");
		sql.append(" where s.owner='"+owner_name+"'");
		sql.append(" and s.table_name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表分区键信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTablePartFieldInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" s.column_name as field_name,");	//分区字段名
		sql.append(" '0' as sub_part_flag,");			//是否主分区
		sql.append(" s.column_position as position,"); 	//位置
		sql.append(" '' as part_field_name");			//分区字段函数定义
		sql.append(" from all_part_key_columns s");
		sql.append(" where s.object_type = 'TABLE'");
		sql.append(" and s.owner='"+owner_name+"'");
		sql.append(" and s.name='"+table_name+"'");
		sql.append(" union all");
		sql.append(" select");
		sql.append(" s.column_name as field_name,");	//分区字段名
		sql.append(" '1' as sub_part_flag,");			//是否主分区
		sql.append(" s.column_position as position,"); 	//位置
		sql.append(" '' as part_field_name");			//分区字段函数定义
		sql.append(" from all_subpart_key_columns s");
		sql.append(" where s.object_type = 'TABLE'");
		sql.append(" and s.owner='"+owner_name+"'");
		sql.append(" and s.name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	/**
	 * 查询指定表分区键值信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTablePartFieldValueInfo(String owner_name, String table_name) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select");
		sql.append(" p.partition_name as partition_name,");											//分区名称
		sql.append(" c.column_name as field_name,");												//字段名称
		sql.append(" case when t.partitioning_type='RANGE' then p.high_value end as max_value,"); 	//位置
		sql.append(" case when t.partitioning_type='RANGE' then n.high_value end as min_value,");	//分区字段函数定义
		sql.append(" case when t.partitioning_type='LIST' then p.high_value end as list_value ");	//分区字段函数定义
		sql.append(" from all_tab_partitions p");
		sql.append(" inner join all_part_key_columns c");
		sql.append(" on p.table_owner=c.owner and p.table_name=c.name");
		sql.append(" left join all_tab_partitions n");
		sql.append(" on p.table_owner=n.table_owner and p.table_name=n.table_name and p.partition_position=n.partition_position-1");
		sql.append(" left join all_part_tables t");
		sql.append(" on p.table_owner=t.owner and p.table_name=t.table_name");
		sql.append(" where c.column_position=1 and c.object_type='TABLE'");
		sql.append(" and c.owner='"+owner_name+"'");
		sql.append(" and c.name='"+table_name+"'");
		return queryForList(sql.toString());
	}

	protected List<Map<String, Object>> queryForList(String sql)
		throws Exception {
		List<Map<String, Object>> dataList = this.jdbcTemplate.queryForList(sql);
		return GlobalUtil.lowercaseListMapKey(dataList);
	}
	
}
