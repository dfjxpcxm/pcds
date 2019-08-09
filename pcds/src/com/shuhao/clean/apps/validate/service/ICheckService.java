/**
 * FileName:     ICheckService.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 下午7:42:14 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-28       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public interface ICheckService {
	
	/**
	 * 普通单值校验 
	 * @param metaId 元数据
	 * @param value  元数据的值
	 * @param contect 通用参数
	 * @throws Exception
	 */
	public List<IMessage> cellValid(String metaId,String value,Map<String, Object> contect)throws Exception;
	
	/**
	 * 批量分解单值校验 
	 * @param metaId 父级元数据ID
	 * @param map
	 * @param contect 通用参数
	 * @throws Exception
	 */
	public List<IMessage> cellsValid(String metaId,Map<String, Object> map,Map<String, Object> contect)throws Exception;
	
	/**
	 * 行级、表级别校验
	 * @param metaId 页面元数据ID
	 * @param map
	 * @param contect 通用参数
	 * @return
	 * @throws Exception
	 */
	public List<IMessage> rowValid(String metaId,Map<String, Object> map,Map<String, Object> contect)throws Exception;
	
	/**
	 * 行级、表级别校验
	 * @param metaId
	 * @param map
	 * @param contect
	 * @param isTrans 是否需要转出参数map中的key "_"
	 * @return
	 * @throws Exception
	 */
	public List<IMessage> rowValid(String metaId,Map<String, Object> map,Map<String, Object> contect,boolean isTrans)throws Exception;
	
	/**
	 * 表元数据校验
	 * @param map : templateId,metadataType
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validTableMeta(Map<String, Object> map,Map<String, Object> data,Map<String, Object> context,boolean isTrans);
	
	/**
	 * 功能元数据校验
	 * @param map : templateId,metadataType
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validFunctionMeta(Map<String, Object> map,Map<String, Object> data,Map<String, Object> context,boolean isTrans);
	
	/**
	 * excel sheet校验
	 * @param param
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validExcelSheet(Map<String, Object> param,Map<String, Object> data,Map<String, Object> context,boolean isTrans) ;
	
	/**
	 * 校验excel sheet关联的table
	 * @param param
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validExcelSheetTable(Map<String, Object> param,Map<String, Object> data,Map<String, Object> context,boolean isTrans) ;

		
	/**
	 * 行级校验
	 * @param metas 元数据列表
	 * @param data 传入数据
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validRowMeta(List<UppMetadata> metas,Map<String, Object> data,Map<String, Object> context,boolean isTrans);

	/**
	 * 导入完成后校验x
	 * @param metas
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validAfterImport(Map<String, Object> params,Map<String, Object> data,Map<String, Object> context);
	
	/**
	 * 提交申请时执行校验
	 * @param params
	 * @param data
	 * @param context
	 * @return
	 */
	public Map<String, Object> validSubmitApprove(Map<String, Object> params, Map<String, Object> context);
}
