/**
 * FileName:     ExtEvent.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-9 下午3:55:48 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-9       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.model.ext.base;

import org.apache.log4j.Logger;

import com.shuhao.clean.apps.meta.entity.XmlObject;
import com.shuhao.clean.apps.model.ext.Ext;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class ExtEvent implements Ext{
	
	private static final Logger logger = Logger.getLogger(ExtEvent.class);

	
	public static String EXE_TYPE_AJAX="ajax";
	public static String EXE_TYPE_FN="fn";
	public static String EXE_TYPE_JS="js";

	private String id;//controller的ID
	private String name;
	//事件对象
	private XmlObject eventObject ;
	

	public ExtEvent(String id,String name){
		this.id = id;
		this.name = name;
	}
	
//		listeners:{
//			blur:function(t){
//			   Ext.Ajax.request({
//				   url : pathUrl + '/bckTrackAjax/doEvent/',
//				   method: 'POST',
//				   params: {id: t.getValue()},
//				   success: function(response, opts) {
//				      var json=Ext.util.JSON.decode(response.responseText);
//					  if (json.success) {
//						  var arys =json.results;
//						  for(var i=0;i<arys.length;i++){
//							  var ary = dataForm.find('name',arys[i].name);
//							  ary[0].setValue(arys[i].value)
//						  }
//						  Ext.Msg.alert('提示','删除成功');
//					  } else {
//						  Ext.Msg.alert('错误',json.info);
//					  }
//				   } 
//				});
//			}
//		}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.Ext#output()
	 */
	public String output() {
		StringBuffer sb =new StringBuffer();
		sb.append(getEventString());
		return sb.toString();
	}
	
	private String getEventString(){
		StringBuffer sb = new StringBuffer();
		if(this.eventObject.getExeType().equals(EXE_TYPE_AJAX)){
			sb.append(",listeners:{");
			sb.append("	"+getEventObject().getEventName()+":function(t){");
			sb.append("	   if(t.getValue().trim().length==0){return ;}");
			sb.append("	   Ext.Ajax.request({");
			sb.append("		   url : pathUrl + '/pageManager/doEvent/"+this.id+"/"+this.name+"',");
			sb.append("		   method: 'POST',");
			sb.append("		   params: {value: t.getValue()},");
			sb.append("		   success: function(response, opts) {");
			sb.append("		      var json=Ext.util.JSON.decode(response.responseText);");
			sb.append("		      var arys =json.results;");
			sb.append("			  if (arys) {");
			sb.append("				  for(var i=0;i<arys.length;i++){");
			sb.append("					  var ary = dataForm.find('realName',arys[i].name);");
			sb.append("					  if(ary){");
			sb.append("					      ary[0].setValue(arys[i].value);");
			sb.append("					  }");
			sb.append("				  }");
			sb.append("			  } ");
			sb.append("		   } ");
			sb.append("		});");
			sb.append("	}");
			sb.append("} ");
		}else if(this.eventObject.getExeType().equals(EXE_TYPE_FN)){
			
		}else if(this.eventObject.getExeType().equals(EXE_TYPE_JS)){
			sb.append(",listeners:{");
			sb.append("	"+getEventObject().getEventName()+":function("+getEventObject().getParam()+"){");
			sb.append(getEventObject().getOutput());
			sb.append(" }");
			sb.append("}");
		}else{
			logger.error("错误的事件处理方式："+this.eventObject.getExeType());
		}
		return sb.toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public XmlObject getEventObject() {
		return eventObject;
	}

	public void setEventObject(XmlObject eventObject) {
		this.eventObject = eventObject;
	}
}
