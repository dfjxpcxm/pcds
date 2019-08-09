package com.shuhao.clean.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ResourcesUtils {
	
	private  int FORNUM = 0;   //递归循环记录
	public  List<String> FUNC_LIST = new ArrayList<String>();
	
	
	/**
	 * 创建菜单构造方法 
	 * @param resource_id
	 * @param resource_name
	 * @param handler
	 * @param image_handler
	 * @param func
	 * @return
	 */
	private  String createFuncStart(String resource_id,String resource_name,String handler,String image){
		StringBuffer buffer = new StringBuffer();
		buffer.append(resource_id).append("_Window = Ext.extend(Ext.app.Module,{").append("\n");
		buffer.append("   constructor: function(config) {").append("\n");
		buffer.append("      config = config||{};").append("\n");
		buffer.append("      this.rxcfg = {};").append("\n");
		if (strIsNull(resource_id)) {
			buffer.append("      this.rxcfg.id = config.id;").append("\n");
		}else {
			buffer.append("      this.rxcfg.id = '"+ resource_id.trim()+"';").append("\n");
		}
		if (strIsNull(resource_name)) {
			buffer.append("      this.rxcfg.text = config.text;").append("\n");
		}else {
			buffer.append("      this.rxcfg.text = '"+resource_name.trim()+"';").append("\n");
		}
//		if (strIsNull(image)) {
//			buffer.append("      this.rxcfg.iconCls = config.iconCls;").append("\n");
//		}else {
//			buffer.append("      this.rxcfg.iconCls = '"+image.trim()+"';").append("\n");
//		}
		if (strIsNull(handler)) {
			buffer.append("      this.rxcfg.url = config.url;").append("\n");
		}else {
			buffer.append("      this.rxcfg.url = '"+handler.trim()+"';").append("\n");
		}
		
		buffer.append("      "+resource_id+"_Window.superclass.constructor.call(this, config);").append("\n");
		buffer.append("   },").append("\n");
		buffer.append("   init : function(){").append("\n");
		return buffer.toString();
	}
	
	
	private  String getOneMenuStart(String resource_id,String resource_name,String handler,String image){
		StringBuffer buffer = new StringBuffer();
		buffer.append("         this.launcher = {   ").append("\n");
		buffer.append("            text : this.rxcfg.text,   ").append("\n");
//		buffer.append("            iconCls : this.rxcfg.iconCls,   ").append("\n");
		buffer.append("            handler : this.createWindow,   ").append("\n");
		buffer.append("            scope : this   ").append("\n");
		buffer.append("         }   ").append("\n");
		buffer.append("      },   ").append("\n");
		buffer.append("      createWindow : function() {   ").append("\n");
		buffer.append("      this.url = this.rxcfg.url;   ").append("\n");
		buffer.append("      this.text = this.rxcfg.text;   ").append("\n");
		buffer.append("      this.id = this.rxcfg.id;   ").append("\n");
		buffer.append("      createWindow(this);   ").append("\n");
		buffer.append("   }   ").append("\n");
		return buffer.toString();
	}
	
	private  String createFuncEnd(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("   }   ").append("\n");
		buffer.append("});").append("\n");
		return buffer.toString();
	}
	
	/**
	 * 生成系统二级菜单开始标记
	 * @param resource_id
	 * @param resource_name
	 * @param handler
	 * @param image_handler
	 * @return
	 */
	private  String getTwoMenuStart(String resource_id,String resource_name,String handler,String image){
		StringBuffer buffer = new StringBuffer();
		buffer.append("   this.launcher = {   ").append("\n");
		buffer.append("      text : this.rxcfg.text,   ").append("\n");
//		buffer.append("      iconCls : this.rxcfg.iconCls,   ").append("\n");
		buffer.append("      handler : function() { return false;},   ").append("\n");
		buffer.append("      scope : this,   ").append("\n");
		buffer.append("      menu : {   ").append("\n");
		buffer.append("         items : [   ").append("\n");
		return buffer.toString();
	}
	
	
	/**
	 * 生成系统二级菜单方法
	 * @param resource_id
	 * @param resource_name
	 * @param handler
	 * @param image_handler
	 * @return
	 */
	private  String getTwoMenuFunc(String resource_id,String resource_name,String handler,String image){
		StringBuffer buffer = new StringBuffer();
		buffer.append("            {   ").append("\n");
		buffer.append("               text : '"+resource_name+"',   ").append("\n");
		buffer.append("               id : '"+resource_id+"',   ").append("\n");
//		buffer.append("               iconCls : '"+image+"',   ").append("\n");
		buffer.append("               handler : function() {   ").append("\n");
		buffer.append("                  this.text = '"+resource_name+"';   ").append("\n");
		buffer.append("                  this.id = '"+resource_id+"';   ").append("\n");
		buffer.append("                  this.url = '"+handler+"';   ").append("\n");
//		buffer.append("                  this.iconCls = '"+image+"';   ").append("\n");
		buffer.append("                  createWindow(this);   ").append("\n");
		buffer.append("               },   ").append("\n");
		buffer.append("               scope : this   ").append("\n");
		buffer.append("            }   ").append("\n");
		return buffer.toString();
	}
	
	/**
	 * 生成系统三级菜单
	 * @param resource_id
	 * @param resource_name
	 * @param handler
	 * @param image_handler
	 * @return
	 */
	private  String getThreeMenuFunc(String resource_id,String resource_name,String handler,String image){
		StringBuffer buffer = new StringBuffer();
		buffer.append("            {   ").append("\n");
		buffer.append("               text : '"+resource_name+"',   ").append("\n");
		buffer.append("               id : '"+resource_id+"',   ").append("\n");
//		buffer.append("               iconCls : '"+image+"',   ").append("\n");
		buffer.append("               handler : function() {   ").append("\n");
		buffer.append("                  return false;   ").append("\n");
		buffer.append("               },   ").append("\n");
		buffer.append("               scope : this,   ").append("\n");
		buffer.append("               menu : {   ").append("\n");
		buffer.append("                  items : [   ").append("\n");
		return buffer.toString();
	}
	
	
	/**
	 * 生成系统二/三级菜单结束标记
	 * @param resource_id
	 * @param resource_name
	 * @param handler
	 * @param image_handler
	 * @return
	 */
	private  String getTwoMenuEnd(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("         ]  ").append("\n");
		buffer.append("      }   ").append("\n");
		buffer.append("   }   ").append("\n");
		return buffer.toString();
	}
	
	
	
	public  String getBaseMenuResource(Map<Integer,Tree> treeStore){
		StringBuffer buffer = new StringBuffer();
		try {
			String xml = TreeUtil.CreateUtil(treeStore, treeStore.size());
			Document documents = DocumentHelper.parseText(xml);
			Element elements = documents.getRootElement();
			getMenuStr(elements,buffer);
			if(FORNUM == 1){
				String funcEnd = createFuncEnd();
				buffer.append(funcEnd);
			}else if(FORNUM == 2){
				String twoMenuEnd = getTwoMenuEnd();
				buffer.append(twoMenuEnd).append("\n");
				String funcEnd = createFuncEnd();
				buffer.append(funcEnd);
			}else if(FORNUM == 3){
				String twoMenuEnd = getTwoMenuEnd();
				buffer.append(twoMenuEnd).append("\n");
				buffer.append(twoMenuEnd).append("\n");
				String funcEnd = createFuncEnd();
				buffer.append(funcEnd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	
	private  void getMenuStr(Element elements,StringBuffer buffer){
		if (elements != null) {
			for (Iterator<?> iterator = elements.elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if(element != null){
					String resource_id = element.attributeValue("id");
					String resource_name = element.attributeValue("name");
					String handler  = element.attributeValue("handler");
					String image  = element.attributeValue("image");
					String [] str_ = resource_id.split("_");
					if(str_.length == 1){  //一级菜单
						if(FORNUM == 1){
							String oneMenuStart = getOneMenuStart(resource_id, resource_name, handler, image);
							buffer.append(oneMenuStart);
							buffer.append("});   ");
						}else if(FORNUM == 2){
							String twoMenuEnd = getTwoMenuEnd();
							buffer.append(twoMenuEnd).append("\n");
							String funcEnd = createFuncEnd();
							buffer.append(funcEnd);
						}else if(FORNUM == 3){
							String twoMenuEnd = getTwoMenuEnd();
							buffer.append(twoMenuEnd).append("\n");
							buffer.append(twoMenuEnd).append("\n");
							String funcEnd = createFuncEnd();
							buffer.append(funcEnd);
						}
						String str = createFuncStart(resource_id, resource_name, handler, image);
						buffer.append(str);
						FUNC_LIST.add(resource_id+"_Window");  //存储菜单方法名称
						FORNUM = 1;
					}else if(str_.length == 2){//二级菜单
						if(FORNUM == 1){
							String twoMenuStart = getTwoMenuStart(resource_id, resource_name, handler, image);
							buffer.append(twoMenuStart);
							if(!element.elementIterator().hasNext()){
								String twoMenuFunc = getTwoMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(twoMenuFunc);
							}else{
								String threeMenuFunc = getThreeMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(threeMenuFunc);
							}
						}else if(FORNUM == 2){
							if(!buffer.toString().trim().endsWith("[")){
								buffer.append(",");
							}
							if(element.elementIterator().hasNext()){
								String threeMenuFunc = getThreeMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(threeMenuFunc);
							}else{
								String twoMenuFunc = getTwoMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(twoMenuFunc);
							}
						}else if(FORNUM == 3){
							//上级结束时是三级菜单需要拼接结束标签
							String menuEnd = getTwoMenuEnd();
							buffer.append(menuEnd);
							
							if(!buffer.toString().trim().endsWith("[")){
								buffer.append(",");
							}
							if(element.elementIterator().hasNext()){
								String threeMenuFunc = getThreeMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(threeMenuFunc);
							}else{
								String twoMenuFunc = getTwoMenuFunc(resource_id, resource_name, handler, image);
								buffer.append(twoMenuFunc);
							}
						}
						FORNUM = 2;
					}else{ //三级菜单
						if(FORNUM == 2){
							if(!buffer.toString().trim().endsWith("[")){
								buffer.append(",");
							}
							String twoMenuFunc = getTwoMenuFunc(resource_id, resource_name, handler, image);
							buffer.append(twoMenuFunc);
							
						}else if(FORNUM == 3){
							if(!buffer.toString().trim().endsWith("[")){
								buffer.append(",");
							}
							String twoMenuFunc = getTwoMenuFunc(resource_id, resource_name, handler, image);
							buffer.append(twoMenuFunc);
						}
						FORNUM = 3;
					}
					getMenuStr(element,buffer);
				}
			}  //循环结束
		}
	}
	
	private  boolean strIsNull(String param){
		if (param == null || param.trim().equals("")) {
			return true;
		}
		return false;
	}
}
