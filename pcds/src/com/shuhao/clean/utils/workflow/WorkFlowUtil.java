package com.shuhao.clean.utils.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.shuhao.clean.apps.flow.entity.BaseFlow;
import com.shuhao.clean.apps.flow.entity.FlowLine;
import com.shuhao.clean.apps.flow.entity.FlowNode;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.constant.WorkFlowConstant;

public class WorkFlowUtil {
	
	//private static String workFlowXml = "";
	
	/**
	 * 获取当前流程中第一个人机交互节点
	 * @return FlowNode
	 */
	public static FlowNode getFristTaskNode(Map<String,List<BaseFlow>> flowMap) throws DocumentException{
		//获取所有的连接线
		List<BaseFlow> listLine = flowMap.get(WorkFlowConstant.WORK_FLOW_LINES);
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
	    
	   //获取开始节点   流程开始节点只有一个
	    FlowNode start = null;
	    
	    for (int i = 0; i < listNode.size(); i++) {
			FlowNode node = (FlowNode)listNode.get(i);
			if (WorkFlowConstant.WORK_FLOW_START.equals(node.getType())) {
				start = node;
				break;
			}
		}
	    String startId = start.getId();
	    boolean type = true;
	    
	    while(type){
	    	for (int i = 0; i < listLine.size(); i++) {
	    		FlowLine line = (FlowLine)listLine.get(i);
	    		if (line.getFrom().equals(startId)) {
					 String id = line.getTo();
					 for (int j = 0; j < listNode.size(); j++) {
							FlowNode node = (FlowNode)listNode.get(j);
							if (node.getId().equals(id)) {
								if (WorkFlowConstant.WORK_FLOW_TASK.equals(node.getType())) {
									type = false;
									return node;
								}else {
									startId = node.getId();
								}
								break;
							}
					}
					 break;
				}
			}
	    }
		return null;
	}
	
	
	/**
	 * 获取当前流程中最后一个人机交互节点 
	 * 
	 * @return FlowNode
	 */
	public static List<FlowNode> getLastTaskNode(Map<String,List<BaseFlow>> flowMap) throws DocumentException{
		
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
	    
	    //存储最后一级审批节点
		List<FlowNode> lastTaskNode =  new ArrayList<FlowNode>();
	    
	    for (int p = 0; p < listNode.size(); p++) {
			FlowNode endNode = (FlowNode)listNode.get(p);
			if (WorkFlowConstant.WORK_FLOW_END.equals(endNode.getType())) {
				List<FlowNode> TaskNodes = getPrevNodeByType(flowMap,endNode, WorkFlowConstant.WORK_FLOW_TASK);
				for (int i = 0; i < TaskNodes.size(); i++) {
					lastTaskNode.add(TaskNodes.get(i));
				}
			}
		}
	    return lastTaskNode;
		
	}
	
	/**
	 * 获取当前节点的上级节点
	 * 
	 * @param currentNode
	 * @return List<FlowNode>
	 * @throws DocumentException
	 */
	public static List<FlowNode> getPrevNode(Map<String,List<BaseFlow>> flowMap,FlowNode currentNode) throws DocumentException{
		List<FlowNode> PrevNodeList = getPrevNodeByType(flowMap,currentNode, WorkFlowConstant.WORK_FLOW_DEFAULT);
	    return PrevNodeList;
	}
	
	/**
	 * 判断当前节点（人机交互）是否为最后审批节点
	 * 
	 * @param currentNode
	 * @return boolean
	 * @throws DocumentException
	 */
	public static boolean checkLastTaskNode(Map<String,List<BaseFlow>> flowMap,FlowNode currentNode) throws DocumentException{
		boolean isEnd = false;
		List<FlowNode> nodeList = getLastTaskNode(flowMap);
		for (int i = 0; i < nodeList.size(); i++) {
			FlowNode node = nodeList.get(i);
			if (node.getId().equals(currentNode.getId())) {
				isEnd = true;
			}
		}
		return isEnd;
	}
	
	
	/**
	 * 获取当前节点的下级节点
	 * @param workFlowXml 流程数据
	 * @param currentNode 当前节点
	 * @return List<FlowNode>
	 */
	public static List<FlowNode> getNextNode(Map<String,List<BaseFlow>> flowMap,BaseFlow currentNode) throws DocumentException{
		
		//获取所有的连接线
		List<BaseFlow> listLine = flowMap.get(WorkFlowConstant.WORK_FLOW_LINES);
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
		
		List<FlowNode> nextNodes = new ArrayList<FlowNode>();
		
		for (int i = 0; i < listLine.size(); i++) {
			FlowLine line = (FlowLine)listLine.get(i);
			if (line.getFrom().equals(currentNode.getId())) {
				String to = line.getTo();
				for (int j = 0; j < listNode.size(); j++) {
					FlowNode node = (FlowNode)listNode.get(j);
					if (node.getId().equals(to)) {
						nextNodes.add(node);
					}
				}
			}
		}
		return nextNodes;
	}
	
	
	
	
	//根据当前节点的操作状态获取下级节点走向节点
	
