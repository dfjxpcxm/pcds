package com.shuhao.clean.apps.flow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.flow.dao.IWorkFlowDao;
import com.shuhao.clean.apps.flow.service.IWorkFlowService;
import com.shuhao.clean.apps.meta.dao.BckTrackingMetaDao;
import com.shuhao.clean.apps.meta.dao.PageManagerDao;
import com.shuhao.clean.utils.GlobalUtil;

@Service(value = "workFlowService")
public class WorkFlowServiceImpl implements IWorkFlowService {

	@Autowired
	public IWorkFlowDao workFlowDao;
	
	@Autowired
	public BckTrackingMetaDao bakTrackMetaDao;
	@Autowired
	public PageManagerDao pageManagerDao;

	/**
	 * 查询流程数据
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getWorkFlowList(Map<String, Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao
				.getWorkFlowList(params));
	}

	/**
	 * 查询流程数据总数
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getWorkFlowSize(Map<String, Object> params) throws Exception {
		return workFlowDao.getWorkFlowSize(params);
	}

	/**
	 * 新增流程信息
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void addWorkFlow(Map<String, Object> params) throws Exception {
		workFlowDao.addWorkFlow(params);
	}

	/**
	 * 通过ID 获取流程信息
	 * 
	 * @param flowId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getWorkFlowById(Map<String, Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao
				.getWorkFlowById(params));
	}

	/**
	 * 编辑流程信息
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void editWorkFlow(Map<String, Object> params) throws Exception {
		workFlowDao.editWorkFlow(params);
	}

	/**
	 * 删除流程信息(支持批量)
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void deleteWorkFlow(List<String> params) throws Exception {
		workFlowDao.delWorkFlow(params);
	}

	/**
	 * 发布流程信息(支持批量)
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void pubWorkFlow(List<String> params, String _status)
			throws Exception {
		if ("Y".equals(_status)) {
			workFlowDao.pubWorkFlow(params);
		} else {
			workFlowDao.rebPubWorkFlow(params);
		}

	}

	/**
	 * 校验流程信息
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean checkWorkFlow(List<String> params) throws Exception {
		for (int i = 0; i < params.size(); i++) {
			if (workFlowDao.checkWorkFlow(params.get(i)) > 0) {
				return false;
			}
		}
		return true;
	}

	public List<Map<String, Object>> getBankOrg(Map<String, Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getBankOrg(params));
	}

	public List<Map<String, Object>> getDeptInfo(Map<String, Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getDeptInfo(params));
	}

	public List<Map<String, Object>> getPostInfo() throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getPostInfo());
	}

	public List<Map<String, Object>> getTeamInfo() throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getTeamInfo());
	}

	public List<Map<String, Object>> getUserInfo(Map<String,Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getUserInfo(params));
	}
	
	public int getUserInfoCount(Map<String,Object> params) throws Exception{
		return workFlowDao.getUserInfoCount(params);
	}

	public List<Map<String, Object>> getUserRole() throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getUserRole());
	}
	
	public List<String> getCurrentUserRole(Map<String,Object> params) throws Exception{
		List<String> roleIds = new ArrayList<String>();
		List<Map<String, Object>> roles = GlobalUtil.lowercaseListMapKey(workFlowDao.getCurrentUserRole(params));
		for (Map<String, Object> map : roles) {
			roleIds.add(map.get("role_uuid").toString());
		}
		return roleIds;
	}
	
	/**
	 * 查询用户对应权限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<String> getPermissionUserId(Map<String,Object> params)throws Exception{
		List<String> ids = new ArrayList<String>();
		return ids;
	}
	
	/**
	 * 获取审批流水信息
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getApproveSeq(Map<String, Object> params)
			throws Exception {
		return GlobalUtil
				.lowercaseListMapKey(workFlowDao.getApproveSeq(params));
	}

	public List<Map<String, Object>> getApproveById(Map<String, Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(workFlowDao
				.getApproveInfo(params));
	}

	
	//----------------------不带事务的循环执行---------------------------
	/**
	 * 开始进入审批流程
	 * @param paramList
	 * @return
	 * @throws Exception
	 */
	public void startApprove(Map<String, Object> params)throws Exception {
		workFlowDao.startApprove(params);
		/*String business_no = String.valueOf(params.get("business_no"));
		String templateId = String.valueOf(params.get("templateId"));
		doTransaction("start",business_no,templateId);*/
	}
	
