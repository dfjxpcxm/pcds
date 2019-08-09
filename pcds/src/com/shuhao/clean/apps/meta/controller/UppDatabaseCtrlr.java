package com.shuhao.clean.apps.meta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.databaseUtil.DqmConnCfg;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppDatabaseUser;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTablespace;
import com.shuhao.clean.apps.meta.service.IOracleDatabaseService;
import com.shuhao.clean.apps.meta.service.IUppDatabaseService;
import com.shuhao.clean.apps.meta.service.impl.OracleDatabaseServiceImpl;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

@Controller
@RequestMapping("/uppDatabase")
public class UppDatabaseCtrlr extends BaseCtrlr {
	
	@Autowired
	private IUppDatabaseService uppDatabaseService;
	
	private IOracleDatabaseService oracleDatabaseService ;
	
	//查询数据库信息f
	@RequestMapping(value="queryUppDatabaseList")
	@ResponseBody
	public Object queryUppDatabaseList() throws Exception {
		UppDatabase database = this.getParamObject(UppDatabase.class);
		
		try {
			List<UppDatabase> dataList = uppDatabaseService.queryUppDatabaseList(database);
			return doJSONResponse(dataList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取数据库元数据信息失败 "+e.getMessage());
		}
	}
	
	//查询元数据数据库下的表空间信息
	@RequestMapping(value="queryUppTablespaceList")
	@ResponseBody
	public Object queryUppTablespaceList() throws Exception {
		UppTablespace tableSpace = this.getParamObject(UppTablespace.class);
		
		try {
			List<UppTablespace> dataList = uppDatabaseService.queryUppTableSpaceList(tableSpace);
			return doJSONResponse(dataList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取表空间元数据信息失败 "+e.getMessage());
		}
	}
	
	/***
	 * 查询选中数据库的表空间信息
	 * (查询原始数据库表空间信息)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryTablespaceList")
	@ResponseBody
	public Object queryTablespaceList() throws Exception {
		
		UppDatabase uppDatabase = this.getParamObject(UppDatabase.class);
		
		try {
			DqmConnCfg base=new DqmConnCfg();
			base.setDatabase_id(uppDatabase.getDatabase_id());
			base.setConnId(uppDatabase.getDatabase_id());
			
			oracleDatabaseService = new OracleDatabaseServiceImpl(base);
			
			List<Map<String, Object>> tablespaceList = oracleDatabaseService.tableSpaceInfoList();
			
			return doJSONResponse(tablespaceList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取表空间信息失败"+e.getMessage());
		}
		
	}
	
	/**
	 * 查询指定数据库用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryUserList")
	@ResponseBody
	public Object queryUserList() throws Exception {
		
		UppDatabase uppDatabase = this.getParamObject(UppDatabase.class);
		try {
			
			DqmConnCfg base=new DqmConnCfg();
			base.setDatabase_id(uppDatabase.getDatabase_id());
			base.setConnId(uppDatabase.getDatabase_id());
			oracleDatabaseService = new OracleDatabaseServiceImpl(base);
			
			List<Map<String, Object>> userList = oracleDatabaseService.dataPrivUserList();
			
			return doJSONResponse(userList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取用户信息失败"+e.getMessage());
		}
		
		/*DqmConnCfg base=new DqmConnCfg();
		base.setDatabase_id(dababase_id);
		base.setConnId(dababase_id);
		Map result = new HashMap();
		DatabaseUtilOracleImpl dboracle=new DatabaseUtilOracleImpl(base);
		List<Map<String, Object>> duserList=new ArrayList<Map<String, Object>>();
		try {
			duserList = dboracle.dataPrivUserList();
			result.put("success", Boolean.valueOf(true));
			doJSONResponse(duserList,request,response);
		} catch (RuntimeException e) {
			result.put("failure",Boolean.valueOf(false));
			result.put("info", "查询数据失败，原因："+e.getMessage());
			doJSONResponse(result,request,response);
			e.printStackTrace();
		}
		return null;*/
	}
	
/*	//查询原始数据表空间信息
	@SuppressWarnings("unchecked")
	public Object querydbLinkList() throws Exception {
		String dababase_id = request.getParameter("database_id");
		DqmConnCfg base=new DqmConnCfg();
		base.setDatabase_id(dababase_id);
		base.setConnId(dababase_id);
		Map result = new HashMap();
		DatabaseUtilOracleImpl dboracle=new DatabaseUtilOracleImpl(base);
		List<Map<String, Object>> dblinklist=new ArrayList<Map<String, Object>>();
		try {
			dblinklist = dboracle.queryDbLinkList();
			result.put("success", Boolean.valueOf(true));
			doJSONResponse(dblinklist,request,response);
		} catch (RuntimeException e) {
			result.put("failure", Boolean.valueOf(false));
			result.put("info", "查询数据失败，原因："+e.getMessage());
			doJSONResponse(result,request,response);
			e.printStackTrace();
		}
		return null;
	}
*/

	//查询数据库下的授权用户信息
	@RequestMapping(value="queryUppDatabaseUserList")
	@ResponseBody
	public Object queryUppDatabaseUserList() throws Exception {
		UppDatabaseUser object = this.getParamObject(UppDatabaseUser.class);
		
		try {
			List<UppDatabaseUser> dataList = uppDatabaseService.queryUppDatabaseUserList(object);
			return doJSONResponse(dataList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取数据库用户元数据信息失败 "+e.getMessage());
		}
		
	}

	//查询数据库下的连接信息
	/*public Object queryDbLinkList() throws Exception {
		String dababase_id = request.getParameter("database_id");
		String dblink_name = request.getParameter("dblink_name");
		BsdConfigDblink dblink=new BsdConfigDblink();
		dblink.setDatabase_id(dababase_id);
		dblink.setDblink_name(dblink_name);
		List<Map<String, Object>> dblinkList = baseInfoDao.dataLinkList(dblink);
		doJSONResponse(dblinkList,request,response);
		return null;
	}*/
	
	//查询数据库类型
	/*public Object listDataBaseType() throws Exception {
		List<Map<String, Object>> databaseList = baseInfoDao.dataBaseTypeList();
		doJSONResponse(databaseList,request,response);
		return null;
	}*/
	
	/**
	 * 数据库保存操作
	 * 对元数据的ID进行判断 如果有进行修改 如果没有进行新增操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUppDatabase")
	@ResponseBody
	public Object addUppDatabase() throws Exception {
		UppDatabase database = this.getParamObject(UppDatabase.class);
		try {
			String opt_user_id = this.getCurrentUser().getUser_id();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_metadata_id", MetaConstant.META_DATA_PARENT_DATABASE);
			params.put("metadata_cate_code", MetaConstant.META_DATA_CATE_DATABASE);
			params.put("metadata_desc", database.getDatabase_desc());
			
			if(database.getDatabase_id() == null || "".equals(database.getDatabase_id())){
				database.setDatabase_id(UID.next(MetaConstant.PREFIX_DATABASE));
				
				params.put("create_user_id", opt_user_id);
				params.put("metadata_id", database.getDatabase_id());
				params.put("metadata_name",database.getDatabase_name());
				params.put("display_order", database.getDisplay_order());
				uppDatabaseService.addUppDatabase(database,this.getUppMetadata(params));
				
			}else{
				params.put("update_user_id", opt_user_id);
				params.put("metadata_id", database.getDatabase_id());
				params.put("metadata_name",database.getDatabase_name());
				params.put("display_order", database.getDisplay_order());
				
				uppDatabaseService.updateUppDatabase(database,this.getUppMetadata(params));
			}
			
			//更新连接缓存
			OracleDatabaseServiceImpl.createDataSource(database);
			
			return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "保存失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
		
	}
	
	//测试数据库
	@RequestMapping(value="testJdbcConn")
	@ResponseBody
	public Object testJdbcConn() throws Exception {
		UppDatabase database = this.getParamObject(UppDatabase.class);
		boolean flag = false;
		try {
			flag = uppDatabaseService.testJdbcConn(database);
			
			return doSuccessInfoResponse("连接成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "连接失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	/**
	 * 新增表空间元数据
	 *
	 */
	@RequestMapping(value="addUppTablespace")
	@ResponseBody
	public Object addUppTablespace() throws Exception {
		
		UppTablespace object = this.getParamObject(UppTablespace.class);
		try {
			String opt_user_id = this.getCurrentUser().getUser_id();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_metadata_id", object.getDatabase_id());
			params.put("metadata_cate_code", MetaConstant.META_DATA_CATE_TABLESPACE);
			params.put("metadata_desc", object.getTablespace_desc());
			
			if(object.getTablespace_id() == null || "".equals(object.getTablespace_id())){
				object.setTablespace_id(UID.next(MetaConstant.PREFIX_TABLESPACE));
				params.put("create_user_id", opt_user_id);
				params.put("metadata_id", object.getTablespace_id());
				params.put("metadata_name",object.getTablespace_name());
				params.put("display_order", object.getDisplay_order());
				uppDatabaseService.addUppTablespace(object,this.getUppMetadata(params));
			}else{
				params.put("update_user_id", opt_user_id);
				params.put("metadata_id", object.getTablespace_id());
				params.put("metadata_name",object.getTablespace_name());
				params.put("display_order", object.getDisplay_order());
				uppDatabaseService.updateUppTablespace(object,this.getUppMetadata(params));
			}
			
			return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "保存失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	/**
	 * 批量新增表空间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addBatchUppTablespace")
	@ResponseBody
	public Object addBatchUppTablespace() throws Exception {
		String database_id = request.getParameter("database_id");
		String infos = request.getParameter("infos");
		
		List<Map<String, Object>> uppTablespaceList=new ArrayList<Map<String, Object>>();
		String opt_user_id = this.getCurrentUser().getUser_id();
		List<Map<String, Object>> uppMetadataList = new ArrayList<Map<String,Object>>();
		
		String [] spacelist=null;
		if(!"".equals(infos)){
			 spacelist=infos.split("@");
			 
			 Map<String, Object>  uppTablespace = null;
			 Map<String, Object>  uppMetadata = null;
			 for(String str:spacelist){
				 if(str !="" && str.indexOf(",") != -1){
					 String [] tabspace=str.split(",");
					 
					 	uppTablespace = new HashMap<String, Object>();
					 	uppTablespace.put("tablespace_id",UID.next(MetaConstant.PREFIX_TABLESPACE));
					 	uppTablespace.put("create_user_id",opt_user_id);
					 	uppTablespace.put("database_id", database_id);
					 	uppTablespace.put("tablespace_name", getStrValue(tabspace, 0));
					 	uppTablespace.put("tablespace_desc", getStrValue(tabspace, 1));
					 	uppTablespace.put("is_display", getStrValue(tabspace, 2));
					 	uppTablespace.put("display_order", getStrValue(tabspace, 3));
					 	
						uppTablespaceList.add(uppTablespace);
						
						uppMetadata = new HashMap<String, Object>();
						uppMetadata.put("parent_metadata_id", database_id);
						uppMetadata.put("create_user_id", opt_user_id);
						uppMetadata.put("metadata_id", uppTablespace.get("tablespace_id"));
						uppMetadata.put("metadata_name",uppTablespace.get("tablespace_name"));
						uppMetadata.put("display_order",uppTablespace.get("display_order"));
						uppMetadata.put("metadata_cate_code", MetaConstant.META_DATA_CATE_TABLESPACE);
						uppMetadataList.add(uppMetadata);
				 }
			 }
		}
		try{
			uppDatabaseService.addBatchUppTablespace(uppTablespaceList,this.getUppMetadataList(uppMetadataList));
			return doSuccessInfoResponse("保存成功");
		
		}catch(Exception e){
			e.printStackTrace();
			String info = "保存失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
		
		/*response.setContentType("text/html;charset=UTF-8");
		String database_id = request.getParameter("database_id");
		String infos = request.getParameter("infos");
		String [] spacelist=null;
		List<BsdConfigTablespace> spaceListInfo=new ArrayList<BsdConfigTablespace>();
		if(!"".equals(infos)){
			 spacelist=infos.split("@");
			 for(String str:spacelist){
				 if(str !="" && str.indexOf(",") != -1){
					 String [] tabspace=str.split(",");
					 BsdConfigTablespace table=new BsdConfigTablespace();
						table.setDatabase_id(database_id);
						table.setTablespace_name(tabspace[0]);
						table.setTablespace_desc(tabspace[1]);
						table.setDisplay_flag(tabspace[2].charAt(0));
						table.setDisplay_order(Integer.parseInt(tabspace[3]));
						baseInfoDao.addTableSpaceInfo(table);
						spaceListInfo.add(table);
				 }
			 }
		}
		baseInfoDao.batchAddTableSpaceInfo(spaceListInfo,database_id);
		Map result = new HashMap();
		try{
			
			result.put("success", Boolean.valueOf(true));
			result.put("info", "保存成功");
		
		}catch(Exception e){
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		doJSONResponse(result, request, response);*/
		
	}
	
	private String getStrValue(String [] tabspace,int i){
		String value =  tabspace[i];
		if(value == null || "null".equals(value)){
			value ="";
		}
		return value;
	}
	
	//批量新增用户
	@RequestMapping(value="addBatchUppDatabaseUser")
	@ResponseBody
	public Object addBatchUppDatabaseUser() throws Exception {
		String database_id = request.getParameter("database_id");
		String infos = request.getParameter("infos");
		String [] users=null;
		List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> uppMetadataList = new ArrayList<Map<String,Object>>();
		String opt_user_id = this.getCurrentUser().getUser_id();
		
		if(!"".equals(infos)){
			users=infos.split("@");
			
			Map<String, Object> map = null;
			Map<String, Object> uppMetadata = null;
			 for(String str:users){
				 if(str !="" && str.indexOf(",") != -1){
					String [] user=str.split(",");
					map = new HashMap<String, Object>();
				 	map.put("db_user_id", UID.next(MetaConstant.PREFIX_DATABASE_USER));
				 	map.put("create_user_id", opt_user_id);
				 	map.put("database_id", database_id);
				 	map.put("owner_name", this.getStrValue(user, 0));
				 	map.put("owner_desc", this.getStrValue(user, 1));
				 	map.put("is_display", this.getStrValue(user, 2));
				 	map.put("display_order", this.getStrValue(user, 3));
				 	userList.add(map);
				 	
				 	uppMetadata = new HashMap<String, Object>();
					uppMetadata.put("parent_metadata_id", database_id);
					uppMetadata.put("create_user_id", opt_user_id);
					uppMetadata.put("metadata_id", map.get("db_user_id"));
					uppMetadata.put("metadata_name",map.get("owner_name"));
					uppMetadata.put("display_order",map.get("display_order"));
					uppMetadata.put("metadata_cate_code", MetaConstant.META_DATA_CATE_DATABASE_USER);
					uppMetadataList.add(uppMetadata);
				 }
			 }
		}
		try{
			uppDatabaseService.addBatchUppDatabaseUser(userList,this.getUppMetadataList(uppMetadataList));
			return doSuccessInfoResponse("保存成功");
		
		}catch(Exception e){
			e.printStackTrace();
			String info = "保存失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	//批量新增数据库连接
	public Object batchAddDblink() throws Exception {
		/*response.setContentType("text/html;charset=UTF-8");
		String database_id = request.getParameter("database_id");
		String infos = request.getParameter("infos");
		String [] links=null;
		List<BsdConfigDblink> linklist=new ArrayList<BsdConfigDblink>();
		if(!"".equals(infos)){
			links=infos.split("@");
			 for(String str:links){
				 if(str !="" && str.indexOf(",") != -1){
					 String [] link=str.split(",");
					 BsdConfigDblink lk=new BsdConfigDblink();
						lk.setDatabase_id(database_id);
						lk.setDblink_name(link[0]);
						lk.setDblink_desc(link[1]);
						lk.setDisplay_flag(link[2].charAt(0));
						lk.setDisplay_order(Integer.parseInt(link[3]));
						linklist.add(lk);
				 }
			 }
		}
		baseInfoDao.batchAddDbLink(linklist,database_id);
		Map result = new HashMap();
		try{
			
			result.put("success", Boolean.valueOf(true));
			result.put("info", "保存成功");
		
		}catch(Exception e){
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		doJSONResponse(result, request, response);*/
		
		return null;
	}
	
	//新增授权用户
	@RequestMapping(value="addUppDatabaseUser")
	@ResponseBody
	public Object addUppDatabaseUser() throws Exception {
		UppDatabaseUser object = this.getParamObject(UppDatabaseUser.class);
		try {
			String opt_user_id = this.getCurrentUser().getUser_id();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_metadata_id", object.getDatabase_id());
			params.put("metadata_cate_code", MetaConstant.META_DATA_CATE_DATABASE_USER);
			params.put("metadata_desc", object.getOwner_desc());
			if(object.getDb_user_id() == null || "".equals(object.getDb_user_id())){
				object.setDb_user_id(UID.next(MetaConstant.PREFIX_DATABASE_USER));
				params.put("create_user_id", opt_user_id);
				params.put("metadata_id", object.getDb_user_id());
				params.put("metadata_name",object.getOwner_name());
				params.put("display_order", object.getDisplay_order());
				uppDatabaseService.addUppDatabaseUser(object,this.getUppMetadata(params));
			}else{
				uppDatabaseService.updateUppDatabaseUser(object,this.getUppMetadata(params));
			}
			return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "保存失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	//新增授数据库连接
	public Object addDbLink() throws Exception {
		/*response.setContentType("text/html;charset=UTF-8");
		String database_id = request.getParameter("database_id");
		String dblink_name = request.getParameter("dblink_name");
		String dblink_desc = request.getParameter("dblink_desc");
		int display_order =Integer.parseInt(request.getParameter("display_order"));
		char display_flag =request.getParameter("display_flag").charAt(0);
		Map result = new HashMap();
		try{
			BsdConfigDblink dblink=new BsdConfigDblink();
			dblink.setDatabase_id(database_id);
			dblink.setDblink_name(dblink_name);
			dblink.setDblink_desc(dblink_desc);
			dblink.setDisplay_order(display_order);
			dblink.setDisplay_flag(display_flag);
			baseInfoDao.addDbLinkInfo(dblink);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "保存成功");
		
		}catch(Exception e){
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		doJSONResponse(result, request, response);*/
		
		return null;
	}
	
	//删除数据库
	@RequestMapping(value="deleteUppDatabase")
	@ResponseBody
	public Object deleteUppDatabase() throws Exception {
		
		UppDatabase object = this.getParamObject(UppDatabase.class);
		String opt_user_id = this.getCurrentUser().getUser_id();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("update_user_id", opt_user_id);
			params.put("metadata_id", object.getDatabase_id());
			uppDatabaseService.deleteUppDatabase(object,this.getUppMetadata(params));
			//更新连接缓存
			OracleDatabaseServiceImpl.deleteDataSource(object);
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "删除失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	//删除数据库连接
	public Object deleteDataLink() throws Exception {
		/*response.setContentType("text/html;charset=UTF-8");
		String database_id = request.getParameter("database_id");
		String dblink_name = request.getParameter("dblink_name");
		Map result = new HashMap();
		try{
			BsdConfigDblink link=new BsdConfigDblink();
			link.setDatabase_id(database_id);
			link.setDblink_name(dblink_name);
			baseInfoDao.deleteDblink(link);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "删除成功");
		
		}catch(Exception e){
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "删除失败:" + e.getMessage());
		}
		doJSONResponse(result, request, response);*/
		
		return null;
	}
	
	//删除表空间
	@RequestMapping(value="deleteUppTablespace")
	@ResponseBody
	public Object deleteUppTablespace() throws Exception {
		UppTablespace object = this.getParamObject(UppTablespace.class);
		String opt_user_id = this.getCurrentUser().getUser_id();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("update_user_id", opt_user_id);
			params.put("metadata_id", object.getTablespace_id());
			uppDatabaseService.deleteUppTableSpace(object,this.getUppMetadata(params));
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "删除失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
	
	//删除授权用户
	@RequestMapping(value="deleteUppDatabaseUser")
	@ResponseBody
	public Object deleteUppDatabaseUser() throws Exception {
		UppDatabaseUser object = this.getParamObject(UppDatabaseUser.class);
		String opt_user_id = this.getCurrentUser().getUser_id();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("update_user_id", opt_user_id);
			params.put("metadata_id", object.getDb_user_id());
			uppDatabaseService.deleteUppDatabaseUser(object,this.getUppMetadata(params));
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			String info = "删除失败:"+e.getMessage();
			return doFailureInfoResponse(info);
		}
	}
		
	
	
	/**
	 * 获取metadata
	 * @param param
	 * @return
	 */
	private UppMetadata getUppMetadata(Map<String, Object> params){
		UppMetadata dataObj = new UppMetadata();
		String metadata_id = GlobalUtil.getStringValue(params, "metadata_id");
		String metadata_name = GlobalUtil.getStringValue(params, "metadata_name");
		String display_order = GlobalUtil.getStringValue(params, "display_order");
		String create_user_id = GlobalUtil.getStringValue(params, "create_user_id");
		String update_user_id = GlobalUtil.getStringValue(params, "update_user_id");
		String parentmetadata_id = GlobalUtil.getStringValue(params, "parent_metadata_id");
		String metadata_cate_code = GlobalUtil.getStringValue(params, "metadata_cate_code");
		String metadata_desc = GlobalUtil.getStringValue(params, "metadata_desc");
		
		dataObj.setMetadata_id(metadata_id);
//		dataObj.setParent_metadata_id(parentmetadata_id);
		dataObj.setMetadata_name(metadata_name);
		dataObj.setDisplay_order(display_order);
		dataObj.setCreate_user_id(create_user_id);
		dataObj.setUpdate_user_id(update_user_id);
		//数据库 101020  表空间 101021 数据库用户 101022
//		dataObj.setMetadata_cate_code(metadata_cate_code);//
//		dataObj.setMetadata_desc(metadata_desc);
		
//		dataObj.setStatus_code("02");//02正常???
		return dataObj;
	}
	
	/***
	 * 获取UppMetadata列表
	 * @param params
	 * @return
	 */
	private List<UppMetadata> getUppMetadataList(List<Map<String, Object>> params){
		List<UppMetadata> uppMetadataList = new ArrayList<UppMetadata>();
		
		UppMetadata dataObj = null;
		for(int i = 0;i<params.size();i++){
			dataObj = this.getUppMetadata(params.get(i));
			uppMetadataList.add(dataObj);
		}
		
		return uppMetadataList;
	}
}
