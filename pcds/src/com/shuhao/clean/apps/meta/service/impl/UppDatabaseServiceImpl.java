package com.shuhao.clean.apps.meta.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.UppDatabaseDao;
import com.shuhao.clean.apps.meta.dao.UppMetadataDao;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppDatabaseUser;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTablespace;
import com.shuhao.clean.apps.meta.service.IUppDatabaseService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.utils.GlobalUtil;


/**
 * 数据库元数据管理接口实现类
 * 包括数据库 、表空间、用户
 * @author bixb
 *
 */
@Service(value="uppDatabaseService")
public class UppDatabaseServiceImpl  extends BaseJdbcService implements IUppDatabaseService {

	@Autowired
	private UppDatabaseDao uppDatabaseDao;
	
	@Autowired
	private UppMetadataDao uppMetadataDao;
	
	/**
	 * 获取数据库信息
	 * @param database
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabase> queryUppDatabaseList(UppDatabase uppDatabase)
			throws Exception {
		
		List<UppDatabase> dataList = uppDatabaseDao.queryUppDatabaseList(uppDatabase);
		return dataList;
		
	}

	/**
	 * 获取表空间信息
	 * 
	 * @param space
	 * @return
	 * @throws Exception
	 */
	public List<UppTablespace> queryUppTableSpaceList(UppTablespace uppTablespace)
			throws Exception {
		List<UppTablespace> dataList = uppDatabaseDao.queryUppTablespaceList(uppTablespace);
		return dataList;
	}

	/**
	 * 获取数据库用户信息
	 * 
	 * @param owener
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabaseUser> queryUppDatabaseUserList(UppDatabaseUser uppDatabaseUser)
			throws Exception {
		List<UppDatabaseUser> dataList = uppDatabaseDao.queryUppDatabaseUserList(uppDatabaseUser);
				
		return dataList;
	}

	/**
	 * 新增数据库元数据记录
	 * 
	 * @param baseInfo
	 * @throws Exception
	 */
	public void addUppDatabase(UppDatabase uppDatabase,UppMetadata uppMetadata) throws Exception {
		uppDatabaseDao.addUppDatabase(uppDatabase);
		//操作元数据的新增 
		uppMetadataDao.addThemeMeta(uppMetadata);
	}

	/**
	 * 新增表空间元数据记录
	 * 
	 * @param table
	 * @throws Exception
	 */
	public void addUppTablespace(UppTablespace uppTablespace,UppMetadata uppMetadata) throws Exception {
		
		uppDatabaseDao.addUppTablespace(uppTablespace);
		
		//操作元数据的新增 
		uppMetadataDao.addThemeMeta(uppMetadata);
	}

	/**
	 * 新增数据库用户元数据记录
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void addUppDatabaseUser(UppDatabaseUser uppDatabaseUser,UppMetadata uppMetadata) throws Exception {
		
		uppDatabaseDao.addUppDatabaseUser(uppDatabaseUser);
		
		//操作元数据的新增 
		uppMetadataDao.addThemeMeta(uppMetadata);
	}

	/**
	 * 修改数据库元数据记录
	 * 
	 * @param baseInfo
	 * @throws Exception
	 */
	public void updateUppDatabase(UppDatabase uppDatabase,UppMetadata uppMetadata) throws Exception {

		uppDatabaseDao.updateUppDatabase(uppDatabase);
		
		//操作元数据的修改 
		uppMetadataDao.updateThemeMeta(uppMetadata);
	}

	/**
	 * 修改表空间元数据记录
	 * 
	 * @param table
	 * @throws Exception
	 */
	public void updateUppTablespace(UppTablespace uppTablespace,UppMetadata uppMetadata) throws Exception {

		uppDatabaseDao.updateUppTablespace(uppTablespace);
		
		//操作元数据的修改 
		uppMetadataDao.updateThemeMeta(uppMetadata);
		
	}

