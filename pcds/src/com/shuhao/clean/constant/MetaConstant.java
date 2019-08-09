package com.shuhao.clean.constant;

public interface MetaConstant {
	
	/**
	 * 默认元数据id默认值
	 */
	public static final String DEFAULT_METADATA_ID = "default"; 
	
	/**
	 * 数据库前缀
	 */
	public static final String PREFIX_DATABASE="db";
	
	/**
	 * 表空间前缀
	 */
	public static final String PREFIX_TABLESPACE="sp";
	
	/**
	 * 数据库用户前缀
	 */
	public static final String PREFIX_DATABASE_USER="ur";
	
	/**
	 * 表前缀
	 */
	public static final String PREFIX_TABLE="tb";
	
	/**
	 * 列前缀
	 */
	public static final String PREFIX_COLUMN="cl";
	
	/**
	 * 主题前缀
	 */
	public static final String PREFIX_THEME="th";
	/**
	 * 功能页面前缀
	 */
	public static final String PREFIX_PAGE="pg";
	
	/**
	 * 工具条前缀
	 */
	public static final String PREFIX_TOOLBAR="ba";
	
	
	/**
	 * 显示字段,component
	 */
	public static final String PREFIX_CP="cp";
	
	/**
	 * 元数据,component
	 */
	public static final String PREFIX_ME="me";

	
	/**
	 * 数据库元数据初始化父级ID
	 */
	public static final String META_DATA_PARENT_DATABASE = "database";
	
	/**
	 * 元数据类型 暂时使用变量（数据库）
	 */
	public static final String META_DATA_CATE_DATABASE = "101020";
	
	/**
	 * 元数据类型 （表空间）
	 */
	public static final String META_DATA_CATE_TABLESPACE = "101021";
	
	/**
	 * 元数据类型 （数据库用户）
	 */
	public static final String META_DATA_CATE_DATABASE_USER = "101022";
	
	/**
	 * 公共隐藏组件,模版ID
	 */
	public static final String HIDDEN_TEMPLATEID = "_templateId";
	
	/**
	 * 特殊处理1：子模版用到的参数 PARENT_TABLE
	 */
	public static final String HIDDEN_PARENT_TABLE = "PARENT_TABLE";
	
	public static final String MEAT_APPLY_SUBMIT = "01";   //提交审批
	public static final String META_APPLY_BACK = "02";        //撤回审批
	public static final String META_APPLY_APPROVE = "03";//一级审批
	public static final String META_APPLY_APPROVE_ = "04";//二级审批
	
	//元数据分类代码定义
	/**
	 * 数据库
	 */
	public static final String CATEGORY_TYPE_DATABASE = "DB"; 
	/**
	 * 数据库用户
	 */
	public static final String CATEGORY_TYPE_USER = "USR"; 
	/**
	 * 主题
	 */
	public static final String CATEGORY_TYPE_THEME = "THM"; 
	/**
	 * 表
	 */
	public static final String CATEGORY_TYPE_TABLE = "TAB"; 
	/**
	 * 表字段
	 */
	public static final String CATEGORY_TYPE_COLUMN = "COL"; 
	/**
	 * 功能页面
	 */
	public static final String CATEGORY_TYPE_PAGE = "PAG"; 
	/**
	 * 功能字段列表
	 */
	public static final String CATEGORY_TYPE_COLFIELD_LIST = "FDL"; 
	/***
	 * 工具条
	 */
	public static final String CATEGORY_TYPE_TOOLBAR = "TBA"; 
	/**
	 * 工具条按钮
	 */
	public static final String CATEGORY_TYPE_TOOLBAR_BUTTON = "TBT"; 
	/**
	 * 表单
	 */
	public static final String CATEGORY_TYPE_FORM = "FRM"; 
	/***
	 * 表单按钮
	 */
	public static final String CATEGORY_TYPE_FORM_BUTTON = "FBT"; 
	/**
	 * 表单字段
	 */
	public static final String CATEGORY_TYPE_COLFIELD = "FLD"; 
}
