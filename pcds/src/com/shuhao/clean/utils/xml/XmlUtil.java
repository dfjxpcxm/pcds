package com.shuhao.clean.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.rx.util.CommonUtil;
import com.shuhao.clean.apps.meta.entity.FnEvent;
import com.shuhao.clean.apps.meta.entity.JsEvent;
import com.shuhao.clean.apps.meta.entity.XmlObject;
import com.shuhao.clean.apps.model.ext.base.ExtEvent;

/**
 * 
 * @Description: TODO
 * 
 * @author: gongzhiyang
 */
public class XmlUtil {

	private static final Logger logger = Logger.getLogger(XmlUtil.class);

	/**
	 * 功能元数据
	 */
	public static String XML_FNMETA = "FnMeta.xml";
	
	private static Document document;
	
	public static Document getFnDocument() {
		try {
			if(document==null){
				document = getDocument(XML_FNMETA);
			}
		} catch (Exception e) {
			logger.error("解析["+XML_FNMETA+"]异常:"+e.getMessage());
		}
		return document;
	}
 
	public static Document getDocument(String xmlName) throws Exception {
		File file = new File(getClassLoaderPath() + File.separator + "xmls" + File.separator + xmlName);
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}
	
	/**
	 * xml字符串转换为document
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	public static Document xmlStrToDoc(String xmlString) throws Exception {
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
	
	
	public static void main(String[] args) throws Exception {
		
		///bookstore/book[price>35.00]s
		System.out.println(getFnEvent("template*","cust_id").getEventName());
		List list = getFnDocument().selectNodes("/fnmetas/fnmeta[@pid='up201412051846190001']/event[@name='cust_id']" );
		List evs = getFnDocument().selectNodes("/fnmetas/fnmeta/event[@id='cl201412021638080077']" );
		/*List evs = document.selectNodes("/fnmetas/fnmeta/event[@id='cl201412021638080077']" );
		
		System.out.println(evs.size());
		//cl201412021638080077,pg201412021638070066
		FnEvent ev = XmlUtil.getFnEvent("cl201412021638080077");
		System.out.println(ev.getMetaName());
		
		if(list.size()>0){
			Element element = (Element)list.get(0);
			
		    Element event = element.element("event");  
		    
		    System.out.println(event.attribute("name").getValue()); 
		    Element initsql = event.element("initsql");
		    System.out.println(initsql.getTextTrim());
		    System.out.println(initsql.getText().replaceAll("^\\s*|\\s*$", ""));
		    Element initcomps = event.element("initcomps");
		    List<Element> comps = initcomps.elements("comp");
		    for (Element e : comps) {
				System.out.println(e.attributeValue("id")+"_"+e.attributeValue("name"));
			}
		}*/
	}
	
	public static String getClassLoaderPath()
	{
		String path=CommonUtil.class.getClassLoader().getResource("").getPath();
		path = path.replaceAll("%20", " ");
		return path;
	}
	
	public static String getWebRootPath()
	{
	    String classLoaderPath = getClassLoaderPath();
	    File file = new File(classLoaderPath);
	    String path = file.getParentFile().getParentFile().getAbsolutePath();
	    path = path.replaceAll("%20", " ");
	    return path;
	}
}
