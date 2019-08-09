package com.shuhao.clean.apps.flow.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface IWorkFlowDao {
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
	public void delWorkFlow(List<String> params)throws Exception;
	
	/**
	 * 发布流程信息(支持批量)
	 * @param params
	 * @throws Exception
	 */
	public void pubWorkFlow(List<String> params)throws Exception;
	
	/**
	 * 取消发布流程信息(支持批量)
	 * @param params
	 * @throws Exception
	 */
	public void rebPubWorkFlow(List<String> params)throws Exception;
	
	/**
	 * 校验流程信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int checkWorkFlow(String flow_tmpl_id)throws Exception;
	
	
	/**
	 * 获取人机交互节点权限信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getBankOrg(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getUserRole() throws Exception;
	/**
	 * 查部门
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getDeptInfo(Map<String,Object> params) throws Exception;
	/**
	 * 查询岗位
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getPostInfo() throws Exception;
	/**
	 * 查询团队
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTeamInfo() throws Exception;
	
	public List<Map<String,Object>> getUserInfo(Map<String,Object> params) throws Exception;
	
	public int getUserInfoCount(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getCurrentUserRole(Map<String,Object> params) throws Exception;
	
	//-----------------------------以下操作为流程自动操作，为保证数据完整性，禁止手工去维护数据-----------------------------------------
	//不包含数据库事务
	
	/**
	 * 获取审批详细信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getApproveInfo(Map<String,Object> params) throws Exception;
	
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
	public List<Map<String,Object>> getApproveSeqByNo(Map<String,Object> params) throws Exception;
	
	/**
	 * 新增审批流水信息
	 * @param params
	 * @throws Exception
	 */
	public void addApproveSeq(Map<String,Object> params) throws Exception;
	
	/**
	 * 新增审批记录
	 * @param params
	 * @throws Exception
	 */
	public void addApproveInfo(Map<String,Object> params) throws Exception;
	
	/**
	 * 修改审批流水信息
	 * @param params
	 * @throws Exception
	 */
	public void editApproveSeq(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除审批流水信息
	 * @param params
	 * @throws Exception
	 */
	public void delApproveSeq(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除审批记录信息
	 * @param params
	 * @throws Exception
	 */
	public void delApproveInfo(Map<String,Object> params) throws Exception;
	
	/**
	 * 修改审批状态
	 * @param params
	 * @throws Exception
	 */
	public void editFlowSeqInfo(Map<String,Object> params) throws Exception;

	
	/**
	 * 开始进入审批流
	 * @param params
	 * @throws Exception
	 */
	public void startApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 编辑审批
	 * @param params
	 * @throws Exception
	 */
	public void startApproveFlow(Map<String,Object> params) throws Exception;
	
	/**
	 * 审批操作
	 * @param params
	 * @throws Exception
	 */
	public void doApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除审批信息
	 * @param params
	 * @throws Exception
	 */
	public void delApprove(Map<String,Object> params) throws Exception;
	
	/**
	 * 按业务编号删除
	 * @param params
	 * @throws Exception
	 */
	public void delApproveByNo(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除审批信息
	 * @param params
	 * @throws Exception
	 */
	public void deleteApproveMsg(Map<String,Object> params) throws Exception;
	
	/**
	 * 判断数据为新增还是修改
	 * @param params
	 * @throws Exception
	 */
	public int checkUpd(Map<String,Object> params) throws Exception;
}