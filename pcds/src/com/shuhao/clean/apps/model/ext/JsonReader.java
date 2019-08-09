package com.shuhao.clean.apps.model.ext;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.model.ext.base.BaseExt;

public class JsonReader extends BaseExt {
	private String root = "results";
	private String totalProperty = "totalCount";
	private String jsonData;
	private boolean hasWorkFlow = true;
	
	public JsonReader(List<Map<String,Object>> dataList){
		this.jsonData = getJsonData(dataList);
	}
	
	/**
	 * 表单相关的jsonreader
	 * @param dataList
	 * @param form
	 */
	public JsonReader(List<Map<String,Object>> dataList,String form){
		this.jsonData = getJsonData(dataList,form);
	}
	
	/**
	 * 是否加上默认的Field
	 * @param dataList
	 * @param addDefalut
	 */
	public JsonReader(List<Map<String,Object>> dataList,boolean addDefalut){
		this.jsonData = getJsonData(dataList,addDefalut);
	}
	
	/**
	 * BaseField构建reader
	 * @param dataList
	 * @param flag
	 */
	public JsonReader(List<BaseField> dataList,int flag){
		this.jsonData = getJsonReader(dataList);
	}
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.id).append("Reader = new Ext.data.JsonReader({").append(enter);
		buffer.append("root : '").append(this.root).append("',").append(enter);
		buffer.append("totalProperty : '").append(this.totalProperty).append("'").append(enter);
		buffer.append("},").append(enter);
		buffer.append(jsonData).append(")");
		return buffer.toString();
	}
	
	public String getJsonData(List<Map<String,Object>> dataList ){
		return getJsonData(dataList,true);
	}
	
	public String getJsonReader(List<BaseField> dataList){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(enter);
		for (int i = 0; i < dataList.size(); i++) {
			BaseField field =dataList.get(i);
			buffer.append("{").append("name :'").append(field.getName()!=null?field.getName().toLowerCase():"").append("',mapping : '").append(field.getName()!=null?field.getName().toLowerCase():"").append("',type :'").append("string").append("'}");
			if (i == dataList.size()-1) {
				buffer.append(enter);
			}else {
				buffer.append(",").append(enter);
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	public String getJsonData(List<Map<String,Object>> dataList,boolean addDefault){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(enter);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String,Object> map =dataList.get(i);
			buffer.append("{").append("name :'").append(map.get("field_name")!=null?((String)map.get("field_name")).toLowerCase():"").append("',mapping : '").append(map.get("field_name")!=null?((String)map.get("field_name")).toLowerCase():"").append("',type :'").append("string").append("'}");
			if (i == dataList.size()-1) {
				buffer.append(enter);
			}else {
				buffer.append(",").append(enter);
			}
		}
		
		if(addDefault && hasWorkFlow){
			//申请信息flow_status_code,apply_user_id,apply_time
			//buffer.append(",{").append("name :'").append("flow_status_code").append("',mapping :'").append("flow_status_code").append("'}");
			
			buffer.append(",{name :'apply_user_id',mapping :'apply_user_id'}"); //create_user
			buffer.append(",{name :'create_date',mapping :'create_date'}");
			//审批信息
			buffer.append(",{name :'task_id',mapping :'task_id'}");
			buffer.append(",{name :'tmpl_id',mapping :'tmpl_id'}");
			buffer.append(",{name :'next_node_id',mapping :'next_node_id'}");
			buffer.append(",{name :'task_status',mapping :'task_status'}");
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	public String getJsonData(List<Map<String,Object>> dataList,String form){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(enter);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String,Object> map =dataList.get(i);
			String type = (map.get("type")== null ||map.get("type").equals(""))  ? "string" : map.get("type").toString();
			if (type.equals("date")) {
				buffer.append("{").append("name :'").append((String)map.get("field_id")+"_"+((String)map.get("field_name")).toLowerCase()).append("',mapping : '").append(((String)map.get("field_name")).toLowerCase()).append("',type :'").append(type).append("',dateFormat :'").append("Y-m-d").append("'}");
			}else if(type.equals("money")){
				buffer.append("{").append("name :'").append((String)map.get("field_id")+"_"+((String)map.get("field_name")).toLowerCase()).append("',mapping : '").append(((String)map.get("field_name")).toLowerCase()).append("',convert :fmoney}");
			}else{
				buffer.append("{").append("name :'").append((String)map.get("field_id")+"_"+((String)map.get("field_name")).toLowerCase()).append("',mapping : '").append(((String)map.get("field_name")).toLowerCase()).append("',type :'").append(type).append("'}");
			}
			if (i == dataList.size()-1) {
				buffer.append(enter);
			}else {
				buffer.append(",").append(enter);
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	public void setHasWorkFlow(boolean hasWorkFlow) {
		this.hasWorkFlow = hasWorkFlow;
	}
}
