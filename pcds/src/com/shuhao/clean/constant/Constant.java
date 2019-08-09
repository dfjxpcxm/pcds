/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.shuhao.clean.constant;

/**
 * 普通常量，标识符号
 * @author gongzy
 *
 */
public interface Constant {

	/**
	 * 占位符
	 */
	public static final String PLACEHOLDER = "&nbsp;";
	
	/**
	 * 总行机构号
	 */
	public static final String ChiefOrgID = "8888";
	/**
	 * 菜单根节点ID ,补录平台
	 */
	public static String ROOT_RESOURCE_ID = "root";
	/**
	 * 产品根节点ID
	 */
	public static String ROOT_PRODUCT_ID = "root";
    
    /**
     * 对照账户批量导入
     */
    public static final String IMPORT_CUSTMGR_ACCT_INDEX = "1";

    /**
     * 现金出入库业务量导入
     */
    public static final String IMPORT_CASH_INOUT_INDEX = "2";

    
    /*
	 * oracle与informix使用日期函数时,日期格式不一样,oracle为 yyyy/mm/dd,informix 为 %Y/%m/%d,
	 * 当数据库变更时,应使用相应的日期格式.
	 */
	public static final String DATE_FORMAT = "yyyy/mm/dd";
	
	//导出前缀标识
	public static final String EXPORT_SESSION_KEY = "export_";
	//异步导出 状态标识
	public static  String ASYNC_EXPORT_KEY ="async_export_";
	
	/**
	 * 默认管理员角色ID
	 */
	public static final String MANAGER_ROLE_ID = "Executive"; //管理员角色ID
	
	/**
	 * 菜单是否加密
	 */
	public static final boolean EncrypyMenu = false; //管理员角色ID
	
	/**
	 * 数据库编码
	 */
	public static final String DB_CODE = "gbk"; //数据库编码
	//是否需要转码
	public static final boolean IS_TRANS_DB_CODE = true; //
	
}