	public static FlowNode getNextTaskByStatus(Map<String,List<BaseFlow>> flowMap,FlowNode currentNode,String flowStatus) throws DocumentException{
		
		//获取所有的连接线
		List<BaseFlow> listLine = flowMap.get(WorkFlowConstant.WORK_FLOW_LINES);
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
	    
	    //当前节点获取下级节点
		for (int i = 0; i < listLine.size(); i++) {
			FlowLine line = (FlowLine)listLine.get(i);
			if (line.getFrom().equals(currentNode.getId())) {
				//当前节点出发的连线
				String to = line.getTo();
				for (int j = 0; j < listNode.size(); j++) {
					FlowNode node = (FlowNode)listNode.get(j);
					if (node.getId().equals(to)) {
						if (node.getType().equals(WorkFlowConstant.WORK_FLOW_FORK)) {
							if(node.getVmodel().equals(flowStatus)){
								String value = node.getVvalue();
								return WorkFlowUtil.getFlowNodeById(listNode, value);
							}else{
								List<FlowNode> nodes = getNextNode(flowMap, node);
								for (int n = 0; n < nodes.size(); n++) {
									FlowNode node_ = nodes.get(n);
									String value = node.getVvalue();
									if (!node_.getId().equals(value)) {
										return node_;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	
	//通过节点id获取节点对象
	public static FlowNode getFlowNodeById(List<BaseFlow> listNode,String NodeId) throws DocumentException{
		for (int i = 0; i < listNode.size(); i++) {
			FlowNode node = (FlowNode)listNode.get(i);
			if (node.getId().equals(NodeId)) {
				return node;
			}
		}
		return null;
	}
	
	//通过连线id获取连线对象
	public static FlowLine getFlowLineById(List<BaseFlow> listLine,String LineId) throws DocumentException{
		//获取所有节点
		for (int i = 0; i < listLine.size(); i++) {
			FlowLine line = (FlowLine)listLine.get(i);
			if (line.getId().equals(LineId)) {
				return line;
			}
		}
		return null;
	}
	
	
	/**
	 * 获取的当前节点指定类型的上级节点
	 * @param currNode 当前节点
	 * @param nodeType 需要获取的当前节点指定类型的上级节点类型
	 * @return 
	 * @throws DocumentException
	 */
	public static List<FlowNode> getPrevNodeByType(Map<String,List<BaseFlow>> flowMap,FlowNode currNode,String nodeType) throws DocumentException{
		
		List<FlowNode> lastTaskNode = new ArrayList<FlowNode>();
		//获取所有的连接线
		List<BaseFlow> listLine = flowMap.get(WorkFlowConstant.WORK_FLOW_LINES);
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
		
		boolean type = true;                //控制递归循环
	    boolean c = false;                  //控制节点遍历
	    int count = 0;                      //存储结束节点上的连线数
	    String currId = currNode.getId();   //当前节点ID
	    
	    //存储已便利过的结束节点连线Id
	    List<String> saveLineId = new ArrayList<String>();
	    
	    //得到以当前节点为终点的连接线
	    for (int i = 0; i < listLine.size(); i++) {
    		FlowLine line = (FlowLine)listLine.get(i);
    		if (line.getTo().equals(currId)) {
    			count++;
    		}
	    }
	    //根据结束节点上连线的条数来循环获取节点
	    int num = 0;
	    while(num < count){
	    	while(type){
	    		for (int i = 0; i < listLine.size(); i++) {
		    		FlowLine line = (FlowLine)listLine.get(i);
		    		if (line.getTo().equals(currId)) {
		    			String line_id = line.getId();
		    			 //得到以当前节点为终点的连接线
						 String fromId = line.getFrom();
						 
						 boolean t = false;   //控制跳过已遍历过的连线
						 
						 if (saveLineId != null && saveLineId.size() > 0) {
							 for (int a = 0; a < saveLineId.size(); a++) {
								 String lineId = saveLineId.get(a);
								 if(lineId.equals(line_id)){
									 t = true;
									 break;
								 }
							}
						}
						//存储已便利过的结束节点连线Id
						if (currId.equals(currNode.getId())) saveLineId.add(line_id);
						if (t)  continue;
						 for (int j = 0; j < listNode.size(); j++) {
							FlowNode node = (FlowNode)listNode.get(j);
							if (node.getId().equals(fromId)) {
								if (WorkFlowConstant.WORK_FLOW_DEFAULT.equals(nodeType)) {
									lastTaskNode.add(node);
									type = false;  //终止递归循环
									c = true;  //终止当前循环
									break;
								}else{
									if (nodeType.equals(node.getType())) {
										lastTaskNode.add(node);
										type = false;  //终止递归循环
										c = true;  //终止当前循环
										break;
									}else {
										if (c) break;
										currId = node.getId();   //当前遍历到的节点递归赋值
									}
								}
							}
						}
					}
				}
	    	}
	    	num ++;
	    	type = true;
	    	c = false;
	    	//重置遍历根节点，
	    	currId = currNode.getId();
	    }
	    return lastTaskNode;
	}
	
	
	/**
	 * 获取的当前节点指定类型的下级节点   (未完成)
	 * @param currNode 当前节点
	 * @param nodeType 需要获取的当前节点指定类型的下级节点类型
	 * @return 
	 * @throws DocumentException
	 */
	public static List<FlowNode> getNextNodeByType(Map<String,List<BaseFlow>> flowMap,FlowNode currNode,String nodeType) throws DocumentException{
		
		List<FlowNode> nextTaskNode = new ArrayList<FlowNode>();
		
		//获取所有的连接线
		List<BaseFlow> listLine = flowMap.get(WorkFlowConstant.WORK_FLOW_LINES);
		//获取所有节点
		List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
		
		boolean type = true;                //控制递归循环
	    boolean c = false;                  //控制节点遍历
	    int count = 0;                      //存储结束节点上的连线数
	    String currId = currNode.getId();   //当前节点ID
	    
	    //存储已便利过的结束节点连线Id
	    List<String> saveLineId = new ArrayList<String>();
	    
	    //得到以当前节点为起点的连接线
	    for (int i = 0; i < listLine.size(); i++) {
    		FlowLine line = (FlowLine)listLine.get(i);
    		if (line.getFrom().equals(currId)) {
    			count++;
    		}
	    }
	    //根据结束节点上连线的条数来循环获取节点
	    int num = 0;
	    while(num < count){
	    	while(type){
	    		for (int i = 0; i < listLine.size(); i++) {
		    		FlowLine line = (FlowLine)listLine.get(i);
		    		if (line.getFrom().equals(currId)) {
		    			String line_id = line.getId();
		    			 //得到以当前节点为起点的连接线
						 String id = line.getTo();
						 
						 boolean t = false;   //控制跳过已遍历过的连线
						 
						 if (saveLineId != null && saveLineId.size() > 0) {
							 for (int a = 0; a < saveLineId.size(); a++) {
								 String lineId = saveLineId.get(a);
								 if(lineId.equals(line_id)){
									 t = true;
									 break;
								 }
							}
						}
//						存储已便利过的结束节点连线Id
						if (currId.equals(currNode.getId())) saveLineId.add(line_id);
						if (t)  continue;
						 for (int j = 0; j < listNode.size(); j++) {
							FlowNode node = (FlowNode)listNode.get(j);
							if (node.getId().equals(id)) {
								if (WorkFlowConstant.WORK_FLOW_DEFAULT.equals(nodeType)) {
									nextTaskNode.add(node);
									type = false;  //终止递归循环
									c = true;  //终止当前循环
									break;
								}else{
									if (nodeType.equals(node.getType())) {
										nextTaskNode.add(node);
										type = false;  //终止递归循环
										c = true;  //终止当前循环
										break;
									}else{
										if (c) break;
										currId = node.getId();   //当前遍历到的节点递归赋值
										break;
									}
								}
							}
						}
					}
				}
	    	}
	    	num ++;
	    	type = true;
	    	c = false;
	    	//重置遍历根节点，
	    	currId = currNode.getId();
	    }
	    return nextTaskNode;
	}
	
	
	/**
	 * 根据xml字符串成才Document对象 
	 * @param flow_xml
	 * @return
	 * @throws DocumentException
	 */
	public static Document getWorkFlowDocument(String flow_xml) throws DocumentException{
		Document document = DocumentHelper.parseText(flow_xml);
		return document;
	}
	
	/**
	 * 根据json字符串成才xml类型 
	 * @param flow_json
	 * @return
	 */
	public static String getWorkFlowXml(String flow_json){
		XMLSerializer xml = new XMLSerializer();
		xml.setTypeHintsEnabled(false);
		JSON jsons = (new JSONObject()).element("workflow",flow_json);
	    String flow_xml = StringUtils.substringBetween(xml.write(jsons), "<o>", "</o>");
		return flow_xml;
	}
	
	//节点查找类
	
	
	/**
	 * 根据传入类型查找节点
	 * @param baseFlow 返回的节点类型
	 * @param nodeType 查找类型  1.text 标签查找 2.name 标签值查找
	 * @param params 查找的参数
	 * @return
	 */
	public static Map<String,List<BaseFlow>> WorkFlowHandler(String flow_json) throws DocumentException{
		String workFlowXml = getWorkFlowXml(flow_json); //json转换成xml
		
		Document document = DocumentHelper.parseText(workFlowXml);
		Element elements = document.getRootElement();
		Map<String,List<BaseFlow>> flowMap = new HashMap<String, List<BaseFlow>>();
		List<BaseFlow> flowLines = new ArrayList<BaseFlow>();
		List<BaseFlow> flowNodes = new ArrayList<BaseFlow>();
		
		WorkFlowHandler_(elements,flowNodes,WorkFlowConstant.WORK_FLOW_NODES);
		WorkFlowHandler_(elements,flowLines,WorkFlowConstant.WORK_FLOW_LINES);
		flowMap.put(WorkFlowConstant.WORK_FLOW_NODES, flowNodes);
		flowMap.put(WorkFlowConstant.WORK_FLOW_LINES, flowLines);
		return flowMap;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static List<BaseFlow> WorkFlowHandler_(String workFlowXml,List<BaseFlow> list,String node_type) throws DocumentException{
		Document document = DocumentHelper.parseText(workFlowXml);
		List<Element> _list = null;
		if (node_type.equals(WorkFlowConstant.WORK_FLOW_NODES)) {
			_list = document.selectNodes("/workflow/nodes");
		}else{
			_list = document.selectNodes("/workflow/lines");
		}
		WorkFlowHandler_(_list.get(0),list,node_type);
		return list;
	}
	
	public static void WorkFlowHandler_(Element elements,List<BaseFlow> list,String node_type) throws DocumentException{
		if (elements != null && elements.elementIterator().hasNext()) {
			for (Iterator<?> iterator = elements.elementIterator(); iterator.hasNext();) {
				Element element = (Element)iterator.next();
				if (element != null) {
					if (node_type.equals(WorkFlowConstant.WORK_FLOW_NODES)) {
						if (element.getName().contains("demo_node_")) {
							String id = element.getName();
							String name = element.element("name") == null ? "" : element.element("name").getText();
							String type = element.element("type")== null ? "" : element.element("type").getText();
							FlowNode node = new FlowNode();
							String left = element.element("left") == null ? "" : element.element("left").getText();
							String top = element.element("top") == null ? "" : element.element("top").getText();
							String Vmodel = element.element("Vmodel") == null ? "" : element.element("Vmodel").getText();
							String Vvalue = element.element("Vvalue")== null ? "" : element.element("Vvalue").getText();
							String check = element.element("Vcheck")== null ? "" : element.element("Vcheck").getText();
							boolean Vcheck = false;
							
							if ("true".equals(check)) {
								Vcheck = true;
							}
							
							node.setId(id);
							node.setName(name);
							node.setType(type);
							node.setVmodel(Vmodel);
							node.setVvalue(Vvalue);
							node.setVcheck(Vcheck);
							node.setLeft(left);
							node.setTop(top);
							list.add(node);
						}else{
							WorkFlowHandler_(element, list, node_type);
						}
					}else if(node_type.equals(WorkFlowConstant.WORK_FLOW_LINES)) {
						if (element.getName().contains("demo_line_")) {
							FlowLine line = new FlowLine();
							String id = element.getName();
							String name = element.element("name") == null ? "" : element.element("name").getText();
							String type = element.element("type")== null ? "" : element.element("type").getText();
							String from = element.element("from").getText();
							String to = element.element("to").getText();
							line.setId(id);
							line.setName(name);
							line.setType(type);
							line.setFrom(from);
							line.setTo(to);
							list.add(line);
						}else{
							WorkFlowHandler_(element, list, node_type);
						}
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		String json = "{'title':'二级审批流','nodes':{'demo_node_31':{'name':'录入撤回','left':490,'top':94,'type':'fork','Vmodel':'00','Vvalue':'demo_node_6','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_1':{'name':'开始','left':152,'top':309,'type':'start','width':24,'height':24,'alt':true},'demo_node_2':{'name':'录入','left':154,'top':91,'type':'task','width':24,'height':24,'alt':true,'Vmodel':'01','Vvalue':'R00001','Vcheck':false},'demo_node_3':{'name':'审批1','left':309,'top':92,'type':'task','width':24,'height':24,'alt':true,'Vmodel':'01','Vvalue':'R00002','Vcheck':true},'demo_node_6':{'name':'是否同意','left':674,'top':93,'type':'fork','width':24,'height':24,'alt':true,'Vmodel':'00','Vvalue':'demo_node_7','Vcheck':false},'demo_node_7':{'name':'审批2','left':674,'top':167,'type':'task','width':24,'height':24,'alt':true,'Vmodel':'01','Vvalue':'R00003','Vcheck':true},'demo_node_8':{'name':'结束','left':675,'top':338,'type':'end','width':24,'height':24,'alt':true},'demo_node_16':{'name':'是否同意','left':673,'top':231,'type':'fork','width':24,'height':24,'alt':true,'Vmodel':'00','Vvalue':'demo_node_8','Vcheck':false},'demo_node_17':{'name':'录入','left':487,'top':236,'type':'task','width':24,'height':24,'alt':true,'Vmodel':'01','Vvalue':'R00001','Vcheck':true},'demo_node_24':{'name':'继续提交','left':309,'top':238,'type':'fork','width':24,'height':24,'alt':true,'Vmodel':'00','Vvalue':'demo_node_3','Vcheck':false}},'lines':{'demo_line_34':{'type':'sl','from':'demo_node_31','to':'demo_node_17','name':'不同意'},'demo_line_9':{'type':'sl','from':'demo_node_1','to':'demo_node_2','name':''},'demo_line_10':{'type':'sl','from':'demo_node_2','to':'demo_node_3','name':''},'demo_line_13':{'type':'sl','from':'demo_node_6','to':'demo_node_7','name':'同意'},'demo_line_18':{'type':'sl','from':'demo_node_7','to':'demo_node_16','name':''},'demo_line_20':{'type':'sl','from':'demo_node_16','to':'demo_node_8','name':'同意'},'demo_line_22':{'type':'sl','from':'demo_node_16','to':'demo_node_17','name':'不同意'},'demo_line_25':{'type':'sl','from':'demo_node_6','to':'demo_node_17','name':'不同意'},'demo_line_26':{'type':'sl','from':'demo_node_17','to':'demo_node_24','name':''},'demo_line_28':{'type':'sl','from':'demo_node_24','to':'demo_node_3','name':'同意'},'demo_line_29':{'type':'tb','M':349,'from':'demo_node_24','to':'demo_node_8','name':'不同意'},'demo_line_32':{'type':'sl','from':'demo_node_3','to':'demo_node_31','name':''},'demo_line_33':{'type':'sl','from':'demo_node_31','to':'demo_node_6','name':'同意'}},'areas':{},'initNum':35}";
		json = "{'title':'workflow','nodes':{'demo_node_1':{'name':'开始','left':199,'top':293,'type':'start','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_2':{'name':'结束','left':398,'top':305,'type':'end','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_3':{'name':'获取数据','left':199,'top':174,'type':'node','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_4':{'name':'录入','left':199,'top':58,'type':'task','Vmodel':'01','Vvalue':'root','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_5':{'name':'审核1','left':310,'top':58,'type':'task','Vmodel':'01','Vvalue':'R00002','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_6':{'name':'撤回','left':409,'top':60,'type':'fork','Vmodel':'00','Vvalue':'demo_node_7','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_7':{'name':'同意','left':563,'top':57,'type':'fork','Vmodel':'00','Vvalue':'demo_node_8','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_8':{'name':'审批2','left':569,'top':180,'type':'task','Vmodel':'01','Vvalue':'R00003','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_9':{'name':'同意','left':484,'top':181,'type':'fork','Vmodel':'00','Vvalue':'demo_node_2','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_10':{'name':'录入','left':410,'top':181,'type':'task','Vmodel':'01','Vvalue':'R00002','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_11':{'name':'继续','left':311,'top':183,'type':'fork','Vmodel':'00','Vvalue':'demo_node_5','Vcheck':false,'width':24,'height':24,'alt':true}},'lines':{'demo_line_12':{'type':'sl','from':'demo_node_1','to':'demo_node_3','name':'','alt':true},'demo_line_14':{'type':'sl','from':'demo_node_3','to':'demo_node_4','name':'','alt':true},'demo_line_16':{'type':'sl','from':'demo_node_4','to':'demo_node_5','name':'','alt':true},'demo_line_17':{'type':'sl','from':'demo_node_5','to':'demo_node_6','name':'','alt':true},'demo_line_18':{'type':'sl','from':'demo_node_6','to':'demo_node_10','name':'不同意','alt':true},'demo_line_19':{'type':'sl','from':'demo_node_6','to':'demo_node_7','name':'同意','alt':true},'demo_line_20':{'type':'sl','from':'demo_node_7','to':'demo_node_8','name':'同意','alt':true},'demo_line_21':{'type':'sl','from':'demo_node_7','to':'demo_node_10','name':'不同意','alt':true},'demo_line_22':{'type':'sl','from':'demo_node_8','to':'demo_node_9','name':'','alt':true},'demo_line_23':{'type':'lr','from':'demo_node_9','to':'demo_node_2','name':'同意','alt':true,'M':494},'demo_line_24':{'type':'sl','from':'demo_node_9','to':'demo_node_10','name':'不同意','alt':true},'demo_line_25':{'type':'sl','from':'demo_node_10','to':'demo_node_11','name':'','alt':true},'demo_line_26':{'type':'sl','from':'demo_node_11','to':'demo_node_5','name':'同意','alt':true},'demo_line_27':{'type':'lr','from':'demo_node_11','to':'demo_node_2','name':'不同意','alt':true,'M':324}},'areas':{},'initNum':28}";
		json = "{'title':'测试流程','nodes':{'demo_node_23':{'name':'开始','left':69,'top':74,'type':'start','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_25':{'name':'审批1','left':293,'top':76,'type':'task','Vmodel':'00','Vvalue':'1B7','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_26':{'name':'审批2','left':485,'top':76,'type':'task','Vmodel':'00','Vvalue':'1080','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_27':{'name':'是否同意','left':393,'top':75,'type':'fork','Vmodel':'00','Vvalue':'demo_node_26','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_28':{'name':'是否同意','left':485,'top':191,'type':'fork','Vmodel':'00','Vvalue':'demo_node_31','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_29':{'name':'继续提交','left':288,'top':188,'type':'fork','Vmodel':'00','Vvalue':'demo_node_25','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_30':{'name':'录入','left':392,'top':192,'type':'task','Vmodel':'00','Vvalue':'1082','Vcheck':true,'width':24,'height':24,'alt':true},'demo_node_31':{'name':'结束','left':566,'top':191,'type':'end','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_32':{'name':'录入','left':181,'top':77,'type':'task','Vmodel':'00','Vvalue':'1082','Vcheck':false,'width':24,'height':24,'alt':true},'demo_node_44':{'name':'结束','left':177,'top':184,'type':'end','Vmodel':'00','Vvalue':'','Vcheck':false,'width':24,'height':24,'alt':true}},'lines':{'demo_line_34':{'type':'sl','from':'demo_node_23','to':'demo_node_32','name':''},'demo_line_35':{'type':'sl','from':'demo_node_32','to':'demo_node_25','name':''},'demo_line_36':{'type':'sl','from':'demo_node_25','to':'demo_node_27','name':''},'demo_line_37':{'type':'sl','from':'demo_node_27','to':'demo_node_26','name':'是','alt':true},'demo_line_38':{'type':'sl','from':'demo_node_26','to':'demo_node_28','name':''},'demo_line_39':{'type':'sl','from':'demo_node_28','to':'demo_node_31','name':'是','alt':true},'demo_line_40':{'type':'sl','from':'demo_node_28','to':'demo_node_30','name':'否','alt':true},'demo_line_41':{'type':'sl','from':'demo_node_27','to':'demo_node_30','name':'否','alt':true},'demo_line_42':{'type':'sl','from':'demo_node_30','to':'demo_node_29','name':''},'demo_line_43':{'type':'sl','from':'demo_node_29','to':'demo_node_25','name':'是','alt':true},'demo_line_45':{'type':'sl','from':'demo_node_29','to':'demo_node_44','name':'否','alt':true}},'areas':{},'initNum':46}";
		String workFlowXml = getWorkFlowXml(json);
		System.out.println(workFlowXml);
		
		Map<String,List<BaseFlow>> flowMap = WorkFlowUtil.WorkFlowHandler(workFlowXml);
		FlowNode cnode = new FlowNode();
		cnode.setId("demo_node_26");
		cnode.setName("审批1");
		cnode.setType("task");
		FlowNode n = getNextTaskByStatus(flowMap, cnode, WorkFlowConstant.WORK_FLOW_AGREE);
		System.out.println(n.getName());
		
		FlowNode n2 = getNextTaskByStatus(flowMap, cnode, WorkFlowConstant.WORK_FLOW_REBACK);
		System.out.println(n2.getName());
		
		//
		
		cnode.setId("demo_node_32");
		cnode.setName("录入");
		cnode.setType("task");
		List<FlowNode> nodes = getNextNode(flowMap, cnode);
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println(nodes.get(i).getName());
		}
	}
	
	/**
	 * 生成查询sql 
	 * @param baseSql
	 * @param task_status
	 * @param currentUser
	 * @param nodes
	 * @param isShared 是否共享
	 * @return
	 */
	public static String getQuerySql(String baseSql,Map<String, Object> queryParams){
		String task_status = String.valueOf(queryParams.get("task_status"));
		String currentUser = String.valueOf(queryParams.get("user_id"));
		String nodes = String.valueOf(queryParams.get("nodeIds"));
		boolean isShared = Boolean.valueOf(String.valueOf(queryParams.get("isShared")));
		String hasApprove = String.valueOf(queryParams.get("hasApprove"));
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (");
		//查询数据  查询自己新建的任务
		/*buffer.append(" select a.*,c.task_id,c.task_status,c.create_user as apply_user_id,");
		buffer.append(" c.next_node_id,c.current_node_id,c.flow_id ,date_format(c.create_date,'%Y-%m-%d') create_date from");
		buffer.append(" (").append(baseSql).append(") a");
		buffer.append(" left join upp_flow_sequences c on c.business_no = a.business_no ");
		buffer.append("  where 1=1 ");*/
		
		//2017.10.30
		buffer.append(" select a.*,c.task_id,c.task_status,c.create_user as apply_user_id,");
		buffer.append(" c.next_node_id,c.current_node_id,c.flow_id ,date_format(c.create_date,'%Y-%m-%d') create_date from");
		buffer.append(" (").append(baseSql).append(") a,");
		buffer.append(" upp_flow_sequences c ");
		buffer.append("  where 1=1 ");
		//传入审批状态参数
		if (task_status != null && !task_status.equals("")) {
			if(task_status.equals("null")){
				buffer.append(" and c.task_status is null ");
			}else{
				buffer.append(" and c.task_status = '"+task_status+"' ");
			}
		}
		//如果是共享模版，不限制申请人,超级管理员查询不受限制
		if(!isShared && !currentUser.equals(LoginConstant.SUPER_USER)){
			//如果有审批权限,限制申请人等于登录用户，或当前用户有审批节点
			if(hasApprove.equals("Y")){
				//创建人
				buffer.append(" and (c.create_user in('"+currentUser+"','00000') or c.create_user is null ");
				buffer.append(" or (c.next_node_id in ("+nodes.substring(0,nodes.length()-1)+")"); 
				buffer.append(" or c.current_node_id in ("+nodes.substring(0,nodes.length()-1)+")) )");
			}else{
				//没有审批权限，只限制申请人等于当前用户
				buffer.append(" and a.create_user in('"+currentUser+"','00000',null)");
			}
		}
		buffer.append(" )h  order by create_date"); //别名 h
		return buffer.toString();
	}

}
