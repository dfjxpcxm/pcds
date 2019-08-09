package com.shuhao.clean.apps.meta.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.flow.dao.FlowDao;
import com.shuhao.clean.apps.meta.dao.ManagerFnMdProDao;
import com.shuhao.clean.apps.meta.dao.TableManageDao;
import com.shuhao.clean.apps.meta.dao.UppMetadataDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;
import com.shuhao.clean.apps.meta.entity.UppFnMetadataProperty;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.service.ITableManageService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

@Service(value = "tableManageService")
public class TableManageServiceImpl extends BaseJdbcService implements
		ITableManageService {
	
	static Logger logger = Logger.getLogger(TableManageServiceImpl.class);

	public TableManageDao tableManageDao;
	
	@Autowired
	public  UppMetadataDao uppMetadataDao;
	
	@Autowired
	public ManagerFnMdProDao managerFnMdProDao;
	
	@Autowired
	private FlowDao flowDao;
	
	public List<Map<String, Object>> getDataBase() throws Exception {
		return  toLowerMapList(tableManageDao.getDataBaseList());
	}
	
	public List<Map<String, Object>> getDBUser(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(tableManageDao.getOwnerList(paramMap));
	}
	
	public List<Map<String, Object>> getLocalTable(Map<String, Object> paramMap)
			throws Exception {
		return toLowerMapList(tableManageDao.getLocalTableList(paramMap));
	}
	
	@Resource(name="tableManageDao")
	public void setTableManageDao(TableManageDao tableManageDao) {
		this.tableManageDao = tableManageDao;
	}

	public List<Map<String, Object>> getRemoteTable(Map<String, Object> paramMap) throws Exception {
		String database_name=getStringValue(paramMap,"database_name");
		String dbUser=getStringValue(paramMap,"owner_name");
		String searchCon=getStringValue(paramMap,"searchCon");
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		String sql="select  t.table_name as table_name, "+            //表名
						     "'' as table_desc,"+                 //表描述
						     "'"+database_name+"' as database_id,"+      //引用的数据库ID
						     " t.owner as owner_name, "+               //用户名
						     " t.tablespace_name as tablespace_name, "+         //表空间名称
						     "'' as table_style_code, "+               //定义表分类代码
						     "'' as source_system_code,"+             // 源系统代码
						     "'1' as maintain_flag,    "+           //表数据是否可以维护
						     "'' as con_table_type_code,  "+            //配置表类型
						     "'' as sub_sql,"+                   //子查询SQL
						     "case when s.partitioning_type is null then 'N/A'"+
						     " when s.subpartitioning_type!='NONE' then s.partitioning_type || '_' || s.subpartitioning_type"+
						     " else s.partitioning_type end as partition_type_code "+ //分区类型
				     " from all_tables t"+
				     " left join all_part_tables s on t.owner=s.owner and t.table_name=s.table_name"+
				     " where t.owner=upper(?) and t.table_name like 'IMP%'";
				     if (GlobalUtil.isNotNull(searchCon)) {
						sql+=" and t.table_name like '%"+searchCon+"%'";
					}
				    sql+= " order by t.table_name";
				    logger.debug("------"+sql);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.250:1521:"+database_name, "opmadm", "opmadm");
			logger.info("--创建连接成功--");
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,dbUser );
			res=stmt.executeQuery();
			while (res.next()) {
				Map<String, Object> resultMap=new HashMap<String, Object>();
				resultMap.put("table_name", res.getString("table_name"));
				resultMap.put("show_table_name", res.getString("table_name"));
				resultMap.put("table_desc", res.getString("table_desc")==null ? res.getString("table_name"):res.getString("table_desc")) ;
				resultMap.put("database_id", res.getString("database_id"));
				resultMap.put("owner_name", res.getString("owner_name"));
				resultMap.put("tablespace_name", res.getString("tablespace_name"));
				resultMap.put("table_style_code", res.getString("table_style_code"));
				resultMap.put("source_system_code", res.getString("source_system_code"));
				resultMap.put("maintain_flag", res.getString("maintain_flag"));
				resultMap.put("con_table_type_code", res.getString("con_table_type_code"));
				resultMap.put("sub_sql", res.getString("sub_sql"));
				resultMap.put("partition_type_code", res.getString("partition_type_code"));
				resultList.add(resultMap);
			}
			List<Map<String, Object>> localList=tableManageDao.getLocalTableList(paramMap);
			for (Map<String, Object> map : localList) {
				for (Map<String, Object> resultmap : resultList) {
					if(getStringValue(map, "TABLE_NAME").equals(getStringValue(resultmap, "table_name"))){
						resultmap.put("show_table_name", "@"+getStringValue(map, "TABLE_NAME"));
						break;
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null){
					res.close();
				}
			} catch (Exception e) {
			}
			try {
				if(stmt!=null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(conn!=null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return resultList;
	}

	public int getLocalTableCount(Map<String, Object> paramMap)
			throws Exception {
		return tableManageDao.getLocalTableCount(paramMap);
	}

	public List<Map<String, Object>> getLocalTableInfo(
			Map<String, Object> paramMap) throws Exception {
		
		return toLowerMapList(tableManageDao.getLocalTableInfo(paramMap));
	}

	public List<Map<String, Object>> getTableSpaceList(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(tableManageDao.getTableSpaceList(paramMap));
	}
	
	public List<Map<String, Object>> getThemeList() throws Exception {
		return toLowerMapList(tableManageDao.getThemeList());
	}

	public List<Map<String, Object>> getConTableTypeList() throws Exception {
		return toLowerMapList(tableManageDao.getConTableTypeList());
	}

	public List<Map<String, Object>> getDataTypeList() throws Exception {
		return toLowerMapList(tableManageDao.getDataTypeList());
	}

	public List<Map<String, Object>> getTableInfo(Map<String, Object> paramMap)
			throws Exception {
		String database_name=getStringValue(paramMap, "database_name");
		String owner_name=getStringValue(paramMap, "owner_name");
		String table_name=getStringValue(paramMap, "table_name");
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet res=null;
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.250:1521:"+database_name, "opmadm", "opmadm");
			logger.info("--创建连接成功--");
			StringBuffer sql=new StringBuffer();
			sql.append("select");
			sql.append(" s.column_name as column_name,"); 								//列名
			sql.append(" 'Y' as is_display,");										//是否显示
			sql.append(" '' as column_type_code,");										//字段类型代码
			sql.append(" s.data_type as data_type_code,");									//字段类型
			sql.append(" s.data_length as data_length,");  								//数据长度
			sql.append(" s.data_scale as data_scale,"); 								//数据刻度
			sql.append(" s.nullable as is_nullable,");	//是否可为空
			sql.append(" s.data_default as default_value,");							//默认值
			sql.append(" s.column_id as display_order");									//字段顺序号
			sql.append(" from all_tab_columns s");
			sql.append(" where s.owner=?");
			sql.append(" and s.table_name=?");
			logger.debug("------远程表字段sql------"+table_name+sql.toString());
			stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1,owner_name.toUpperCase());
			stmt.setString(2,table_name.toUpperCase());
			
			res=stmt.executeQuery();
			while (res.next()) {
				Map<String, Object> resultMap=new HashMap<String, Object>();
				resultMap.put("column_name", res.getString("column_name"));
				resultMap.put("is_display", res.getString("is_display"));
				resultMap.put("data_type_code", res.getString("data_type_code"));
				resultMap.put("column_type_code", res.getString("column_type_code"));
				resultMap.put("data_length", res.getString("data_length"));
				resultMap.put("data_scale", res.getString("data_scale"));
				resultMap.put("is_nullable", res.getString("is_nullable"));
				resultMap.put("default_value", res.getString("default_value"));
				resultMap.put("display_order", res.getString("display_order"));
				resultList.add(resultMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(res!=null){
					res.close();
				}
			} catch (Exception e) {
			}
			try {
				if(stmt!=null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(conn!=null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return resultList;
	}
	
	//	private String trimNull(String str){
	//		return (str==null || str.equals("null") || str.equals("undefined"))? null :str;
	//	}
		
	//	private String getBooleanStr(String str){
	//		String res=str==null? "N" : (str.equals("true") ? "Y" : "N");
	//		return res;
	//	}
	//	
	//
	//	private String getArrVal(String[] col,int index) {
	//		String str = (col.length<index+1? "" :col[index]);
	//		return (str==null || str.equals("null") || str.equals("undefined"))? null :str;
	//	}
		
		public void deleteTable(Map<String, Object> paramMap) throws Exception {
			tableManageDao.deleteTableGlInfo(paramMap);//删除表及关联信息
//			tableManageDao.deleteTable(paramMap);
//			tableManageDao.deleteTableField(paramMap);
			//UppDicFnRela uppDicFnRela=new UppDicFnRela(getStringValue(paramMap, "table_id"),null);
	//		UppMetadata metadata=new UppMetadata();TODO 删除元数据
	//		metadata.setMetadata_id(getStringValue(paramMap, "table_id"));
	//		uppMetadataDao.delMetadataByParentId(metadata);
//			UppFnMetadataProperty property=new UppFnMetadataProperty();
//			managerFnMdProDao.deleteFnMdPro(property);
//			
//			tableManageDao.deleteDicFnRela(uppDicFnRela);
			//flowDao.delAndAddBlmbGn(property.getMetadata_id());
			
		}

	public int getTableDataCount(Map<String, Object> paramMap)  throws Exception {
//		int tableDataCount = tableManageDao.getTableDataCount(paramMap);
//		if (tableDataCount>0) {
//			throw new Exception();
//		}
		return tableManageDao.getTableDataCount(paramMap);
	}

	public void deleteField(Map<String, Object> paramMap) throws Exception {
		String column = getStringValue(paramMap, "column_id");
		paramMap.put("metadata_id", column);
		
		tableManageDao.deleteField(paramMap);
		
		UppDicFnRela uppDicFnRela=new UppDicFnRela(column,null);
		tableManageDao.deleteDicFnRela(uppDicFnRela);
		
		
		UppMetadata metadata=new UppMetadata();
		metadata.setMetadata_id(column);
		uppMetadataDao.delThemeMeta(metadata);
		
		List<Map<String, Object>> fieldMap=tableManageDao.getFnMetaId(paramMap);
		if(fieldMap!=null && !fieldMap.isEmpty()){
			
			UppFnMetadataProperty property=new UppFnMetadataProperty();
			property.setMetadata_id(getStringValue(fieldMap.get(0), "FN_METADATA_ID"));
			
			flowDao.delAndAddBlmbGn(property.getMetadata_id());
			
			metadata.setMetadata_id(property.getMetadata_id());
			uppMetadataDao.delThemeMeta(metadata);
			managerFnMdProDao.deleteFnMdPro(property);
			
			
		}
		
	}

	public void addField(Map<String, Object> paramMap) throws Exception {
		String column_id = UID.next(MetaConstant.PREFIX_COLUMN);
		paramMap.put("column_id",column_id );
		
		if (getStringValue(paramMap, "column_name") != null) {
			byte[] bytes = getStringValue(paramMap, "column_name").getBytes(
					"iso-8859-1");
			paramMap.put("column_name", new String(bytes, "utf-8"));
		}
		if (getStringValue(paramMap, "column_desc") != null) {
			byte[] bytes = getStringValue(paramMap, "column_desc").getBytes(
					"iso-8859-1");
			paramMap.put("column_desc", new String(bytes, "utf-8"));
		}
		
		if (getStringValue(paramMap, "default_value") != null) {
			byte[] bytes = getStringValue(paramMap, "default_value").getBytes(
					"iso-8859-1");
			paramMap.put("default_value", new String(bytes, "utf-8"));
		}
		
		tableManageDao.addTableField(paramMap);
		
		String field_id = UID.next(MetaConstant.PREFIX_COLUMN);
		
		String table_id=getStringValue(paramMap, "table_id");
		String user_id=getStringValue(paramMap, "user_id");
		String statusCode=getStringValue(paramMap, "status_code");
		statusCode=(statusCode==null || statusCode.equals(""))? "02" :statusCode;
		//--------------字段元数据--------
		UppMetadata field=createMetadata("1010101", column_id,getStringValue(paramMap, "column_name") , 
				user_id, table_id, statusCode,getStringValue(paramMap, "display_order"),getStringValue(paramMap, "column_desc"));
		uppMetadataDao.addThemeMeta(field);
		//------------功能属性元数据----------
		paramMap.put("metadata_id", table_id);
		List<Map<String, Object>> pageMap=tableManageDao.getFnMetaId(paramMap);
		if (pageMap.isEmpty()) {
			throw new Exception("功能页面元数据为空，不能添加该字段！");
		}
		field=createMetadata("2010101", field_id,getStringValue(paramMap, "column_name") , 
				user_id, getStringValue(pageMap.get(0), "FN_METADATA_ID"), statusCode,getStringValue(paramMap, "display_order"),getStringValue(paramMap, "column_desc"));
		uppMetadataDao.addThemeMeta(field);
		
		UppFnMetadataProperty property=new UppFnMetadataProperty();
		property.setMetadata_id(field.getMetadata_id());
		property.setMetadata_name(field.getMetadata_name());
		property.setStatus_code(statusCode);
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		UppDicFnRela uppDicFnRela=new UppDicFnRela();
		uppDicFnRela.setDb_obj_id(column_id);
		uppDicFnRela.setMetadata_id(field_id);
		/*uppDicFnRela.setDic_metadata_id(column_id);
		uppDicFnRela.setFn_metadata_id(field_id);*/
		tableManageDao.addDicFnRela(uppDicFnRela);
		
		flowDao.delAndAddBlmbGn(property.getMetadata_id());
	}

	
	public void updateField(Map<String, Object> paramMap) throws Exception {
		if (getStringValue(paramMap, "column_name") != null) {
			byte[] bytes = getStringValue(paramMap, "column_name").getBytes(
					"iso-8859-1");
			paramMap.put("column_name", new String(bytes, "utf-8"));
		}
		if (getStringValue(paramMap, "column_desc") != null) {
			byte[] bytes = getStringValue(paramMap, "column_desc").getBytes(
					"iso-8859-1");
			paramMap.put("column_desc", new String(bytes, "utf-8"));
		}
		tableManageDao.updateField(paramMap);
		/**
		 * 		业务元数据	10					功能元数据	20
				主题			1010				功能页面		2010
				表			101010				工具条		201010
				字段			1010101				显示字段		2010101
	
		 */
		//-----修改表时修改元数据---修改时以表的id作为元数据id，与添加一致-----------
		UppMetadata field=createMetadata("101010", getStringValue(paramMap, "column_id"), getStringValue(paramMap, "column_name"),
				getStringValue(paramMap, "user_id"),getStringValue(paramMap, "theme_id"), getStringValue(paramMap, "status_code"),"101010",getStringValue(paramMap, "column_desc"));
		uppMetadataDao.updateThemeMeta(field);
		 
		UppFnMetadataProperty property=new UppFnMetadataProperty();
		property.setMetadata_id(field.getMetadata_id());
		property.setMetadata_name(field.getMetadata_name());
		managerFnMdProDao.updateFnMdPro(property);
	}

	
	public void updateTableInfo(Map<String, Object> paramMap) throws Exception {
		if (getStringValue(paramMap, "table_desc") != null) {
			byte[] bytes = getStringValue(paramMap, "table_desc").getBytes(
					"iso-8859-1");
			paramMap.put("table_desc", new String(bytes, "utf-8"));
		}
		tableManageDao.updateTableInfo(paramMap);
		/**
		 * 业务元数据 10 功能元数据 20 主题 1010 功能页面 2010 表 101010 工具条 201010 字段 1010101
		 * 显示字段 2010101
		 * 
		 */
		// -----修改表时修改元数据---修改时以表的id作为元数据id，与添加一致-----------
		UppMetadata table = createMetadata("101010", getStringValue(paramMap,
				"table_id"), getStringValue(paramMap, "table_name"),
				getStringValue(paramMap, "user_id"), getStringValue(paramMap,
						"theme_id"), getStringValue(paramMap, "statue_code"),
				"101010",getStringValue(paramMap, "table_desc"));
		uppMetadataDao.updateThemeMeta(table);
	
		// -------------------------
		UppFnMetadataProperty property = new UppFnMetadataProperty();
		property.setMetadata_id(table.getMetadata_id());
		property.setMetadata_name(table.getMetadata_name());
		managerFnMdProDao.updateFnMdPro(property);
		
		//----------------------修改表对应功能页面的元数据
		UppMetadata pageMeta = new UppMetadata();
		Map<String,Object> row = new HashMap<String, Object>();
		row.put("metadata_id", table.getMetadata_id());
		pageMeta.setMetadata_id((String)this.tableManageDao.getFnMetaId(row).get(0).get("FN_METADATA_ID"));
		
		List<Map<String,Object>>  pageMetaList= toLowerMapList(this.uppMetadataDao.getMetadataById(pageMeta));
		if(pageMetaList.size()==1){
			Map<String,Object> pageMetaRow = (Map<String,Object>)pageMetaList.get(0);
//			pageMeta.setMetadata_desc(table.getMetadata_desc());
			pageMeta.setMetadata_name(table.getMetadata_name());
			pageMeta.setUpdate_user_id(table.getCreate_user_id());
//			pageMeta.setStatus_code(table.getStatus_code());
			
//			pageMeta.setParent_metadata_id(GlobalUtil.getStringValue(pageMetaRow, "parent_metadata_id"));
//			pageMeta.setMetadata_cate_code(GlobalUtil.getStringValue(pageMetaRow, "metadata_cate_code"));
			pageMeta.setDisplay_order(GlobalUtil.getStringValue(pageMetaRow, "display_order"));
			
			uppMetadataDao.updateThemeMeta(pageMeta);
		}
	}

	
	public void addTable(Map<String, Object> paramMap)
			throws Exception {
		String user_id=getStringValue(paramMap, "user_id");
		String table_id=UID.next("tb");
		String table_name=getStringValue(paramMap, "table_name");
		String statusCode=getStringValue(paramMap, "status_code");
		String themeId=getStringValue(paramMap, "theme_id");
		paramMap.put("table_id", table_id);
		if (getStringValue(paramMap, "table_desc")!=null) {
			byte[] bytes = getStringValue(paramMap, "table_desc").getBytes("iso-8859-1");
			paramMap.put("table_desc", new String (bytes,"utf-8"));
		}
		tableManageDao.addTable(paramMap);
		
		/**
		 * 		业务元数据	10					功能元数据	20
				主题			1010				功能页面		2010
				表			101010				工具条		201010
				字段			1010101				显示字段		2010101

		 */
		//----------表--------------表id作为元数据的id
		UppMetadata table=createMetadata("101010", table_id, table_name, user_id, themeId, statusCode,"101010",getStringValue(paramMap, "table_desc"));
		uppMetadataDao.addThemeMeta(table);
		
		
		//----------表对应的功能页面------自动生成的元数据id
		UppMetadata page=createMetadata("2010",UID.next(MetaConstant.PREFIX_PAGE),table_name,
				user_id,"metath20",statusCode,"2010",getStringValue(paramMap, "table_desc"));
		uppMetadataDao.addThemeMeta(page);
		
		UppFnMetadataProperty property=new UppFnMetadataProperty();
		property.setMetadata_id(page.getMetadata_id());
		property.setMetadata_name(page.getMetadata_name());
		property.setStatus_code(statusCode);
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//--------功能页面中的工具条按钮-------导入----------自动生成的元数据id
		UppMetadata importMetadata=createMetadata("201010", UID.next(MetaConstant.PREFIX_TOOLBAR), "导入", 
				user_id, page.getMetadata_id(), statusCode,"201010", "导入");
		uppMetadataDao.addThemeMeta(importMetadata);
		
		property.setMetadata_id(importMetadata.getMetadata_id());
		property.setMetadata_name(importMetadata.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//--------导出------自动生成的元数据id
		UppMetadata exportMetadata=createMetadata("201010", UID.next(MetaConstant.PREFIX_TOOLBAR), "导出",
				user_id, page.getMetadata_id(), statusCode,"201010", "导出");
		uppMetadataDao.addThemeMeta(exportMetadata);
		
		property.setMetadata_id(exportMetadata.getMetadata_id());
		property.setMetadata_name(exportMetadata.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//---------新增-----自动生成的元数据id
		UppMetadata add=createMetadata("201010", UID.next(MetaConstant.PREFIX_TOOLBAR), "新增",
				user_id, page.getMetadata_id(),statusCode,"201010", "新增");
		uppMetadataDao.addThemeMeta(add);
		
		property.setMetadata_id(add.getMetadata_id());
		property.setMetadata_name(add.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		//--------修改------自动生成的元数据id
		UppMetadata update=createMetadata("201010", UID.next(MetaConstant.PREFIX_TOOLBAR), "修改", 
				user_id, page.getMetadata_id(), statusCode,"201010", "修改");
		uppMetadataDao.addThemeMeta(update);
		
		property.setMetadata_id(update.getMetadata_id());
		property.setMetadata_name(update.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//----------删除----
		UppMetadata delete=createMetadata("201010",UID.next(MetaConstant.PREFIX_TOOLBAR),"删除",
				user_id,page.getMetadata_id(),statusCode,"201010","删除");
		uppMetadataDao.addThemeMeta(delete);
		property.setMetadata_id(delete.getMetadata_id());
		property.setMetadata_name(delete.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//----------查询----
		UppMetadata query=createMetadata("201010",UID.next(MetaConstant.PREFIX_TOOLBAR),"查询",
				user_id,page.getMetadata_id(),statusCode,"201010","查询");
		uppMetadataDao.addThemeMeta(query);
		property.setMetadata_id(query.getMetadata_id());
		property.setMetadata_name(query.getMetadata_name());
		property.setStatus_code("02");
		property.setComponent_hide("N");
		property.setIf_editable("Y");
		property.setComponent_query("N");
		property.setIf_pk("N");
		property.setIf_must_input("N");
		managerFnMdProDao.addFnMdPro(property);
		
		//添加表与页面映射关系
		UppDicFnRela dicFn=new UppDicFnRela(table.getMetadata_id(),page.getMetadata_id());
		tableManageDao.addDicFnRela(dicFn);
		
//		dicFn=new UppDicFnRela(table.getMetadata_id(),importMetadata.getMetadata_id());
//		tableManageDao.addDicFnRela(dicFn);
//		
//		dicFn=new UppDicFnRela(table.getMetadata_id(),exportMetadata.getMetadata_id());
//		tableManageDao.addDicFnRela(dicFn);
//		
//		dicFn=new UppDicFnRela(table.getMetadata_id(),add.getMetadata_id());
//		tableManageDao.addDicFnRela(dicFn);
//		
//		dicFn=new UppDicFnRela(table.getMetadata_id(),update.getMetadata_id());
//		tableManageDao.addDicFnRela(dicFn);
//		
//		dicFn=new UppDicFnRela(table.getMetadata_id(),delete.getMetadata_id());
//		tableManageDao.addDicFnRela(dicFn);
		
		//-------------添加表时同时添加表字段------------------
		paramMap.put("page_id", page.getMetadata_id());
		String addTable=getStringValue(paramMap, "addTable");
		if ("01".equals(addTable)) {
			addTableField(paramMap);
		}
		
	}


	private UppMetadata createMetadata(String metaCode, String metaId,
			String metaName, String userId, String parentMetaId,
			String statusCode, String displayOrder,String metadata_desc) {
		UppMetadata obj = new UppMetadata();
		obj.setCreate_user_id(userId);
//		obj.setMetadata_cate_code(metaCode);
		obj.setMetadata_id(metaId);
//		obj.setMetadata_desc(metadata_desc);
//		obj.setParent_metadata_id(parentMetaId);
		obj.setMetadata_name(metaName);
//		obj.setStatus_code(statusCode);
		obj.setDisplay_order(displayOrder);
		obj.setUpdate_user_id(userId);
		return obj;
	}
	
	private void addTableField(Map<String, Object> paramMap) throws Exception{
		String table_id = getStringValue(paramMap,"table_id");
		String user_id = getStringValue(paramMap,"user_id");
		String statusCode=getStringValue(paramMap,"status_code");
		statusCode=(statusCode==null || statusCode.equals(""))? "02" :statusCode;
		UppMetadata field=null;
		UppDicFnRela uppDicFnRela=new UppDicFnRela();
		UppFnMetadataProperty property=new UppFnMetadataProperty();
		List<Map<String, Object>> fieldsList=getTableInfo(paramMap);
		for (Map<String, Object> map : fieldsList) {
			Map<String, Object> params=new HashMap<String, Object>();
			
			String column_id = UID.next(MetaConstant.PREFIX_COLUMN);
			String field_id = UID.next(MetaConstant.PREFIX_COLUMN);
			params.put("table_id", table_id);
			params.put("column_id", column_id);
			if (getStringValue(map, "column_name")!=null) {
				byte[] bytes = getStringValue(map, "column_name").getBytes("iso-8859-1");
				params.put("column_name", new String (bytes,"utf-8"));
			}
			if (getStringValue(map, "column_desc")!=null) {
				byte[] bytes = getStringValue(map, "column_desc").getBytes("iso-8859-1");
				params.put("column_desc", new String (bytes,"utf-8") );
			}
			
			String dataType = getStringValue(map, "data_type_code");
			if(dataType!=null && (dataType.equalsIgnoreCase("VARCHAR2") || dataType.equalsIgnoreCase("CHAR"))){
				dataType="01";
			}
			if(dataType!=null && dataType.equalsIgnoreCase("NUMBER")){
				dataType="02";
			}
			if(dataType!=null && dataType.equalsIgnoreCase("DATE")){
				dataType="03";
			}
			String isDisplay = getStringValue(map, "is_display");
			params.put("is_display", isDisplay==null ? "N" : (isDisplay.equals("1") ? "Y" : "N"));
			
			params.put("data_type_code", dataType);
			params.put("column_type_code", getStringValue(map, "column_type_code"));
			params.put("data_scale", getStringValue(map, "data_scale"));
			params.put("is_editable","Y") ;
			params.put("data_length", getStringValue(map, "data_length"));
			params.put("is_nullable", getStringValue(map, "is_nullable"));
			params.put("default_value", getStringValue(map, "default_value"));
			params.put("display_order", getStringValue(map, "display_order"));
			tableManageDao.addTableField(params);
			
			//--------------字段元数据--------
			field=createMetadata("1010101", column_id,getStringValue(params, "column_name") , 
					user_id, table_id, statusCode,getStringValue(params, "display_order"),getStringValue(params, "column_desc"));
			uppMetadataDao.addThemeMeta(field);
			
			//------------功能属性元数据----------
			field=createMetadata("2010101", field_id,getStringValue(params, "column_name") , 
					user_id, getStringValue(paramMap,"page_id"), statusCode,getStringValue(params, "display_order"),getStringValue(params, "column_desc"));
			uppMetadataDao.addThemeMeta(field);
			
			property.setMetadata_id(field.getMetadata_id());
			property.setMetadata_name(field.getMetadata_name());
			property.setStatus_code(statusCode);
			property.setComponent_hide("N");
			property.setIf_editable("Y");
			property.setComponent_query("N");
			property.setIf_pk("N");
			property.setIf_must_input("N");
			managerFnMdProDao.addFnMdPro(property);
			uppDicFnRela.setDb_obj_id(column_id);
			uppDicFnRela.setMetadata_id(field_id);
			/*uppDicFnRela.setDic_metadata_id(column_id);
			uppDicFnRela.setFn_metadata_id(field_id);*/
			tableManageDao.addDicFnRela(uppDicFnRela);
			
			flowDao.delAndAddBlmbGn(property.getMetadata_id());
			
		}
		
	}
//	private String trimNull(String str){
//		return (str==null || str.equals("null") || str.equals("undefined"))? null :str;
//	}

	
	
}