	/**
	 * 开始执行审批（新），新增审批流水，修改审批状态
	 * @param params
	 * @throws Exception
	 */
	public void startApproveFlow(Map<String, Object> params)throws Exception {
		workFlowDao.startApproveFlow(params);
	}
	
	/**
	 * 执行审批操作
	 * @param paramList
	 * @return
	 * @throws Exception
	 */
	public void doApprove(Map<String, Object> params) throws Exception {
		//取消对屋里补录表的操作
		/*String task_status = String.valueOf(params.get("task_status"));
		String business_no = String.valueOf(params.get("business_no"));
		String templateId = String.valueOf(params.get("templateId"));
		//完成
		if(WorkFlowConstant.WORK_FLOW_APPLY_DONE.equals(task_status)){
			doTransaction("done",business_no,templateId); //完成
		}else if(WorkFlowConstant.WORK_FLOW_APPLY_BACK.equals(task_status)){
			doTransaction("start",business_no,templateId);
		}else if(WorkFlowConstant.WORK_FLOW_APPLY_RESUB.equals(task_status) ){
			doTransaction("start",business_no,templateId);
			params.put("task_status", "01"); //???
		}else if(WorkFlowConstant.WORK_FLOW_APPLY_EDIT.equals(task_status)){
			doTransaction("edit",business_no,templateId);
		}
		*/
		workFlowDao.doApprove(params);
	}
	
	/**
	 * 添加审批流水信息
	 * @param params
	 * @throws Exception
	 */
	public void addApproveSeq(Map<String, Object> params) throws Exception {
		workFlowDao.addApproveSeq(params);
	}
	
	/**
	 * 数据状态: 05 主动撤回,10 完成,00 编辑,01 待审
	 * @param action = start,upd,edit 将审批的状态分类到数据状态
	 * @param businessNo
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public boolean doTransaction(String action, String businessNo,String templateId)
			throws Exception {
		
		return true;
		/*try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("tmpl_id", templateId);  //模板ID
			String tabName = pageManagerDao.getTabNameByTempId(param);
			param.put("tabName", tabName);
			param.put("business_no", businessNo);
			//	param.put("flow_code", "10");
			
			if(action.equals("del")){
				bakTrackMetaDao.delBusinessData(param);
			}
			else{
				if(action.equals("start")){
					param.put("flow_code", "05");
				}else if(action.equals("done")){ //完成
					param.put("flow_code", "10");
				}else if(action.equals("edit")){
					param.put("flow_code", "00");
				}
				//去掉flow_status_code之后，没有更新
				bakTrackMetaDao.updateBusinessData(param);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
	}
	
	/**
	 * 删除审批信息
	 * @param paramList
	 * @return
	 * @throws Exception
	 */
	public void deleteApprove(Map<String, Object> params) throws Exception {
		/*String businessNo = String.valueOf(params.get("business_no"));
		String templateId = String.valueOf(params.get("templateId"));
		doTransaction("del",businessNo,templateId);*/
		workFlowDao.delApprove(params);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.IWorkFlowService#delApproveByNo(java.util.Map)
	 */
	public void deleteApproveByNo(Map<String, Object> params) throws Exception {
//		String businessNo = String.valueOf(params.get("business_no"));
//		String templateId = String.valueOf(params.get("templateId"));
//		doTransaction("edit",businessNo,templateId);
		workFlowDao.delApproveByNo(params);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.IWorkFlowService#updateApprove(java.util.Map)
	 */
	public void updateApprove(Map<String, Object> params) throws Exception {
		workFlowDao.delApproveInfo(params);
		workFlowDao.editFlowSeqInfo(params);
	}

	/**
	 * 根据业务数据查询审批数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getApproveSeqByBusinessNo(
			Map<String, Object> params) throws Exception {
		
		return GlobalUtil.lowercaseListMapKey(workFlowDao.getApproveSeqByNo(params));
	}
	
	public int checkUpd(Map<String,Object> params) throws Exception{
		return workFlowDao.checkUpd(params);
	}
	
	public void updateFlowInApprove(Map<String,Object> params)
			throws Exception {
		
		//先删除就审批信息
		workFlowDao.delApproveByNo(params);
		//插入初始流程信息
		workFlowDao.addApproveSeq(params);
	/*	try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("tmpl_id", templateId);  //模板ID
			String tabName = pageManagerDao.getTabNameByTempId(param);
			param.put("tabName", tabName);
			param.put("business_no", businessNo);
			param.put("flow_code", "00");
			//先删审批信息
			workFlowDao.deleteApproveMsg(param);
			//更新状态
			bakTrackMetaDao.updateBusinessData(param);	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
	}
}
