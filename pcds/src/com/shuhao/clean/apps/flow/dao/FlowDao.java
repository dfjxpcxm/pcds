package com.shuhao.clean.apps.flow.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppTmplate;

/**
 * 流程管理  DAO 
 * @author Wanggl
 *
 */
@MyBatisDao
public interface FlowDao {

	/**
	 * 流程状态列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> flowList(Map<String, Object> param) throws Exception;

	/**
	 * 添加流程状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	void addFlowStatus(Map<String, Object> param) throws Exception;

	/**
	 * 流程状态修改
	 * @param param
	 * @throws Exception
	 */
	void updateFlow(Map<String, Object> param) throws Exception;
	
	/**
	 * 流程状态删除
	 * @param param
	 * @throws Exception
	 */
	void delFlow(Map<String, Object> param) throws Exception;

	/**
	 * 查找流程
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getFlowById(Map<String, Object> param) throws Exception;

	/**
	 * 补录类型代码
	 * @return
	 * @throws Exception
	 */
	List<? extends Object> queryUppEnterType() throws Exception;

	/**
	 * 添加补录模板
	 * @param param
	 * @throws Exception
	 */
	void addBlmb(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改补录模板
	 * @param param
	 * @throws Exception
	 */
	void updateBlmb(Map<String, Object> param) throws Exception;

	/**
	 * 删除补录模板
	 * @param param
	 * @throws Exception
	 */
	void delBlmb(Map<String, Object> param) throws Exception;

	/**
	 * 查询补录模板
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findBlmbById(Map<String, Object> param) throws Exception;

	/**
	 * 查询 所有表信息
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllTable() throws Exception;

	/**
	 * 建立补录模板与页面
	 * @param param
	 * @throws Exception
	 */
	void addBlmbPage(Map<String, Object> param) throws Exception;
	

	/**
	 * 删除补录模板与表关系
	 * @param param
	 * @throws Exception
	 */
	void delBlmbPage(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询节点对应表信息
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> nodeTableList(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询节点对应功能信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> nodeGnList(Map<String, Object> param) throws Exception;
	
	/**
	 * 申请页面  list
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getApplyList(Map<String, Object> param) throws Exception;
	
	/**
	 * 申请页面的提交与撤回
	 * 前端需传一个参数  apply_status  '02' 提交      '03'撤回
	 * 	1	00	初始	
		2	01	编辑	
		3	02	待审		
		4	03	撤回		
		5	04	退回		
		6	05	通过		
	 * @return
	 * @throws Exception
	 */
	void applyFlow(Map<String, Object> param) throws Exception;
	
	/**
	 * 添加流水
	 * @param param
	 * @throws Exception
	 */
	void addApplyTrans(Map<String, Object> param) throws Exception;
	
	/**
	 * 撤回操作
	 * @param param
	 * @throws Exception
	 */
	void reback(Map<String, Object> param) throws Exception;
	
	/**
	 * 一级审批
	 * @param param
	 * @throws Exception
	 */
	void reApprove(Map<String, Object> param) throws Exception;
	
	/**
	 * 终审
	 * @param param
	 * @throws Exception
	 */
	void resultApprove(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询所有流程模板
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllFlowTmpl() throws Exception;

	/**
	 * 建立模板流程关系
	 * @param param
	 * @throws Exception
	 */
	void addblmbFlow(Map<String, Object> param) throws Exception;

	/**
	 * 删除补录模板与流程模板关系
	 * @param param
	 * @throws Exception
	 */
	void delBlmbFlow(Map<String, Object> param) throws Exception;

	/**
	 * 审批页面    list
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getApproveList(Map<String, Object> param) throws Exception;
	
	/**
	 * 申请页面  查询总数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int getApplyListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 审批页面  查询总数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int getApproveListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 插入审批流水
	 * @param param
	 * @throws Exception
	 */
	void addApproTrans(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改流程状态
	 * @param param
	 * @throws Exception
	 */
	void updateApproStatus(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询所有模版
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<UppTmplate> getAllTmpl(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询差额信息
	 * @param param
	 * @return
	 */
	String queryTotalCe(Map<String, Object> param) throws Exception;
	
	/**
	 * 更新补录模板与功能关系    先删除再新增
	 * @param param
	 * @throws Exception
	 */
	void delAndAddBlmbGn(String metadata_id) throws Exception;

	/**
	 * 由模板获取表名
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String getTableName(Map<String, Object> param) throws Exception;

	/**
	 * 由模板获取产品名
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String getProductId(Map<String, Object> param) throws Exception;
	
	/**
	 * 批量提交流程
	 * @param list
	 * @throws Exception
	 */
	void applyFlowList(List<Map<String,Object>> list) throws Exception;
	
	/**
	 * 批量撤回流程
	 * @param list
	 * @throws Exception
	 */
	void rebackList(List<Map<String,Object>> list) throws Exception;
	
	/**
	 * 批量通过流程
	 * @param list
	 * @throws Exception
	 */
	void approveList(List<Map<String,Object>> list) throws Exception;
	
	/**
	 * 批量退回流程
	 * @param list
	 * @throws Exception
	 */
	void returnList(List<Map<String,Object>> list) throws Exception;
	
	/**
	 * 根据数据ID获取任务
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String  getTaskByDateId(String data_id) throws Exception;
	
	/**
	 * 获取用户模板信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getBlmbByUserId(Map<String, Object> param) throws Exception;
	
	/**
	 * 获取模板
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listBlmb() throws Exception;
	
	/**
	 * 添加用户模板
	 * @param list
	 * @throws Exception
	 */
	void addUserBlmb(List<Map<String, Object>> list) throws Exception;
	
	/**
	 * 添加用户模板
	 * @param list
	 * @throws Exception
	 */
	void addUserBlmb2(List<Map<String, Object>> list) throws Exception;

	/**
	 * 删除用户模板   每次添加用户模板之前先清空
	 * @param param
	 * @throws Exception
	 */
	void delUserBlmb(Map<String, Object> param) throws Exception;

	/**
	 * 删除流程   删除流水及结果表
	 * @param param
	 * @throws Exception
	 */
	void deleteFlow(Map<String, Object> param) throws Exception;
	
	/**
	 * 获取模板用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getUserByBlmb(Map<String, Object> param) throws Exception;
	
	/**
	 * 用户列表总数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int getUserListCount(Map<String, Object> param) throws Exception;

	/**
	 * 用户列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getUserList(Map<String, Object> param) throws Exception;

	/**
	 * 移除用户模板权限
	 * @param list
	 * @throws Exception
	 */
	void removeUser(List<Map<String, Object>> list) throws Exception;
	

	int getUserByBlmbCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增流程信息
	 * @param param
	 * @throws Exception
	 */
	void addFlowInfo(Map<String, Object> param) throws Exception;
	
	/**
	 * 修改流程信息
	 * @param param
	 * @throws Exception
	 */
	void updateFlowInfo(Map<String, Object> param) throws Exception;
	
	/***
	 * 删除流程信息
	 * @param param
	 * @throws Exception
	 */
	void deleteFlowInfo(Map<String, Object> param) throws Exception;

	String getJsonData(Map<String, Object> param) throws Exception;

	List<Map<String, Object>> listAllFlow() throws Exception;

	/***
	 * 新增流程节点信息
	 * @param list
	 * @throws Exception
	 */
	void addNodeDetail(List<Object> list) throws Exception;
	
	/***
	 * 删除流程节点信息
	 * @param list
	 * @throws Exception
	 */
	void deleteNodeDetail(Map<String, Object> param) throws Exception;

	/**
	 * @return
	 */
	List<Map<String, Object>> listAllPage();

	/**
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> getPageInfo(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPageFdl(Map<String, Object> map);

	/**
	 * @param param
	 */
	void setCurrent(Map<String, Object> param);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getFlowInfo(Map<String, Object> map);

	/**
	 * @param param
	 */
	void publicBlmb(Map<String, Object> param);

}
