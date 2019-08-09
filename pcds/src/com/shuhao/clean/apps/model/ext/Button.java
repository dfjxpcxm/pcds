package com.shuhao.clean.apps.model.ext;

import java.util.Map;


import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.GlobalUtil;



public  class Button extends BoxComponent {
	
	protected String text;
	protected String iconCls;
	protected String handler = "alert('未定义函数');";
	
	
	public Button(){}
	
	//增加是否拥有审批权限的限制,提交，编辑，删除的时候限制
	public String validApprove(){
		StringBuffer sb = new StringBuffer();
		sb.append("if(hasApprove && hasApprove == 'Y' ){");
		sb.append("	var applyUser = records[0].get('apply_user_id');");
		sb.append("	if(applyUser && applyUser!=curtUserId && curtUserId!='00000'){");
		sb.append("		Ext.Msg.alert('提示信息','提交人非当前用户，无法操作.');");
		sb.append("		return ;");
		sb.append("}}");
		return sb.toString();
	}
	
	//批量校验是否在自己审批的
	public String validRecordsApprove(){
		StringBuffer sb = new StringBuffer();
		sb.append("if(hasApprove && hasApprove == 'Y' ){");
		sb.append("for (var i = 0; i < records.length; i++) {");
		sb.append("	var applyUser = records[i].get('apply_user_id');");
		sb.append("	if(applyUser && applyUser!=curtUserId && curtUserId!='00000'){");
		sb.append("		Ext.Msg.alert('提示信息','第['+(i+1)+']条记录提交人非当前用户，无法操作.');");
		sb.append("		return ;");
		sb.append("}}}");
		return sb.toString();
	}
	
	/**
	 * 是否审批中,只有审批中的不能修改，其他都能修改
	 * @param name
	 * @return
	 */
	private static String isApproved(String name){
		StringBuffer buffer = new StringBuffer();
		buffer.append("if("+name+" && "+name+" == '01'){ ");
		buffer.append("   Ext.Msg.alert('错误','选中的有未完成审批的数据，操作失败，请先[撤回]！'); ");
		buffer.append("   return false; ");
		buffer.append("}");
		return buffer.toString();
	}
	
