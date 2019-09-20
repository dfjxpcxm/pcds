package com.shuhao.clean.utils.exttree;

import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeStore;

import java.util.List;

/**
 * <p>Title: ext树工具类，生成ext tree默认全部展开</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-9-23
 * @author gongzy
 * @version 1.0
 */
public class OrgTreeUtils {
	
	public static String TREE_DEFAULT_ROOT_ID = "350010000000";//树默认根节点ID


	public static String TREE_DEFAULT_ROOT_NAME = "浦城县";
	
	public static <T> ExtTreeNode  listProjectTree(List<? extends ToExtTreeNode> dataList,String rootName)
			throws Exception{
		 
		 TreeStore treeDS = new TreeStore();
		 SimpleTreeNode node = new SimpleTreeNode();
		 node.setNodeId(TREE_DEFAULT_ROOT_ID);
		 node.setParentNodeId("@@@@");
		 node.setNodeName(TREE_DEFAULT_ROOT_NAME);
		 treeDS.addTreeNode(node);
		 for (int i = 0; i < dataList.size(); i++) {
			treeDS.addTreeNode(dataList.get(i)); 
		 }
		 
		ExtTreeNode extTreeNode = new ExtTreeNode();
		extTreeNode.setId(TREE_DEFAULT_ROOT_ID);
		extTreeNode.setText(rootName);
		
		deepProdMeaTree(extTreeNode, treeDS, TREE_DEFAULT_ROOT_ID);
		return extTreeNode;
	}
	/**
	 * 构造树
	 * @param extTreeNode
	 * @param treeDS
	 * @param pid
	 * @throws Exception
	 */
	private static void deepProdMeaTree(ExtTreeNode extTreeNode, TreeStore treeDS ,String pid) throws Exception{
		@SuppressWarnings("unchecked")
		List<Tree> tempTree = treeDS.getTree(pid).getChildren();
		
		if(tempTree.size()==0){
			extTreeNode.setLeaf(Boolean.valueOf("true"));
		}
		for (int i = 0; i < tempTree.size(); i++) {
			ToExtTreeNode toTreeNode =(ToExtTreeNode) tempTree.get(i).getRootNode();
			ExtTreeNode treeNode = toTreeNode.conver2ExtTreeNode();
			
			extTreeNode.getChildren().add(treeNode);
			deepProdMeaTree(treeNode, treeDS, toTreeNode.getNodeID());
		}
	}
}
