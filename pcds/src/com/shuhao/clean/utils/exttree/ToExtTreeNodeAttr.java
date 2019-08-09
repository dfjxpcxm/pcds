package com.shuhao.clean.utils.exttree;

/**
 * <p>Title: 基础控制类，所有controller类的父类</p>
 * <p>Description: 转换为ext的树节点,包含自定义属性<br>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-9-23
 * @author gongzy
 * @version 1.0
 */
public interface ToExtTreeNodeAttr<T extends ExtTreeNode> extends ToExtTreeNode{
	public T conver2ExtTreeNode();
}