	public Button(Map<String, Object> temp){
		String button_id = GlobalUtil.getStringValue(temp, "button_id");
		String tmpl_id = GlobalUtil.getStringValue(temp, "tmpl_id");
		String button_func_cd = GlobalUtil.getStringValue(temp, "button_func_cd");
		String rela_metadata_id   = GlobalUtil.getStringValue(temp, "rela_metadata_id");
		String type   = GlobalUtil.getStringValue(temp, "type");
		String prt_tmpl_id   = GlobalUtil.getStringValue(temp, "prt_tmpl_id");
		this.text = GlobalUtil.getStringValue(temp, "button_name");
		this.iconCls = GlobalUtil.getStringValue(temp, "icon_path");
		//复制和新增，关联新增窗口
		if(ExtConstant.EXT_BUTTON_TYPE_ADD.equals(button_func_cd)){
			if(GlobalUtil.isNull(rela_metadata_id)){
				this.handler=" Ext.Msg.alert('提示','未配置关联表单！！！'); ";
			}else{
				StringBuffer handler = new StringBuffer();
				if("child".equals(type)){
					handler.append("var parentGrid = Ext.getCmp('dataGrid"+prt_tmpl_id+"');");
					handler.append(" var r = parentGrid.getSelectionModel().getSelections();");
					handler.append(" if(r==null||r.length<1){");
					handler.append(" Ext.Msg.alert('提示','请先选择一条数据');");
					handler.append(" return;");
					handler.append(" }");
					handler.append("  var status = r[0].get('task_status');");
					handler.append(isApproved("status"));
				}
				handler.append("var Win_"+rela_metadata_id+" = new Window"+rela_metadata_id+"();");
				handler.append(this.enter+"Win_"+rela_metadata_id+".show();");
				if("child".equals(type)){
					handler.append("setRelaValue('"+tmpl_id+"',Win_"+rela_metadata_id+",r[0])");
				}else{
					handler.append("setRelaValue('"+tmpl_id+"',Win_"+rela_metadata_id+",null)");
				}
				this.handler = handler.toString();
			}
		}else if(ExtConstant.EXT_BUTTON_TYPE_CANCEL.equals(button_func_cd)){
			this.handler = " this.ownerCt.ownerCt.destroy()";
		}else if(ExtConstant.EXT_BUTTON_TYPE_EDIT.equals(button_func_cd)){
			if(GlobalUtil.isNull(rela_metadata_id)){
				this.handler=" Ext.Msg.alert('提示','未配置关联表单！！！'); ";
			}else{
				StringBuffer buffer = new StringBuffer();
				buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
				buffer.append("if(!records||records.length==0){");
				buffer.append("Ext.Msg.alert('提示','请选择记录');");
				buffer.append("return;}");
				
				//增加审批人限制--编辑
				buffer.append(validApprove());
				
				if("parent".equals(type)){
					buffer.append(" var status = records[0].get('task_status');");
				}else{
					buffer.append(" var parRec = Ext.getCmp('dataGrid"+prt_tmpl_id+"').getSelectionModel().getSelections();");
					buffer.append(" var status = parRec[0].get('task_status');");
				}
				buffer.append(isApproved("status"));
				
				buffer.append(" var Win_"+rela_metadata_id+" = new Window"+rela_metadata_id+"();"+this.enter+"Win_"+rela_metadata_id+".show(); ");
				buffer.append(" doLoad(Win_"+rela_metadata_id+",records[0],'"+tmpl_id+"','"+button_id+"'); ");
				this.handler = buffer.toString();
			}
		}else if(ExtConstant.EXT_BUTTON_TYPE_COPY.equals(button_func_cd)){ //复制
			if(GlobalUtil.isNull(rela_metadata_id)){
				this.handler=" Ext.Msg.alert('提示','未配置关联表单！！！'); ";
			}else{
				StringBuffer buffer = new StringBuffer();
				buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
				buffer.append("if(!records||records.length==0){");
				buffer.append("Ext.Msg.alert('提示','请选择记录');");
				buffer.append("return;}");
				
				if("parent".equals(type)){
					buffer.append(" var status = records[0].get('task_status');");
				}else{
					buffer.append(" var parRec = Ext.getCmp('dataGrid"+prt_tmpl_id+"').getSelectionModel().getSelections();");
					buffer.append(" var status = parRec[0].get('task_status');");
					buffer.append(isApproved("status"));
				}
				
				buffer.append(" var Win_"+rela_metadata_id+" = new Window"+rela_metadata_id+"();"+this.enter+"Win_"+rela_metadata_id+".show(); ");
				buffer.append(" doLoad(Win_"+rela_metadata_id+",records[0],'"+tmpl_id+"','"+button_id+"'); ");
				this.handler = buffer.toString();
			}
		}else if(ExtConstant.EXT_BUTTON_TYPE_SAVE.equals(button_func_cd)){
			this.handler = " doSave(this,'"+tmpl_id+"','"+type+"','"+button_id+"');";
		}else if(ExtConstant.EXT_BUTTON_TYPE_DEL.equals(button_func_cd)){
			StringBuffer buffer = new StringBuffer();
			//判断是否选择记录
			buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
			buffer.append("if(!records||records.length==0){");
			buffer.append("Ext.Msg.alert('提示','请选择记录');");
			buffer.append("return;}");
			
			//增加审批人限制-删除
			buffer.append(validRecordsApprove());
			
			if("parent".equals(type)){
				buffer.append("var business_nos = '';").append(enter);
				buffer.append("var task_ids = '';").append(enter);
				buffer.append(" for(var i=0;i<records.length;i++){");
				buffer.append("  var tmp=records[i];");
				buffer.append("  var business_no = tmp.get('business_no');"); //业务编号
				buffer.append("  var task_id = tmp.get('task_id');");//流程任务编号
				buffer.append("  var status_code = tmp.get('task_status');");
				buffer.append(isApproved("status_code")); //是否审批中,每条删除的记录都要判断状态
				buffer.append("  if(status_code == '10'){Ext.Msg.alert('提示','审批通过的数据不能删除');return;}");
				buffer.append("  business_nos += business_no + '_'; ");
				buffer.append("  task_ids += task_id + '_'; ");
				buffer.append(" } ");
			}else{
				//判断主模版状态
				buffer.append("var p_records = Ext.getCmp('dataGrid"+prt_tmpl_id+"').getSelectionModel().getSelections();");
				buffer.append("if(p_records == null || p_records.length <= 0){").append(enter);
				buffer.append("Ext.Msg.alert('提示','请选择一行数据！');").append(enter);
				buffer.append("return;").append(enter);
				buffer.append("}").append(enter);
				buffer.append(" var status = p_records[0].get('task_status');");
				buffer.append(isApproved("status")); //是否审批中
				buffer.append(" if(status == '10'){Ext.Msg.alert('提示','审批通过的数据不能删除');return;}");
				
				//获取子模版选中的数据
				buffer.append("var business_nos = '';").append(enter);
				buffer.append("var task_ids = '';").append(enter);
				buffer.append(" for(var i=0;i<records.length;i++){");
				buffer.append("  var tmp=records[i];");
				buffer.append("  var business_no = tmp.get('business_no');");
				buffer.append("  business_nos += business_no + '_'; ");
				buffer.append(" } ");
			}
			
			buffer.append("Ext.Msg.confirm('确认','确认删除？',function(btn){");
			buffer.append("if(btn=='yes'){");
			buffer.append("doDelete(business_nos,task_ids,'"+tmpl_id+"');}");
			buffer.append("});");
			this.handler =  buffer.toString();
		}else if(ExtConstant.EXT_BUTTON_TYPE_UPD.equals(button_func_cd)){
			this.handler = " doUpdate(this,'"+tmpl_id+"','"+button_id+"');";
		}else if(ExtConstant.EXT_BUTTON_TYPE_APPLY_AGREE.equals(button_func_cd)){
			StringBuffer buffer = new StringBuffer();
			buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
			buffer.append("if(!records||records.length==0){");
			buffer.append("Ext.Msg.alert('提示','请选择记录');");
			buffer.append("return;}");
			buffer.append("approveFlow('05','0',records,'"+tmpl_id+"');");
			this.handler = buffer.toString();
		}else if(ExtConstant.EXT_BUTTON_TYPE_APPLY_RECALL.equals(button_func_cd)){
			StringBuffer buffer = new StringBuffer();
			buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
			buffer.append("if(!records||records.length==0){");
			buffer.append("Ext.Msg.alert('提示','请选择记录');");
			buffer.append("return;}");
			buffer.append("approveFlow('05','1',records,'"+tmpl_id+"');");
			this.handler = buffer.toString();
		}else if(ExtConstant.EXT_BUTTON_TYPE_APPLY_BACK.equals(button_func_cd)){
			StringBuffer buffer = new StringBuffer();
			buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
			buffer.append("if(!records||records.length==0){");
			buffer.append("Ext.Msg.alert('提示','请选择记录');");
			buffer.append("return;}");
			buffer.append("backApprove(records);");
			this.handler = buffer.toString();
		}else if(ExtConstant.EXT_BUTTON_TYPE_IMP.equals(button_func_cd)){
			if(GlobalUtil.isNull(rela_metadata_id)){
				this.handler=" Ext.Msg.alert('提示','未配置关联Excel模板！！！'); ";
			}else{
				StringBuffer handler = new StringBuffer();
				if("child".equals(type)){
					handler.append("var parentGrid = Ext.getCmp('dataGrid"+prt_tmpl_id+"');");
					handler.append(" var r = parentGrid.getSelectionModel().getSelections();");
					handler.append(" if(r==null||r.length<1){");
					handler.append(" Ext.Msg.alert('提示','请先选择一条数据');");
					handler.append(" return;");
					handler.append(" }");
					handler.append("  var status = r[0].get('task_status');");
					//判断审批中
					handler.append(isApproved("status"));

					handler.append("var relaValues = getRelaValues('"+tmpl_id+"',r[0]);");
					handler.append("doImportData('"+tmpl_id+"','"+rela_metadata_id+"','"+button_id+"',relaValues);");
				}else{
					handler.append("doImportData('"+tmpl_id+"','"+rela_metadata_id+"','"+button_id+"','null');");
				}
				this.handler = handler.toString();
			}
		}else if(ExtConstant.EXT_BUTTON_TYPE_EXP.equals(button_func_cd)){
			if(GlobalUtil.isNull(rela_metadata_id)){
				this.handler=" Ext.Msg.alert('提示','未配置关联Excel模板！！！'); ";
			}else{
				StringBuffer handler = new StringBuffer();
				handler.append("doExportData('"+tmpl_id+"','"+rela_metadata_id+"','"+button_id+"');");
				this.handler = handler.toString();
			}
		}else if(ExtConstant.EXT_BUTTON_TYPE_VALID.equals(button_func_cd)){
			StringBuffer buffer = new StringBuffer();
			buffer.append("var records = this.ownerCt.ownerCt.getSelectionModel().getSelections();");
			buffer.append("if(!records||records.length==0){");
			buffer.append("Ext.Msg.alert('提示','请选择记录');");
			buffer.append("return;}");
			buffer.append("doValidData(records,'"+tmpl_id+"');");
			this.handler =  buffer.toString();
		}
	}
	
	
	public Button(String type,String templateId){
		if(type.equals(ExtConstant.EXT_BUTTON_TYPE_ADD)){
			this.text = "新增";
			this.iconCls = "add";
			this.handler = "doAddData"+templateId+"();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_UPD)){
			this.text = "编辑";
			this.iconCls = "edit";
			this.handler = "doEditData"+templateId+"();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_DEL)){
			this.text = "删除";
			this.iconCls = "delete";
			this.handler = "doDelData"+templateId+"();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_QUE)){
			this.text = "查询";
			this.iconCls = "query";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_IMP)){
			this.text = "导入";
			this.iconCls = "importData";
			this.handler = "doImportData"+templateId+"();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_EXP)){
			this.text = "导出";
			this.iconCls = "exportData";
			this.handler = "doExportData('"+templateId+"');";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_SUBMIT)){
			this.text = "确定";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_CENCEL)){
			this.text = "取消";
			this.handler = "window.destroy();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_AGREE)){
			this.text = "通过";
			this.iconCls = "agree";
			this.handler  = "approveFlow()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_BACK)){
			this.text = "撤回";
			this.iconCls = "back";
			this.handler  = "backApprove()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_RECALL)){
			this.text = "退回";
			this.iconCls = "back";
			this.handler  = "approveFlow()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_UP)){
			this.text = "提交";
			this.iconCls = "submit";
			this.handler  = "submitApprove()";
		}
	}
	
	public Button(String type){
		if(type.equals(ExtConstant.EXT_BUTTON_TYPE_ADD)){
			this.text = "新增";
			this.iconCls = "add";
			this.handler = "doAddData();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_UPD)){
			this.text = "编辑";
			this.iconCls = "edit";
			this.handler = "doEditData();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_DEL)){
			this.text = "删除";
			this.iconCls = "delete";
			this.handler = "doDelData();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_QUE)){
			this.text = "查询";
			this.iconCls = "query";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_IMP)){
			this.text = "导入";
			this.iconCls = "importData";
			this.handler = "doImportData();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_EXP)){
			this.text = "导出";
			this.iconCls = "exportData";
			this.handler = "doExportData();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_SUBMIT)){
			this.text = "确定";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_CENCEL)){
			this.text = "取消";
			this.handler = "window.destroy();";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_AGREE)){
			this.text = "通过";
			this.iconCls = "agree";
			this.handler  = "approveFlow()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_BACK)){
			this.text = "撤回";
			this.iconCls = "back";
			this.handler  = "backApprove()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_RECALL)){
			this.text = "退回";
			this.iconCls = "back";
			this.handler  = "approveFlow()";
		}else if(type.equals(ExtConstant.EXT_BUTTON_TYPE_APPLY_UP)){
			this.text = "提交";
			this.iconCls = "submit";
			this.handler  = "submitApprove()";
		}
	}
	
	public String isApprove(){
		StringBuffer  sb = new StringBuffer();
		sb.append("validApprove(record); ");
		sb.append("if(hasApprove && hasApprove=='Y'){}");
		return null;
	}
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{").append(enter);
		buffer.append(this.baseParams());
		if(isNotNull(this.text)){
			buffer.append(",").append(enter);
			buffer.append("text : '").append(this.text).append("'");
		}
		if(isNotNull(this.iconCls)){
			buffer.append(",").append(enter);
			buffer.append("iconCls : '").append(this.iconCls).append("'");
		}
		buffer.append(",").append(enter);
		buffer.append("handler : function (){").append(enter);
		if(isNotNull(this.handler)){
			buffer.append(this.handler).append(enter);
		}
		buffer.append("}").append(enter);
		buffer.append("}");
		return buffer.toString();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
