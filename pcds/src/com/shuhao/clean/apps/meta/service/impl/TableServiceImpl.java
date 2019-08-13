package com.shuhao.clean.apps.meta.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.TableDao;
import com.shuhao.clean.apps.meta.dao.UppDicRelaDao;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTable;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.entity.UppUser;
import com.shuhao.clean.apps.meta.service.IColumnService;
import com.shuhao.clean.apps.meta.service.IDBUserService;
import com.shuhao.clean.apps.meta.service.IDatabaseService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.apps.meta.service.ITableService;
import com.shuhao.clean.utils.ConnectionTemplate;
import com.shuhao.clean.utils.DatabaseType;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 元数据[数据库表]业务逻辑实现类
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:38:49
 */
@Service
public class TableServiceImpl implements ITableService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private IDBUserService userService = null;
	
	@Autowired
	private IDatabaseService databaseService = null;
	
	@Autowired
	private IColumnService columnService = null;
	
	@Autowired
	private TableDao tableDao = null;
	
	@Autowired
	private UppDicRelaDao dicRelaDao;
	
	/**
	 * 添加数据库表
	 * @param table
	 * @throws Exception
	 */
	public void addTable(UppTable table) throws Exception {
		this.metadataService.addMetadata(table);
		this.tableDao.addTable(table);
	}
	
	
	/**
	 * 根据id获取数据库表对象
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public UppTable getTableById(String table_id) throws Exception {
		return this.tableDao.getTableById(table_id);
	}
	
	
	/**
	 * 保存数据库表对象
	 * @param table
	 * @throws Exception
	 */
	public void saveTable(UppTable table) throws Exception {
		this.metadataService.saveMetadata(table);
		this.tableDao.saveTable(table);
	}
	
	/**
	 * 删除表对象
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteTable(String table_id) throws Exception {
		this.columnService.deleteColumnByTableId(table_id);
		this.metadataService.deleteMetadata(table_id);
		this.tableDao.deleteTable(table_id);
		dicRelaDao.deleteRelaByDic(table_id);
		//删除表关联字段
		this.tableDao.deleteTableColType(table_id);
	}
	
	/**
	 * 根据数据库和用户查询出表列表
	 * @param parent_metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTableByDatabase(String parent_metadata_id, final String searchKey) throws Exception {
		//寻找表的上级数据库用户
		UppMetadata targetMetadata = this.serchParentMetaByType(parent_metadata_id, "USR");
		UppUser dbUser = this.userService.getUserById(targetMetadata.getMetadata_id());
		
		//寻找用户所属的数据库对象
		targetMetadata = this.serchParentMetaByType(dbUser.getMetadata_id(), "DB");
		UppDatabase database = this.databaseService.getDatabaseById(targetMetadata.getMetadata_id());
		
		final DatabaseType dbType = DatabaseType.getValue(database.getDatabase_type_cd());
		
		Connection conn = this.getConnectionFromDatabase(dbType, database, dbUser);
		
		return new ConnectionTemplate<List<Map<String, Object>>>() {

			public List<Map<String, Object>> doWork(Connection conn) throws SQLException {
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
				String sql = dbType.listTablesSql(searchKey);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("table_name", rs.getString("TABLE_NAME").toLowerCase());
					map.put("table_desc", rs.getString("TABLE_DESC"));
					map.put("table_data_source", rs.getString("TABLE_SCHEMA"));
					dataList.add(map);
				}
				return dataList;
			}
		}.process(conn);
	}
	
	/**
	 * 同步表字段
	 * @param table_id
	 * @param overwrite
	 * @param createUserId
	 * @throws Exception
	 */
	public void syncTableColumn(String table_id, boolean overwrite, String createUserId) throws Exception {
		
		//寻找表的上级数据库用户
		UppMetadata targetMetadata = this.serchParentMetaByType(table_id, "USR");
		UppUser dbUser = this.userService.getUserById(targetMetadata.getMetadata_id());
		
		//寻找用户所属的数据库对象
		targetMetadata = this.serchParentMetaByType(dbUser.getMetadata_id(), "DB");
		UppDatabase database = this.databaseService.getDatabaseById(targetMetadata.getMetadata_id());
		
		//表对象
		UppTable table = this.getTableById(table_id);
		
		//查询列表对象
		List<UppTableColumn> columns = this.queryTableColumns(database, dbUser, table, createUserId);
		
		if(overwrite) {
			//覆盖现有列记录,需要先做删除再插入columns
			this.columnService.deleteColumnByTableId(table_id);
		} else {
			//追加不存在的列记录,需要查询出现有列对象并对columns做过滤操作
			List<UppTableColumn> existsColumns = this.columnService.listTableColumns(table_id);
			for(Iterator<UppTableColumn> colIter = columns.listIterator(); colIter.hasNext();) {
				UppTableColumn column = colIter.next();
				boolean exists = false;
				for (UppTableColumn uppTableColumn : existsColumns) {
					if(uppTableColumn.getColumn_name().equalsIgnoreCase(column.getColumn_name())) {
						exists = true;
						break;
					}
				}
				if(exists) {
					colIter.remove();
				}
			}
		}
		
		for (UppTableColumn column : columns) {
			this.columnService.addColumn(column);
		}
	}
	
	/**
	 * 搜寻符合目标元数据类型的父级
	 * @param metadata_id 起始的元数据id
	 * @param target_metadata_type 目标元数据类型
	 * @return
	 * @throws Exception
	 */
	protected UppMetadata serchParentMetaByType(String metadata_id, String target_metadata_type) throws Exception {
		String endMetadataType = "ROT";
		String currentMetadataId = metadata_id;
		
		UppMetadata meta = null;
		
		do{
			meta = this.metadataService.getMetadataById(currentMetadataId);
			if(target_metadata_type.equals(meta.getMd_cate_cd())) {
				return meta;
			}
			currentMetadataId = meta.getPrt_metadata_id();
		} while(!meta.getMd_cate_cd().equals(endMetadataType));
		
		throw new IllegalArgumentException("节点未包含类型为:[" + target_metadata_type +"] 的上级元数据");
	}
	
	/**
	 * 查询表的所有字段
	 * @param database 数据库对象
	 * @param user	数据库用户
	 * @param table 表对象
	 * @return
	 * @throws Exception
	 */
	protected List<UppTableColumn> queryTableColumns(final UppDatabase database, final UppUser user, final UppTable table, final String createUserId) throws Exception {
		DatabaseType dbType = DatabaseType.getValue(database.getDatabase_type_cd());
		final String queryColumnSql = dbType.queryTableColumnSql(); //查询表字段信息的sql
		Connection conn = this.getConnectionFromDatabase(dbType, database, user); //获取对应数据库连接
		
		//根据sql查询表字段 并封装为UppTableColumn对象
		List<UppTableColumn> columns = new ConnectionTemplate<List<UppTableColumn>>() {
			
			public List<UppTableColumn> doWork(Connection conn) throws SQLException {
				List<UppTableColumn> columnList = new ArrayList<UppTableColumn>();
				PreparedStatement ps = conn.prepareStatement(queryColumnSql);
				ps.setString(1,table.getTable_name().toUpperCase());
				
				ResultSet rs = ps.executeQuery();
				int index = 0;
				while(rs.next()) {
					UppTableColumn column = new UppTableColumn();
					column.setColumn_id(UID.next());
					column.setColumn_name(rs.getString("column_name").toLowerCase());
					column.setColumn_desc(rs.getString("column_desc"));
					column.setData_type_cd(rs.getString("data_type_cd"));
					column.setData_length(rs.getString("data_length"));
					column.setData_scale(rs.getString("data_scale"));
					column.setIs_pk(rs.getString("is_pk"));
					column.setIs_nullable(rs.getString("is_nullable"));
					column.setDisplay_order(String.valueOf(++index));
					column.setCreate_user_id(createUserId);
					column.setIs_display("Y");
					column.setStatus_cd("02");
					column.setMd_cate_cd("COL");
					column.setPrt_metadata_id(table.getTable_id());
					columnList.add(column);
				}
				return columnList;
			}
			
		}.process(conn);
		
		return columns;
	}
	
	/**
	 * 根据数据库对象和用户获取connection
	 * @param dbType
	 * @param database
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected Connection getConnectionFromDatabase(DatabaseType dbType, UppDatabase database, UppUser user) throws Exception {
		String connectUrl = database.getConnect_str();
		String username = user.getUser_name();
		String password = user.getUser_password();
		
		Connection conn = dbType.getDBConnection(connectUrl, username, password);
		return conn;
	}
}
