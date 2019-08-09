package com.shuhao.clean.apps.model.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shuhao.clean.apps.model.ext.base.BaseExt;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.GlobalUtil;

public class ColumnModel extends BaseExt {
	private String ColumnHead;
	private String tmplType; //是否主模版
	
	private boolean isSelModel  = true;
	private boolean isAddRowNum  = false;
	private Map<String, Object> defaultAttr = new HashMap<String, Object>();
	
	public ColumnModel(List<Map<String,Object>> dataList,Map<String, Object> eleMap){
		this.tmplType = (String) eleMap.get("type");
		this.ColumnHead =  createColumnHead(dataList,(String) eleMap.get("flow_tmpl_id"));
	}
	
	/**
	 * 通过fields生成列表
	 * @param eleMap
	 */
	public ColumnModel(Map<String, Object> eleMap){
		this.ColumnHead =  createColumnHead((List<BaseField>)eleMap.get("fields"));
	}
	
	private String createColumnHead(List<BaseField> dataList){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < dataList.size(); i++) {
			BaseField field =dataList.get(i);
			String type = field.getCtype();
			//只有隐藏域不显示
			boolean isHide =  (type.equals("hidden") || field.isHidden())? true : false;
		
			String fieldName = field.getName();
			if(i>0){
				buffer.append(",");
			}
			if (ExtConstant.EXT_MONEYFIELD.equals(type)) {
				buffer.append("{").append("id :'").append(fieldName).append("',header : '").append(field.getFieldLabel()).append("',hidden:").append(isHide).append(",dataIndex :'").append(fieldName).append("',align:'right',renderer : fmoney}");
			}else if(ExtConstant.EXT_NUMBERFIELD.equals(type)){
				buffer.append("{").append("id :'").append(fieldName).append("',header : '").append(field.getFieldLabel()).append("',hidden:").append(isHide).append(",dataIndex :'").append(fieldName).append("',align:'right'}");
			}else{
				buffer.append("{").append("id :'").append(fieldName).append("',header : '").append(field.getFieldLabel()).append("',hidden:").append(isHide).append(",dataIndex :'").append(fieldName).append("'}");
			}
			buffer.append(enter);
		}
		return buffer.toString();
	}
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id).append("Cm = new Ext.grid.ColumnModel({columns : [").append(enter);
		if(isAddRowNum){
		buffer.append(" new Ext.grid.RowNumberer(),").append(enter);
		}
		if(isSelModel){
			buffer.append(" winRoleSm = new Ext.grid.CheckboxSelectionModel(),").append(enter);
		}
		buffer.append(ColumnHead).append(enter);
		buffer.append("]");
		if(!defaultAttr.isEmpty()){
			buffer.append(", defaults: {").append(enter);
			for (Entry<String, Object> attr : defaultAttr.entrySet()) {
				if(attr.getValue() instanceof Boolean){
					buffer.append(attr.getKey()).append(" : ").append(attr.getValue()).append(",");
				}else if(attr.getValue() instanceof String){
					buffer.append(attr.getKey()).append(" : '").append(attr.getValue()).append("',");
				}else{
					buffer.append(attr.getKey()).append(" : ").append(attr.getValue()).append(",");
				}
			}
			buffer.append("a:0}").append(enter);
		}
		buffer.append("})");
		return buffer.toString();
	}
	
	private String createColumnHead(List<Map<String,Object>> dataList, String flow_tmpl_id){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < dataList.size(); i++) {
			Map<String,Object> map =dataList.get(i);
			String type = (String)map.get("component_type_id");
			//String hide = (String)map.get("is_hidden");
			//boolean isHide = hide.equals("Y") ? true : false;
			//只有隐藏域不显示
			boolean isHide =  type.equals("hidden") ? true : false;
			String fieldName = String.valueOf(map.get("field_name")).toLowerCase();
			if ("moneyfield".equals(type)) {
				buffer.append("{").append("id :'").append(fieldName).append("',header : '").append(map.get("component_label")).append("',hidden:").append(isHide).append(",dataIndex :'").append(fieldName).append("',renderer : fmoney}");
			}else{
				buffer.append("{").append("id :'").append(fieldName).append("',header : '").append(map.get("component_label")).append("',hidden:").append(isHide).append(",dataIndex :'").append(fieldName).append("'}");
			}
			if (i == dataList.size()-1) {
				buffer.append(enter);
			}else {
				buffer.append(",").append(enter);
			}
		}
		
		//拼接审批信息　
		if(tmplType!=null && "parent".equals(tmplType) && GlobalUtil.isNotNull(flow_tmpl_id)){
			//申请信息：flow_status_code,apply_user_id,apply_time
			//buffer.append(",{").append("id :'flow_status_code',header : '").append("数据状态").append("',dataIndex :'flow_status_code',width:65,renderer : datatype}").append(enter);
			
			//审批信息
			//创建人 , create_user
			buffer.append(",{id :'apply_user_id',header : '申请人',dataIndex :'apply_user_id',width:65}").append(enter);
			//创建时间
			buffer.append(",{id :'create_date',header : '申请时间',dataIndex :'create_date',width:65}").append(enter);
			buffer.append(",{id :'task_id',header : '任务编号',dataIndex :'task_id',hidden : true}").append(enter);
			buffer.append(",{id :'tmpl_id',header : '模板编号',dataIndex :'tmpl_id',hidden : true}").append(enter);
			buffer.append(",{id :'next_node_id',header : '审批节点',dataIndex :'next_node_id',hidden : true}").append(enter);
			buffer.append(",{id :'task_status',header : '流程状态',dataIndex :'task_status',renderer : approvetype}").append(enter);
		}
		return buffer.toString();
	}
	/**
	 * @return the columnHead
	 */
	public String getColumnHead() {
		return ColumnHead;
	}
	/**
	 * @param columnHead the columnHead to set
	 */
	public void setColumnHead(String columnHead) {
		ColumnHead = columnHead;
	}
	/**
	 * @return the tmplType
	 */
	public String getTmplType() {
		return tmplType;
	}
	/**
	 * @param tmplType the tmplType to set
	 */
	public void setTmplType(String tmplType) {
		this.tmplType = tmplType;
	}

	public boolean isSelModel() {
		return isSelModel;
	}

	public void setSelModel(boolean isSelModel) {
		this.isSelModel = isSelModel;
	}

	public Map<String, Object> getDefaultAttr() {
		return defaultAttr;
	}

	public void addDefaultAttr(String name,Object value){
		this.defaultAttr.put(name, value);
	}

	public boolean isAddRowNum() {
		return isAddRowNum;
	}

	public void setAddRowNum(boolean isAddRowNum) {
		this.isAddRowNum = isAddRowNum;
	}
}
