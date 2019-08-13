package com.shuhao.clean.apps.meta.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.AlterTableDao;
import com.shuhao.clean.apps.meta.dao.MetadataDao;
import com.shuhao.clean.apps.meta.entity.DbColumn;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTable;
import com.shuhao.clean.apps.meta.entity.UppUser;
import com.shuhao.clean.apps.meta.service.IAlterTableService;
import com.shuhao.clean.apps.meta.service.IDBUserService;
import com.shuhao.clean.apps.meta.service.IDatabaseService;
import com.shuhao.clean.apps.meta.service.ITableService;
import com.shuhao.clean.utils.ConnectionTemplate;
import com.shuhao.clean.utils.DatabaseType;

/**
 * 
 * 类描述: 修改物理表业务逻辑实现类
 * @author chenxiangdong
 * @创建时间：2015-1-16下午02:46:19
 */
@Service
public class AlterTableServiceImpl implements IAlterTableService {
	
	@Autowired
	private AlterTableDao alterTableDao;
	
	@Autowired
	private MetadataDao metadataDao;
	
	@Autowired
	private IDatabaseService databaseService;
	
	@Autowired
	private IDBUserService userService;
	
	@Autowired
	private ITableService tableService;
	
	/**
	 * 根据上级元数据查询指定类型的下级元数据列表
	 * @param parent_metadata_id
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryMetadata(String parent_metadata_id, String md_cate_cd) throws Exception {
		List<UppMetadata> metadataList = new ArrayList<UppMetadata>();
		Set<String> parentRela = this.getParentCateLink(md_cate_cd);
		
		this.loopFindMetadata(metadataList, md_cate_cd, parent_metadata_id, parentRela);
		
		return metadataList;
	}
	
	/**
	 * 获取指定元数据类型的所有级联上级类型
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	protected Set<String> getParentCateLink(String md_cate_cd) throws Exception {
		Set<String> parentRela = new HashSet<String>();
		List<String> parentCateList = this.alterTableDao.queryAllowParentList(new String[]{md_cate_cd});
		while(!parentCateList.contains("ROT")) {
			parentCateList.addAll(this.alterTableDao.queryAllowParentList(parentCateList.toArray(new String[0])));
		}
		parentRela.addAll(parentCateList);
		return parentRela;
	}
	
	/**
	 * 循环向下查询指定类型的元数据
	 * @param metadataList 结果列表
	 * @param target_cate_cd 目标类型
	 * @param parent_metadata_id 起始元数据id
	 * @param parentRela 目标类型的级联父级节点id
	 * @throws Exception
	 */
	protected void loopFindMetadata(List<UppMetadata> metadataList, String target_cate_cd, String parent_metadata_id, Set<String> parentRela) throws Exception {
		List<UppMetadata> subMetadata = this.metadataDao.queryMetadataByParentId(parent_metadata_id);
		for (UppMetadata metadata : subMetadata) {
			if(target_cate_cd.equals(metadata.getMd_cate_cd())) {
				metadataList.add(metadata);
			} else if(parentRela.contains(metadata.getMd_cate_cd())) {
				loopFindMetadata(metadataList, target_cate_cd, metadata.getMetadata_id(), parentRela);
			}
			
		}
	}
	
	
	/**
	 * 查询表的列信息
	 * @param owner_id 数据库id
	 * @param owner_id 用户id
	 * @param table_id 表id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTableColumnInfo(final String database_id, final String owner_id, final String table_id) throws Exception {
		UppDatabase database = this.databaseService.getDatabaseById(database_id);
		final UppUser user = this.userService.getUserById(owner_id);
		final UppTable table = this.tableService.getTableById(table_id);
		String url = database.getConnect_str();
		String username = user.getUser_name();
		String password = user.getUser_password();
		
		final DatabaseType dbType = DatabaseType.getValue(database.getDatabase_type_cd());
		Connection conn = dbType.getDBConnection(url, username, password);
		
		List<Map<String, Object>> list = new ConnectionTemplate<List<Map<String, Object>>>() {

			public List<Map<String, Object>> doWork(Connection conn) throws SQLException {
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				PreparedStatement ps = conn.prepareStatement(dbType.queryTableColumnSql());
				ps.setString(1, table.getTable_name().toUpperCase());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("database_id", database_id);
					map.put("owner_id", owner_id);
					map.put("table_id", table_id);
					map.put("column_name", rs.getString("column_name").toLowerCase());
					map.put("column_desc", rs.getString("column_desc"));
					map.put("data_type_cd", rs.getString("data_type_cd"));
					map.put("data_length", rs.getString("data_length"));
					map.put("data_scale", rs.getString("data_scale"));
					map.put("is_pk", rs.getString("is_pk"));
					map.put("is_nullable", rs.getString("is_nullable"));
					dataList.add(map);
				}
				return dataList;
			}
			
		}.process(conn);
		
		return list;
	}
	
	
	/**
	 * 添加物理表列
	 * @param dbColumn
	 * @throws Exception
	 */
	public void addColumn(final DbColumn dbColumn) throws Exception {
		//获取数据库连接
		UppDatabase database = this.databaseService.getDatabaseById(dbColumn.getDatabase_id());
		final UppUser user = this.userService.getUserById(dbColumn.getOwner_id());
		final UppTable table = this.tableService.getTableById(dbColumn.getTable_id());
		String url = database.getConnect_str();
		String username = user.getUser_name();
		String password = user.getUser_password();
		final DatabaseType dbType = DatabaseType.getValue(database.getDatabase_type_cd());
		Connection conn = dbType.getDBConnection(url, username, password);
		//查询是否已有列
		final String isExistsSql = dbType.queryColumnExistsSql();
		dbColumn.setTable_data_source(table.getTable_data_source());
		final String[] addSqls = dbType.addColumnSqls(dbColumn);
		try {
			PreparedStatement ps = conn.prepareStatement(isExistsSql);
//			ps.setString(1, user.getUser_name());

			ps.setString(1, dbColumn.getTable_name());
			ps.setString(2, dbColumn.getColumn_name());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt("col_count");
			if(count > 0) {
				throw new SQLException("物理列[" + dbColumn.getColumn_name() + "]已经存在!");
			}
		
			//添加列
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			for (String sql : addSqls) {
				st.addBatch(sql);
			}
			st.executeBatch();
			//提交事物
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		
	}
	
	/**
	 * 编辑物理表列
	 * @param dbColumn
	 * @throws Exception
	 */
	public void editColumn(DbColumn dbColumn) throws Exception {
		//获取数据库连接
		UppDatabase database = this.databaseService.getDatabaseById(dbColumn.getDatabase_id());
		final UppUser user = this.userService.getUserById(dbColumn.getOwner_id());
		final UppTable table = this.tableService.getTableById(dbColumn.getTable_id());
		
		String url = database.getConnect_str();
		String username = user.getUser_name();
		String password = user.getUser_password();
		dbColumn.setTable_data_source(table.getTable_data_source());
		
		final DatabaseType dbType = DatabaseType.getValue(database.getDatabase_type_cd());
		Connection conn = dbType.getDBConnection(url, username, password);

		List<Map> retList = this.alterTableDao.getColumnInfo(dbColumn);
		if(null !=retList && retList.size()>0){
			Map mp = retList.get(0);
			dbColumn.setData_type_cd(mp.get("data_type_cd").toString());
			dbColumn.setData_length(mp.get("data_length").toString());
		}
		//修改sql
		String[] alterSqls = dbType.updateColumnSqls(dbColumn);
		try {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			for (String sql : alterSqls) {
				st.addBatch(sql);
			}
			st.executeBatch();
			//提交事物
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
}