	/**
	 * 修改数据库用户元数据记录
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void updateUppDatabaseUser(UppDatabaseUser uppDatabaseUser,UppMetadata uppMetadata) throws Exception {

		uppDatabaseDao.updateUppDatabaseUser(uppDatabaseUser);
		
		//操作元数据的修改 
		uppMetadataDao.updateThemeMeta(uppMetadata);
	}

	/**
	 * 删除数据库元数据记录
	 * 
	 * @param database
	 * @throws Exception
	 */
	public void deleteUppDatabase(UppDatabase uppDatabase,UppMetadata uppMetadata) throws Exception {
		String database_id = uppDatabase.getDatabase_id();
		String metadata_id = uppMetadata.getMetadata_id();
		List<UppMetadata> dataList = new ArrayList<UppMetadata>();
		
		//获取级联表空间元数据 
		UppTablespace uppTablespace = new UppTablespace();
		uppTablespace.setDatabase_id(database_id);
		List<UppTablespace> uppTablespaceList = uppDatabaseDao.queryUppTablespaceList(uppTablespace);
		for(int i = 0;i<uppTablespaceList.size();i++){
			UppMetadata obj = new UppMetadata();
			obj.setMetadata_id(uppTablespaceList.get(i).getTablespace_id());
			dataList.add(uppMetadata);
		}
		//批量删除表空间元数据信息
		if(dataList.size()>0){
			uppMetadataDao.delMetadataList(dataList);
		}
		
		//删除表空间列表   
		uppDatabaseDao.deleteUppTablespaceByDbId(database_id);
		//清空list
		dataList.clear();
		
		//获取级联用户元数据 
		UppDatabaseUser uppDatabaseUser = new UppDatabaseUser();
		uppDatabaseUser.setDatabase_id(database_id);
		List<UppDatabaseUser> uppDatabaseUserList = uppDatabaseDao.queryUppDatabaseUserList(uppDatabaseUser);
		for(int i = 0;i<uppDatabaseUserList.size();i++){
			UppMetadata obj = new UppMetadata();
			obj.setMetadata_id(uppDatabaseUserList.get(i).getDb_user_id());
			dataList.add(uppMetadata);
		}
		//批量删除用户元数据信息
		if(dataList.size()>0){
			uppMetadataDao.delMetadataList(dataList);
		}
		//删除用户列表   uppDatabaseUser只封装了database_id 将按照数据库删除
		uppDatabaseDao.deleteUppDatabaseUserByDbId(database_id);
		//清空list
		dataList.clear();
		
		
		//删除表 
		
		//删除数据库
		uppDatabaseDao.deleteUppDatabase(uppDatabase);
		
		//操作元数据的删除 
		uppMetadataDao.delThemeMeta(uppMetadata);
	}

	/**
	 * 删除表空间元数据记录
	 * 
	 * @param tablespace
	 * @throws Exception
	 */
	public void deleteUppTableSpace(UppTablespace uppTablespace,UppMetadata uppMetadata) throws Exception {

		uppDatabaseDao.deleteUppTablespace(uppTablespace);
		
		//操作元数据的删除 
		uppMetadataDao.delThemeMeta(uppMetadata);
	}

	/**
	 * 删除数据库用户元数据记录
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void deleteUppDatabaseUser(UppDatabaseUser uppDatabaseUser,UppMetadata uppMetadata) throws Exception {

		uppDatabaseDao.deleteUppDatabaseUser(uppDatabaseUser);
		
		//操作元数据的删除 ?
		uppMetadataDao.delThemeMeta(uppMetadata);
	}

	/**
	 * 测试数据库是否连接正常
	 * 
	 * @param database_name
	 * @param database_ip
	 * @param database_type
	 * @param owner_name
	 * @param owner_password
	 * @param post
	 * @return
	 * @throws Exception
	 */
	public boolean testJdbcConn(UppDatabase database) throws Exception {
		boolean flag = false;
//		String database_ip = database.getDatabase_ip();
//		String access_post = database.getAccess_post();
//		String database_name = database.getDatabase_name();
//		String database_type_id = database.getDatabase_type_id();
//		String username = database.getUsername();
//		String password = database.getPassword();
//		Connection con =null;
//		try {
//			
//			String driver="oracle.jdbc.driver.OracleDriver";
//			String url="jdbc:oracle:thin:@"+database_ip+":"+access_post+":"+database_name;
//			if("01".equals(database_type_id)){
//				driver="oracle.jdbc.driver.OracleDriver";
//				url="jdbc:oracle:thin:@"+database_ip+":"+access_post+":"+database_name;
//			}
//			else if("02".equals(database_type_id)){
//				driver="com.ibm.db2.jcc.DB2Driver";
//				url= "jdbc:db2://"+database_ip+":"+access_post+"/"+database_name;
//			}
//			Class.forName(driver).newInstance();
//			con=DriverManager.getConnection(url,username,password);
//			 flag = true;
//		} catch (Exception e) {
//			throw new Exception("登录异常："+e.getMessage());
//		} finally{
//			try {
//				if(con != null){
//					con.close();
//				}
//			} catch (Exception e) {
//			}
//		}
		return flag;
	}

	/***
	 * 批量添加数据库信息
	 * 
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	public void batchAddUppDatabase(List<UppTablespace> spaceListInfo,
			String database_id) throws Exception {
		
		
	}

	/***
	 * 批量添加数据库表空间信息
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppTablespace(List<Map<String, Object>> spaceListInfo,List<UppMetadata> uppMetadataList) throws Exception {
		
		uppDatabaseDao.addBatchUppTablespace(spaceListInfo);
		//增加元数据
		uppMetadataDao.addMetadataList(uppMetadataList);
	}

	/***
	 * 批量添加数据库用户信息
	 * @param userlist
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppDatabaseUser(List<Map<String, Object>> userlist,List<UppMetadata> uppMetadataList) throws Exception {
		
		uppDatabaseDao.addBatchUppDatabaseUser(userlist);
		//增加元数据
		uppMetadataDao.addMetadataList(uppMetadataList);
	}

	
}
