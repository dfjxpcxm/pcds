package com.shuhao.clean.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;



public class TreeUtil {
	public static final String query_all = "select t.* from (select  m.resource_id,m.parent_resource_id,m.resource_name,handler,icon_name, rownum r from opm_sys_resource m start with m.parent_resource_id = 'root'  connect by prior m.resource_id = m.parent_resource_id ) t";
	
	private static Element Element = null;       //当前操作对象
	private static Element BaseElement = null;   //内存中xml对象
	
	/**
	 * 按层级循序获取数据（带rownum）
	 * @return Map<rownum,Tree>
	 * @throws SQLException
	 */
	public static Map<Integer,Tree> getAllObject(String querySql) throws SQLException{
		Map<Integer,Tree> treeStore = new HashMap<Integer,Tree>();
		Connection conn = getConn();
		PreparedStatement ps = conn.prepareStatement(querySql);
		ResultSet rset = ps.executeQuery();
		while (rset.next()) {
			Tree tree = new Tree();
			tree.setNode_id(rset.getString(1));
			tree.setParent_node_id(rset.getString(2));
			tree.setNode_name(rset.getString(3));
			tree.setNode_handler(rset.getString(4));
			tree.setNode_image(rset.getString(5));
			treeStore.put(rset.getInt(6), tree);
		}
		return treeStore;
	}
	
	/**
	 * 读取xml定位节点
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public static void getElement(String nodeName)throws Exception{
		Element elements = BaseElement;
		if (elements != null && elements.attribute("id").getText().equals(nodeName)) {
			Element = elements;
		}else {
			findNodeByName(elements, nodeName);
		}
	}
	
	public static void findNodeByName(Element elements,String nodeName){
		for (Iterator<?> iterator = elements.elementIterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if (element != null && element.attribute("id").getText().equals(nodeName)) {
				Element = element;
			}
			findNodeByName(element, nodeName);
		}
	}
	
	
	
	
	/**
	 * 创建工具
	 * @param messageID
	 * @param filePath
	 * @throws Exception
	 */
	public static String CreateUtil(Map<Integer,Tree> treeStore,int totalCounts)throws Exception{
		try {
			Document document = new DocumentFactory().createDocument();
			Element = DocumentHelper.createElement("tree");   //根目录
			Element.addAttribute(new QName("id"), "root");
			Element.addAttribute(new QName("name"), "root");
			document.setRootElement(Element);
			BaseElement = Element;
			Tree treeRoot = new Tree();
			treeRoot.setNode_id("root");
			treeRoot.setNode_name("root");
			treeStore.put(totalCounts+1, treeRoot);
			for (int i = 1; i <= totalCounts; i++) {
				Tree tree = treeStore.get(i);
				String nodePid = tree.getParent_node_id();
				for (Iterator<Integer> iterator = treeStore.keySet().iterator(); iterator.hasNext();) {
					int key = iterator.next();
					Tree ptree = treeStore.get(key);
					if (nodePid.equals(ptree.getNode_id())) {
						getElement(nodePid); 
					}
				}
				Element child = DocumentHelper.createElement("item");   //根目录
				child.addAttribute(new QName("id"), tree.getNode_id());
				child.addAttribute(new QName("name"), tree.getNode_name());
				child.addAttribute(new QName("pid"), tree.getParent_node_id());
				child.addAttribute(new QName("handler"), tree.getNode_handler());
				child.addAttribute(new QName("image"), tree.getNode_image());
				Element.add(child);
			}
			return document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "opmadm", "opmadm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) throws SQLException {
		
//		for (int i = 0; i < 50000; i++) {
//			Connection conn = getConn();
//			String sql = "insert into dw_trans_his_dep values ('201406test022000"+i+"',sysdate(),'2','10','1','1','1','0.00','10000.00','1','101','60120701','1','1','系统外转贴现利息收入','','')";
//			PreparedStatement  ps = conn.prepareStatement(sql);
//			ps.execute();
//		}
		System.out.println(System.currentTimeMillis());
	}
}
