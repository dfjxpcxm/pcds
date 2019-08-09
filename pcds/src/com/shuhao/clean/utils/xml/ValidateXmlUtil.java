package com.shuhao.clean.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.rx.util.CommonUtil;
import com.shuhao.clean.apps.meta.entity.FnEvent;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * 
 * @Description: TODO
 * 
 * @author: gongzhiyang
 */
public class ValidateXmlUtil {

	private static final Logger logger = Logger.getLogger(ValidateXmlUtil.class);

	/**
	 * 功能元数据
	 */
	public static String XML_NAME = "CheckFunctions.xml";
	
	private static Document document;
	
	public static Document getFnDocument() {
		try {
			if(document==null){
				document = getDocument(XML_NAME);
			}
		} catch (Exception e) {
			logger.error("解析["+XML_NAME+"]异常:"+e.getMessage());
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
	 * 获取函数列表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> getFunctions()throws Exception{
		List<Node> nodeList = getFnDocument().selectNodes("/functions/function" );
		List<Map<String,Object>> funList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < nodeList.size(); i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			Node node = nodeList.get(i);
			map.put("content", node.selectSingleNode("content").getText().trim());
			map.put("desc", node.selectSingleNode("desc").getText().trim());
			funList.add(map);
		}
		return funList;
	}
	
	public static List<Map<String,Object>> getFunctions(Map<String, Object> param)throws Exception{
		String nodeId = GlobalUtil.getStringValue(param, "nodeId");
		String chkMethod = GlobalUtil.getStringValue(param, "chkMethod");
		if("01".equals(chkMethod)||"02".equals(chkMethod)){
			chkMethod = "java";
		}else{
			chkMethod = "sql";
		}
		List<Node> nodeList = getFnDocument().selectNodes("/functions/group[@type='"+nodeId+"']/exeMethod[@type='"+chkMethod+"']/function" );
		List<Map<String,Object>> funList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < nodeList.size(); i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			Node node = nodeList.get(i);
			map.put("name", node.selectSingleNode("name").getText().trim());
			map.put("content", node.selectSingleNode("content").getText().trim());
			map.put("desc", node.selectSingleNode("desc").getText().trim());
			funList.add(map);
		}
		return funList;
	}
	
	public static void main(String[] args) throws Exception {
		
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
