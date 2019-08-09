package com.shuhao.clean.utils.exttree;

import com.rx.util.tree.TreeNode;

/**
 * <p>Title: 基础控制类，所有controller类的父类</p>
 * <p>Description: 转换为ext的树节点<br>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-9-23
 * @author gongzy
 * @version 1.0
 */
public interface ToExtTreeNode extends TreeNode{
	public ExtTreeNode conver2ExtTreeNode();
}
