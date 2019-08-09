package com.shuhao.clean.constant;

public class WorkFlowConstant {
	
	public static final String WORK_FLOW_DEFAULT="default";
	
	//节点分类
	public static final String WORK_FLOW_LINES="lines";
	public static final String WORK_FLOW_NODES="nodes";

	//节点属性 
	public static final String WORK_FLOW_NAME = "name";
	public static final String WORK_FLOW_TEXT = "text";
	
	//节点类型
	public static final String WORK_FLOW_START="start";//开始
	public static final String WORK_FLOW_TASK = "task";//任务节点
	public static final String WORK_FLOW_NODE = "node";
	public static final String WORK_FLOW_CHAT = "chat";
	public static final String WORK_FLOW_STATE = "state";
	public static final String WORK_FLOW_PLUG = "plug";
	public static final String WORK_FLOW_JOIN = "join";
	public static final String WORK_FLOW_FORK = "fork";//分支
	public static final String WORK_FLOW_END="end";//结束
	
	//连线类型
	public static final String WORK_FLOW_SL = "sl"; //连线
	public static final String WORK_FLOW_TB = "tb";
	
	//连线属性
	public static final String WORK_FLOW_FROM="from";
	public static final String WORK_FLOW_TO="to";
	
	//权限分类
	public static final String WORK_FLOW_ORG = "00";
	public static final String WORK_FLOW_ROLE = "01";
	public static final String WORK_FLOW_DEPT = "02";
	public static final String WORK_FLOW_POST = "03";
	public static final String WORK_FLOW_TEAM = "04";
	public static final String WORK_FLOW_EPM = "05";
	
	
	//流程操作
	/**
	 * 通过
	 */
	public static final String WORK_FLOW_AGREE = "00";   
	/**
	 * 退回
	 */
	public static final String WORK_FLOW_REBACK = "01";  
	
	
	//流程状态
	public static final String WORK_FLOW_APPLY_NEW = "-1"; //新建
	/**
	 * 编辑
	 */
	public static final String WORK_FLOW_APPLY_EDIT = "00";
	/**
	 * 待审
	 */
	public static final String WORK_FLOW_APPLY_WAIT = "01";
	/**
	 * 完成
	 */
	public static final String WORK_FLOW_APPLY_DONE = "02";
	/**
	 * 退回
	 */
	public static final String WORK_FLOW_APPLY_BACK = "03";  
	/**
	 * 撤回
	 */
    public static final String WORK_FLOW_APPLY_RECALL = "04"; 
    
    //数据状态
    /**
     * 编辑、新建 同审批流程
     */
    public static final String DATA_STATUS_NEW = "00"; 
    /**
     * 审批中
     */
    public static final String DATA_STATUS_APPLY = "05"; 
    /**
     * 审批完成
     */
    public static final String DATA_STATUS_DONE = "10"; 
    /**
     * 无流程状态，已校验
     */
    public static final String DATA_STATUS_NF_DONE = "20"; 
}