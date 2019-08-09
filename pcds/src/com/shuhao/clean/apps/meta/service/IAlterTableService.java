package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.DbColumn;
import com.shuhao.clean.apps.meta.entity.UppMetadata;

/**
 * 
 * 类描述: 修改物理表业务逻辑接口
 * @author chenxiangdong
 * @创建时间：2015-1-16下午02:46:19
 */
public interface IAlterTableService {
	
	/**
	 * 根据上级元数据查询指定类型的下级元数据列表
	 * @param parent_metadata_id
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryMetadata(String parent_metadata_id, String md_cate_cd) throws Exception;
	
	/**
	 * 查询表的列信息
	 * @param owner_id 数据库id
	 * @param owner_id 用户id
	 * @param table_id 表id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTableColumnInfo(String database_id, String owner_id, String table_id) throws Exception;
	
	/**
	 * 添加物理表列
	 * @param dbColumn
	 * @throws Exception
	 */
	public void addColumn(DbColumn dbColumn) throws Exception;
	
	/**
	 * 编辑物理表列
	 * @param dbColumn
	 * @throws Exception
	 */
	public void editColumn(DbColumn dbColumn) throws Exception;
	
}
