package com.shuhao.clean.utils.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.shuhao.clean.apps.meta.entity.FnEvent;
import com.shuhao.clean.apps.meta.entity.JsEvent;
import com.shuhao.clean.apps.meta.entity.XmlObject;
import com.shuhao.clean.apps.model.ext.base.ExtEvent;
import com.shuhao.clean.apps.validate.entity.PageEvent;

/**
 * 针对xml string处理
 * @Description: TODO
 * 
 * @author: gongzhiyang
 */
public class XmlStrUtil {

	private static final Logger logger = Logger.getLogger(XmlStrUtil.class);

	private static Document document;
	
	private static PageEvent pageEvent;
	
	public static Document getFnDocument(){
		if(document==null){
			logger.error("未执行docuemnt初始化,请先执行XmlStrUtil.initDocument(PageEvent event).");
		}
		return document;
	}
	
	public static Document initDocument(PageEvent event) {
		try {
			logger.info("开始解析事件配置文件...");
			if(pageEvent==null){
				pageEvent = event;
				document = getDocument(event.getEvent_file());
			}else{
				if(!pageEvent.getUpdate_time().equals(event.getUpdate_time())){
					pageEvent = event;
					document = getDocument(pageEvent.getEvent_file());
				}
			}
			
			if(document==null){
				document = getDocument(pageEvent.getEvent_file());
			}
			logger.info("解析事件配置文件完成...");
		} catch (Exception e) {
			logger.error("解析编号为["+pageEvent.getFile_id()+"]xml异常:"+e.getMessage());
		}
		return document;
	}
 
	/**
	 * xml字符串转换为document
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String xmlString) throws Exception {
		Document document = DocumentHelper.parseText(xmlString);
		return document;
	}
	
	/**
	 * 查询功能元数据xml
	 * @param id
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public static List<FnEvent> getFnEventsFromXml(String pid)throws Exception{
		List list = getFnDocument().selectNodes("/fnmetas/fnmeta[@pid='"+pid+"']" );
		List<FnEvent> events = new ArrayList<FnEvent>();
		if(list.size()>0){
			 for (int i = 0; i < list.size(); i++) {
				 Element element = (Element)list.get(i);
				 FnEvent event = new FnEvent();
				 event.transXmlObject(element.element("event"));
				 events.add(event);
			}
		}
		return events;
	}
	
	/**
	 * 查询某功能配置的事件
	 * @param id 元数据ID
	 * @return
	 * @throws Exception
	 */
	public static FnEvent getFnEvent(String id)throws Exception{
		List list = getFnDocument().selectNodes("/fnmetas/fnmeta/event[@id='"+id+"']" );
		if(list.size()>0){
			Element element = (Element)list.get(0);
			FnEvent event = new FnEvent();
			event.transXmlObject(element);
			return event;
		}
		return null;
	}
	
	/**
	 * 按上级ID，和控件名查找element
	 * @param pid
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static XmlObject getFnEvent(String pid,String name)throws Exception{
		if(pid==null || name==null){
			return null;
		}
		List list = getFnDocument().selectNodes("/fnmetas/fnmeta[@pid='"+pid+"']/field[@name='"+name+"']");
		if(list.size()>0){
			Element element = (Element)list.get(0);
			//取事件节点
			Element eventEl = element.element("event");
			XmlObject object = null;
			if(eventEl.attributeValue("exetype").equals(ExtEvent.EXE_TYPE_AJAX)){
				object = new FnEvent();
				object.transXmlObject(eventEl);
			}else if(eventEl.attributeValue("exetype").equals(ExtEvent.EXE_TYPE_JS)){
				object = new JsEvent();
				object.transXmlObject(eventEl);
			}
			return object;
		}
		return null;
	}
	
	/**
	 * 返回数据源sql
	 * @param pid
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getFnSql(String pid,String name)throws Exception{
		if(pid==null || name==null){
			return null;
		}
		List list = getFnDocument().selectNodes("/fnmetas/fnmeta[@pid='"+pid+"']/store[@name='"+name+"']");
		if(list.size()>0){
			Element element = (Element)list.get(0);
			//取事件节点
			Element eventEl = element.element("sql");
			if(eventEl!=null){
				return eventEl.getTextTrim();
			}
		}
		return null;
	}
}
