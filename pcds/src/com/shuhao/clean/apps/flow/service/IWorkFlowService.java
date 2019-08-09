package com.shuhao.clean.apps.flow.service;

import java.util.List;
import java.util.Map;

/**
 * 流程管理  service 接口
 * @author yangmou
 *
 */
public interface IWorkFlowService {
	/**
	 * 查询流程数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getWorkFlowList(Map<String,Object> params)throws Exception;
	
	/**
	 * 查询流程数据总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getWorkFlowSize(Map<String,Object> params)throws Exception;
	
	/**
	 * 新增流程信息
	 * @param params
	 * @throws Exception
	 */
	public void addWorkFlow(Map<String,Object> params)throws Exception;
	
	/**
	 * 通过ID 获取流程信息
	 * @param flowId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getWorkFlowById(Map<String,Object> params) throws Exception;
	
	/**
	 * 编辑流程信息
	 * @param params
	 * @throws Exception
	 */
	public void editWorkFlow(Map<String,Object> params)throws Exception;
	
	/**
	 * 删除流程信息(支持批量)
	 * @param params
	 * @throws Exception
	 */
	public void deleteWorkFlow(List<String> params)throws Exception;
	
	/**
	 * 发布流程信息(支持批量)
	 * @param params
	 * @throws Exception
	 */
	public void pubWorkFlow(List<String> params,String _status)throws Exception;
	
	/**
	 * 校验流程信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean checkWorkFlow(List<String> params)throws Exception;
	
	/**
	 * 查询机构
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getBankOrg(Map<String,Object> params) throws Exception;
	
	/**
	 * 查询机构
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getUserRole() throws Exception;
	
	/**
	 * 查询部门
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getDeptInfo(Map<String,Object> params) throws Exception;
	
	/**
	 * 查询岗位
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getPostInfo() throws Exception;
	
	/**
	 * 查询团队
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTeamInfo() throws Exception;
	
	public List<Map<String,Object>> getUserInfo(Map<String,Object> params) throws Exception;
	public int getUserInfoCount(Map<String,Object> params) throws Exception;
	
	public List<String> getCurrentUserRole(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取当前用于对应的权限信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<String> getPermissionUserId(Map<String,Object> params)throws Exception;
	
	/**
	 * 获取审批流水信息 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getApproveSeq(Map<String,Object> params) throws Exception;
	
	
	/**
	 * 根据业务数据查询审批数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getApproveSeqByBusinessNo(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取历史审批记录
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getApproveById(Map<String,Object> params) throws Exception;
	
	/**
	 * 开始进入审批流,新状态插入
	 * @param params
	 * @throws Exception
	 */
	public void startApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 开始进入审批流(修改)
	 * @param params
	 * @throws Exception
	 */
	public void startApproveFlow(Map<String,Object> params) throws Exception;
	
	/**
	 * 添加审批流水信息
	 * @param params
	 * @throws Exception
	 */
	public void addApproveSeq(Map<String, Object> params) throws Exception ;
	
	/**
	 * 执行审批操作,变更审批表upp_flow_sequences状态，和插入流水信息
	 * @param params
	 * @throws Exception
	 */
	public void doApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除审批信息,按taskId
	 * @param params
	 * @throws Exception
	 */
	public void deleteApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 按业务编号删除,按business_no
	 * @param params
	 * @throws Exception
	 */
	public void deleteApproveByNo(Map<String,Object> params) throws Exception;
	
	/**
	 * 更新审批状态
	 * @param params
	 * @throws Exception
	 */
	public void updateApprove(Map<String,Object> params) throws Exception;
	
	
	/**
	 * 判断数据为新增还是修改
	 * @param params
	 * @throws Exception
	 */
	public int checkUpd(Map<String,Object> params) throws Exception;
	
	/**
	 * 审批的时候，更改了审批流程
	 * @param params
	 * @throws Exception
	 */
	public void updateFlowInApprove(Map<String,Object> params) throws Exception ;
}