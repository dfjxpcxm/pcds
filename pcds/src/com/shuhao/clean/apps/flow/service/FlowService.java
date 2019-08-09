package com.shuhao.clean.apps.flow.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.utils.exttree.ExtTreeNode;

/**
 * 流程管理  service 接口
 * @author Wanggl
 *
 */
public interface FlowService {

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
	 * 查询模板信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findBlmbById(Map<String, Object> param) throws Exception;

	/**
	 * 查询所有表信息
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllTable() throws Exception;

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
	 * 审批页面的通过与退回
	 * 前端需传一个参数  approve_status  '05' 通过      '04'退回
	 * 	1	00	初始	
		2	01	编辑	
		3	02	待审		
		4	03	撤回		
		5	04	退回		
		6	05	通过		
	 * @return
	 * @throws Exception
	 */
	void approveFlow(Map<String, Object> param) throws Exception;

	/**
	 * 查询所有流程模板
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllFlowTmpl() throws Exception;

	/**
	 * 审批页面   list
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
	 */
	void updateApproStatus(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询我的补录模版
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ExtTreeNode getMyTmpl(Map<String, Object> param)throws Exception;
	
	/**
	 * 查询差额信息
	 * @param param
	 * @return
	 */
	String queryTotalCe(Map<String, Object> param) throws Exception;

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
	 * @param param
	 * @throws Exception
	 */
	void addUserBlmb(Map<String, Object> param) throws Exception;
	
	/**
	 * 添加用户模板
	 * @param param
	 * @throws Exception
	 */
	void addUserBlmb2(Map<String, Object> param) throws Exception;

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
	 * @param param
	 * @throws Exception
	 */
	void removeUser(Map<String, Object> param) throws Exception;

	int getUserByBlmbCount(Map<String, Object> param) throws Exception;

	void saveJsonData(Map<String, Object> param) throws Exception;

	String getJsonData(Map<String, Object> param) throws Exception;

	List<Map<String, Object>> listAllFlow() throws Exception;
	
	/**
	 * 删除流程信息
	 * @param param
	 * @throws Exception
	 */
	void deleteFlowInfo(Map<String, Object> param) throws Exception;

	/**补录页面列表
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
	
	/**
	 * 调用远程shell命令
	 * @return
	 * @throws Exception
	 */
	String execCmd(Map<String, Object> params) throws Exception;
}
