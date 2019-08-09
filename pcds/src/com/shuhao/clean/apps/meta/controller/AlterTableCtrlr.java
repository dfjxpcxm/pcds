package com.shuhao.clean.apps.meta.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shuhao.clean.apps.meta.entity.DbColumn;
import com.shuhao.clean.apps.meta.service.IAlterTableService;
import com.shuhao.clean.base.BaseCtrlr;
/**
 * 
 * 类描述: 修改物理表Action
 * @author chenxiangdong
 * @创建时间：2015-1-16下午02:44:52
 */
@Controller
@RequestMapping("/meta/alterTable")
public class AlterTableCtrlr extends BaseCtrlr {
	
	@Autowired
	private IAlterTableService alterTableService;
	
	@RequestMapping
	public String toPage() {
		return "meta/alter_table";
	}
	
	/**
	 * 根据上级元数据id查询指定类型的下级元数据列表
	 * @param parent_metadata_id
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMetadata")
	@ResponseBody
	public Map<String, Object> queryMetadata(String parent_metadata_id, String md_cate_cd) throws Exception {
		try {
			if(md_cate_cd==null){
				return null;
			}
			return super.getJsonResultMap(this.alterTableService.queryMetadata(parent_metadata_id, md_cate_cd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 查询物理表的列字段信息
	 * @param database_id
	 * @param owner_id
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryColumns")
	@ResponseBody
	public Map<String, Object> queryMetadata(String database_id, String owner_id, String table_id) throws Exception {
		try {
			return super.getJsonResultMap(this.alterTableService.queryTableColumnInfo(database_id, owner_id, table_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 添加物理列字段
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(DbColumn dbColumn) throws Exception {
		try {
			this.alterTableService.addColumn(dbColumn);
			return super.doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 编辑物理列字段
	 * @param dbColumn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(DbColumn dbColumn) throws Exception {
		try {
			this.alterTableService.editColumn(dbColumn);
			return super.doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
}
