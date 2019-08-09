package com.shuhao.clean.apps.flow.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shuhao.clean.apps.flow.entity.BaseFlow;
import com.shuhao.clean.apps.flow.entity.FlowNode;
import com.shuhao.clean.apps.flow.service.IWorkFlowService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.WorkFlowConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;
import com.shuhao.clean.utils.workflow.SequenceUtil;
import com.shuhao.clean.utils.workflow.WorkFlowUtil;

/**
 * 流程工具
 * @author yangmou
 * 
 */
@Controller
@RequestMapping("/workflow")
public class WorkFlowCtrlr extends BaseCtrlr{
	
	@Autowired
	private IWorkFlowService workFlowService;
	
	/**
	 * 列表展示系统所有流程信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getWorkFlowList")
	@ResponseBody
	public Object getWorkFlowList()throws Exception{
		try {
			Map<String, Object> paramsMap = super.getRequestParam();
			List<Map<String,Object>> result = workFlowService.getWorkFlowList(paramsMap);
			int totalCount = workFlowService.getWorkFlowSize(paramsMap);
			request.setAttribute("total", totalCount);
			return doJSONResponse(result);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取流程失败,错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 新增流程信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addWorkFlow")
	@ResponseBody
	public Object addWorkFlow()throws Exception{
		String index = UID.getInstance();
		Date date = new Date();
		String date_str = new SimpleDateFormat("yyyyMMdd").format(date);
		SysUserInfo user = this.getCurrentUser();
		String user_id = user.getUser_id();
		try {
			Map<String, Object> params = super.getRequestParam();
			params.put("flow_tmpl_id", "FLOW"+date_str+index);
			params.put("create_user_id", user_id);
			params.put("status_cd", "N");
			params.put("display_order", 1);
			workFlowService.addWorkFlow(params);
			return doSuccessInfoResponse("流程保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("流程保存失败,错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	
	/**
	 * 获取编辑流程信息
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="getFlowById")
	@ResponseBody
	public Object getFlowById(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		try {
			Map<String,Object> params = super.getRequestParam();
			List<Map<String,Object>> result = workFlowService.getWorkFlowById(params);
			if (result != null && result.size() > 0) {
				Map<String,Object> res = result.get(0);
				String json = String.valueOf(res.get("json_code"));
				return doSuccessInfoResponse(json);
			}else{
				return doSuccessInfoResponse("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
		
	}
	
//	@RequestMapping(value="getWorkFlowById")
//	public void getWorkFlowById(HttpServletRequest req,HttpServletResponse resp)throws Exception{
//		try {
//			Map<String,Object> params = super.getRequestParam();
//			List<Map<String,Object>> result = workFlowService.getWorkFlowById(params);
//			if (result != null && result.size() > 0) {
//				Map<String,Object> res = result.get(0);
//				String json = String.valueOf(res.get("json_code"));
//				XMLSerializer xml = new XMLSerializer();
//				xml.setTypeHintsEnabled(false);
//				JSON jsons = (new JSONObject()).element("workflow",json);
//			    String flow_xml = StringUtils.substringBetween(xml.write(jsons), "<o>", "</o>");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	/**
	 * 编辑流程信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="eidtWorkFlow")
	@ResponseBody
	public Object eidtWorkFlow()throws Exception{
		try {
			Map<String, Object> params = super.getRequestParam();
			SysUserInfo user = this.getCurrentUser();
			String user_id = user.getUser_id();
			params.put("update_user_id", user_id);
			workFlowService.editWorkFlow(params);
			return doSuccessInfoResponse("流程保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("流程保存失败,错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 删除流程信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delWorkFlow")
	@ResponseBody
	public Object delWorkFlow()throws Exception{
		
		try {
			List<String> params = new ArrayList<String>();
			String _key = request.getParameter("key");
			if (_key.indexOf("#") > -1) {
				if (_key.endsWith("#")) {
					_key = _key.substring(0, _key.length()-1);
					for (int i = 0; i < _key.split("#").length; i++) {
						params.add(_key.split("#")[i]);
					}
				}else{
					for (int i = 0; i < _key.split("#").length; i++) {
						params.add(_key.split("#")[i]);
					}
				}
			}
			workFlowService.deleteWorkFlow(params);
			return doSuccessInfoResponse("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败,错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 发布流程信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="pubWorkFlow")
	@ResponseBody
	public Object pubWorkFlow()throws Exception{
		try {
			List<String> params = new ArrayList<String>();
			String _status = request.getParameter("status_cd");
			String _key = request.getParameter("key");
			if (_key.indexOf("#") > -1) {
				if (_key.endsWith("#")) {
					_key = _key.substring(0, _key.length()-1);
					for (int i = 0; i < _key.split("#").length; i++) {
						params.add(_key.split("#")[i]);
					}
				}else{
					for (int i = 0; i < _key.split("#").length; i++) {
						params.add(_key.split("#")[i]);
					}
				}
			}
			
			//取消发布时 检查流程是否被引用
			if(_status.equals("N")){
				boolean b = workFlowService.checkWorkFlow(params);
				if (!b) 
					return doFailureInfoResponse("操作失败,流程已被引用");
			}	
			workFlowService.pubWorkFlow(params,_status);
			return doSuccessInfoResponse("操作成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("操作失败,错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	
	//
	//***************************************************************
	//**                                                           **
	//**                   获取人机交换节点权限选择信息。                                **
	//**                                                           **
	//***************************************************************
	/**
	 * 获取机构/部门 树信息
	 * @return
	 */
	@RequestMapping(value="getMsgInfo/{dim_code}")
	@ResponseBody
	public Object getBankOrg(@PathVariable String dim_code){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String nodeId = request.getParameter("nodeId");
			params.put("nodeId", nodeId);
			if (dim_code.equals("bankOrgId")) {
				List<Map<String,Object>> result = workFlowService.getBankOrg(params);
				return doJSONResponse(result);
			}else{
				List<Map<String,Object>> result = workFlowService.getDeptInfo(params);
				return doJSONResponse(result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("后台错误，错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 获取角色信息
	 * @return
	 */
	@RequestMapping(value="getUserRole")
	@ResponseBody
	public Object getUserRole(){
		try {
			List<Map<String,Object>> result = workFlowService.getUserRole();
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("后台错误，错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 获取岗位信息
	 * @return
	 */
	@RequestMapping(value="getPostInfo")
	@ResponseBody
	public Object getPostInfo(){
		try {
			List<Map<String,Object>> result = workFlowService.getPostInfo();
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("后台错误，错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 获取团队信息
	 * @return
	 */
	@RequestMapping(value="getTeamInfo")
	@ResponseBody
	public Object getTeamInfo(){
		try {
			List<Map<String,Object>> result = workFlowService.getTeamInfo();
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("后台错误，错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/**
	 * 获取人员信息
	 * @return
	 */
	@RequestMapping(value="getUserInfo")
	@ResponseBody
	public Object getUserInfo(){
		try {
			Map<String,Object> params = this.getRequestParam();
			List<Map<String,Object>> result = workFlowService.getUserInfo(params);
			int totalCount = workFlowService.getUserInfoCount(params);
			request.setAttribute("total", totalCount);
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("后台错误，错误信息如下：<br/>"+e.getMessage());
		}
	}
	
	/****************************************************************
	 **                                                            **
	 **                   流程和业务关联部分                          **
	 **                                                            **
	 ****************************************************************
	 */
	
	@RequestMapping(value="getApproveSeq/{taskId}")
	@ResponseBody
	public Object getApproveSeq(@PathVariable String taskId){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("task_id", taskId);
		try {
			List<Map<String, Object>> result = workFlowService.getApproveById(params);
			if(result != null && result.size() > 0){
				Map<String, Object> seq = result.get(0);
				//缓存
				session.setAttribute("current_node_id", seq.get("current_node_id"));
				session.setAttribute("next_node_id", seq.get("next_node_id"));
			}
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取审批流水记录失败");
		}
	}
	
	
	/**
	 * 获取历史审批详情
	 * @return
	 */
	@RequestMapping(value="doTrackApprove/{taskId}")
	@ResponseBody
	public Object doTrackApprove(@PathVariable String taskId){
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("task_id", taskId);
			List<Map<String,Object>> result = workFlowService.getApproveById(params);
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取历史审批记录失败");
		}
	}
	
	/**
	 * 执行审批操作 <br>
	 * gongzhiyang : 增加：<br>
	 * 执行按钮类型：buttonType = 00提交，01撤回，02跟踪，03流程详情,-1不处理类型
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="doApprove")
	@ResponseBody
	public Object doApprove(){
		try {
			//操作按钮类型
			String buttonType = request.getParameter("buttonType"); //按钮类型，00提交
			//提交时执行校验，取校验结果，如果校验不通过，返回校验信息
			if(buttonType.equals("00") && request.getAttribute("validErrors")!=null){
				Map<String, Object> errors = (Map<String, Object>)request.getAttribute("validErrors");
				if(!errors.isEmpty()){
					return errors;
				}
			}
			
			SysUserInfo user = getCurrentUser();
			String userId = user.getUser_id();  //当前用户ID
			String businessNos  = request.getParameter("business_nos"); //业务编号
			String approveValue = request.getParameter("approveValue"); //审批操作
			String approveMsg = request.getParameter("approveMsg");//审批意见
			String workflowId = request.getParameter("workflowId");//流程ID
			String templateId = request.getParameter("templateId");//模板ID
			
			//参数
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("templateId", templateId);
			
		    Map<String,List<BaseFlow>> flowMap = getFlowNodesList(workflowId);
			
			FlowNode Fnode = WorkFlowUtil.getFristTaskNode(flowMap);  //流程中第一个审批节点
			
			//获取当前登陆用户在流程中的对应节点(需要循环)
			List<FlowNode> Cnodes = (List<FlowNode>)session.getAttribute("CurrentNode");
			
			//原始流程ID
			String olaFlowId = ""; //校验流程是否发生变化
			
			//开始拆分业务数据 
			String [] businessNoAry = businessNos.split("_");
			for (int t = 0; t < businessNoAry.length; t++) {
				String businessNo = businessNoAry[t];
				Map<String,Object> Qparams = new HashMap<String, Object>();
				Qparams.put("business_no", businessNo);
				
				//通过业务编号获取任务编号
				List<Map<String,Object>> sequences = workFlowService.getApproveSeqByBusinessNo(Qparams);
				String taskId = null;
				String next_node_id = null;
				FlowNode cnode = null;
				
				//当前节点,currentNodeId = -1 表示新增时的初始记录
				String currentNodeId = null;
				
				//判断是新增的记录
				if(sequences != null && sequences.size() > 0){
					currentNodeId = GlobalUtil.getStringValue(sequences.get(0), "current_node_id");
					olaFlowId = String.valueOf(sequences.get(0).get("flow_id"));
					
					//判断流程是否发生变更，一旦流程发生变更，修改数据该条数据为编辑状态，
					if(!olaFlowId.equals(workflowId)){
						//更新新流程状态
						//删除upp_flow_sequences,upp_flow_approve_info ; 修改审批状态
						
						params.put("task_id", SequenceUtil.next("t"));
						params.put("flow_id", workflowId);
						params.put("current_node_id", "-1");   //当前审批人节点id
						params.put("next_node_id", "-1");  //下级审批人节点id
						params.put("task_status", "-1");  //状态节点
						params.put("create_user", userId);
						params.put("business_no", businessNo);
						
						workFlowService.updateFlowInApprove(params);
						return doFailureInfoResponse("检测到当前审批流程发生变更，还原数据为编辑状态!");
					}
					
					//取任务ID
					taskId = String.valueOf(sequences.get(0).get("task_id"));
					//如果不是初始化节点
					if(!currentNodeId.equals("-1")){
						//取下级节点
						next_node_id = String.valueOf(sequences.get(0).get("next_node_id"));
						
						//判断是否走到用户所在节点
						for (int i = 0; i < Cnodes.size(); i++) {
							FlowNode node = Cnodes.get(i);
							if(node.getId().equals(next_node_id)){
								cnode = node;
								break;
							}
						}
						
						//判断节点为null
						if(cnode==null){
							//判断是否有审批节点
							for (int i = 0; i < Cnodes.size(); i++) {
								FlowNode node = Cnodes.get(i);
								//是审批节点
								if(node.isVcheck()) {
									return doFailureInfoResponse("第["+(t+1)+"]条记录审批失败，当前用户没有权限。");
								}
							}
						}
					}
				}
				
				//没有节点 = 下级节点，并且执行的是提交
				if(cnode == null && buttonType.equals("00")){
					cnode = Fnode;
				}
				
				//当前用户为第一个人机交互节点的权限匹配(当前节点可能为录入节点或者流入修改节点)
				if (cnode.getId().equals(Fnode.getId())) {
					//获取当前登陆节点的下级节点 
					List<FlowNode> nnodes = WorkFlowUtil.getNextNode(flowMap, cnode);
					//新增的记录
					if(currentNodeId!=null && currentNodeId.equals("-1")){
						//新增流程信息
						params.put("task_id", taskId);
						params.put("flow_id", workflowId);
						params.put("current_node_id", cnode.getId());           //当前审批人节点id
						params.put("next_node_id", nnodes.get(0).getId());      //下级审批人节点id
						params.put("task_status", WorkFlowConstant.WORK_FLOW_APPLY_WAIT); 
						
						params.put("business_no", businessNo);
						//审批流水
						params.put("approve_user", userId);
						params.put("approve_role", cnode.getName());
						params.put("approve_info", "提交");
						params.put("approve_desc", approveMsg);
						params.put("templateId", templateId);
						
						//更新upp_flow_sequence,新增upp_approve_info
						workFlowService.startApproveFlow(params);
					}else{
						if(taskId != null){
							//当前用户为录入修改节点或者录入节点
							Map<String,Object> params_ = new HashMap<String, Object>();
							params_.put("current_user", user.getUser_id());
							params_.put("task_id", taskId);
							//从流水表upp_approve_info中查询，是否有记录
							int row = workFlowService.checkUpd(params_);
							//若row > 0 说明审批明细表里有其他人的审批信息   也就说明当前数据为退回后提交数据(录入修改用户)
							if(row > 0){
								//继续提交
								if(WorkFlowConstant.WORK_FLOW_AGREE.equals(approveValue)){
									submitApproveInfo(taskId, cnode, nnodes.get(0), WorkFlowConstant.WORK_FLOW_APPLY_WAIT, "提交", approveMsg,businessNo);
								}
								else{ 
									//执行删除记录
									params.put("task_id", taskId);
									params.put("business_no", businessNo);
									workFlowService.deleteApprove(params);
								}
							}else{
								//撤回提交的数据
								submitApproveInfo(taskId, cnode, nnodes.get(0), WorkFlowConstant.WORK_FLOW_APPLY_WAIT, "撤回提交的数据", approveMsg,businessNo);
							}
						}else{
							//如果发现没有task_id，新增审批信息
							//新增流程信息
							params.put("task_id", SequenceUtil.next("t"));
							params.put("flow_id", workflowId);
							params.put("current_node_id", cnode.getId());           //当前审批人节点id
							params.put("next_node_id", nnodes.get(0).getId());      //下级审批人节点id
							params.put("create_user", userId);
							params.put("task_status", WorkFlowConstant.WORK_FLOW_APPLY_WAIT); 

							params.put("business_no", businessNo);
							
							params.put("approve_user", userId);
							params.put("approve_role", cnode.getName());
							params.put("approve_info", "提交");
							params.put("approve_desc", approveMsg);
							params.put("templateId", templateId);
							//新增流水和审批信息
							workFlowService.startApprove(params);
						}
					}
				}else{
					//非首节点
					if (taskId == null || "".equals(taskId)) {
						continue;
					}
					
					//非首节点时，如果执行的是提交操作(针对退回状态)
					if(buttonType.equals("00") && approveValue.equals("")){
						approveValue = WorkFlowConstant.WORK_FLOW_AGREE;
					}
					
					//获取同意和退回2个分支对应的下级节点
					FlowNode aggreNode = WorkFlowUtil.getNextTaskByStatus(flowMap, cnode, WorkFlowConstant.WORK_FLOW_AGREE);
					FlowNode disaggreNode = WorkFlowUtil.getNextTaskByStatus(flowMap, cnode, WorkFlowConstant.WORK_FLOW_REBACK);
					
					//获取最后的审批（task）节点,对应结束的上级节点
					List<String> lastNodeId = new ArrayList<String>();
					List<FlowNode> lastNode = WorkFlowUtil.getLastTaskNode(flowMap);
					for (int j = 0; j < lastNode.size(); j++) {
						lastNodeId.add(lastNode.get(j).getId());
					}
					
					//当前用户为最后用户
					if (lastNodeId.contains(cnode.getId())) {
						//判断下级节点与起始节点策略相同，策略值相同，并且不是审批节点，则节点为重录
						if (cnode.getVmodel().equals(Fnode.getVmodel()) && cnode.getVvalue().equals(Fnode.getVvalue()) && !cnode.isVcheck()) {
							//当前用户为录入修改用户
							if(WorkFlowConstant.WORK_FLOW_AGREE.equals(approveValue)){
								submitApproveInfo(taskId, cnode, aggreNode, WorkFlowConstant.WORK_FLOW_APPLY_WAIT, "提交", approveMsg,businessNo);
							}else{
								//退回到申请人
								params.put("task_id", taskId);
								params.put("business_no", businessNo);
								workFlowService.updateApprove(params);
							}
						}else{
							//正常审批用户 结束流程
							if(WorkFlowConstant.WORK_FLOW_AGREE.equals(approveValue)){
								submitApproveInfo(taskId, cnode, aggreNode, WorkFlowConstant.WORK_FLOW_APPLY_DONE, "通过(流程结束)", approveMsg,businessNo);
							}else{
								submitApproveInfo(taskId, cnode, disaggreNode, WorkFlowConstant.WORK_FLOW_APPLY_BACK, "退回", approveMsg,businessNo);
							}
						}
					}else{
						if(approveValue != null && !"".equals(approveValue)){
							if(WorkFlowConstant.WORK_FLOW_AGREE.equals(approveValue)){
								submitApproveInfo(taskId, cnode, aggreNode, WorkFlowConstant.WORK_FLOW_APPLY_WAIT, "通过", approveMsg,businessNo);
							}else{
								submitApproveInfo(taskId, cnode, disaggreNode, WorkFlowConstant.WORK_FLOW_APPLY_BACK, "退回", approveMsg,businessNo);
							}
						}
					}
				}
			}
			return doSuccessInfoResponse("审批任务提交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("审批任务提交失败!");
		}
	}
	
	/**
	 * 检测当前任务是否可撤回
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="doCheckReback")
	@ResponseBody
	public Object doCheckReback() throws Exception{
		String task_ids = request.getParameter("task_ids");
		String business_nos= request.getParameter("business_nos");
		//String workflowId = request.getParameter("workflowId");//流程ID
		//Map<String,List<BaseFlow>> flowMap = getFlowNodesList(workflowId);
		FlowNode currNode = null;
		List<FlowNode> currNodes = (List<FlowNode>)session.getAttribute("CurrentNode");
		
		for (int i = 0; i < task_ids.split("_").length; i++) {
			 String task_id = task_ids.split("_")[i];
			 Map<String,Object> params = new HashMap<String, Object>();
			 params.put("task_id", task_id);
			 params.put("business_no", task_id);
			 List<Map<String,Object>> list = workFlowService.getApproveSeq(params);
			 if (list != null && list.size() > 0 && list.get(0)!= null && list.get(0).size() > 0) {
				 String currNodeId = (String)list.get(0).get("current_node_id");
				 
				 boolean b = false;
				 for (int j = 0; j < currNodes.size(); j++) {
						FlowNode node = currNodes.get(j);
						if (node.getId().equals(currNodeId)) {
							b = true;
							currNode = node;
						}
					}
				 if (!b) {
					//不可撤回
					return doFailureInfoResponse("选中的数据已处于审批中，不能撤回!");
				}
			}
		}
		
		//开始执行撤回操作
		//更改流水表中数据状态为  00
		//List<FlowNode> nnodes = WorkFlowUtil.getNextNode(flowMap, currNode);
		System.out.println(currNode.getId()+","+currNode.getName());
		for (int i = 0; i < task_ids.split("_").length; i++) {
			 String task_id = task_ids.split("_")[i];
			 String  business_no = business_nos.split("_")[i];
			 submitApproveInfo(task_id, currNode, currNode, WorkFlowConstant.WORK_FLOW_APPLY_EDIT, "撤回", "录入撤回",business_no);
		}
		return doSuccessInfoResponse("撤回成功");
	}
	
	
	/**
	 * 操作审批信息表
	 * @param taskId
	 * @param cnode
	 * @param nnode
	 * @param applyStatus
	 * @param approveInfo
	 * @param approveMsg
	 * @throws Exception
	 */

	private void submitApproveInfo(String taskId,FlowNode cnode,FlowNode nnode,String applyStatus,String approveInfo,String approveMsg,String businessNo) throws Exception{
		Map<String,Object> params = new HashMap<String, Object>();
		SysUserInfo user = getCurrentUser();
		String userId = user.getUser_id();  //当前用户ID
		params.put("current_node_id", cnode.getId());
		params.put("next_node_id", nnode.getId());
		params.put("update_user", userId);
		params.put("task_status", applyStatus); //WorkFlowConstant。WORK_FLOW_APPLY_EDIT 流程状态
		params.put("task_id", taskId);
		
		params.put("business_no", businessNo);
		
		params.put("approve_user", userId);
		params.put("approve_role", cnode.getName());
		params.put("approve_info", approveInfo);
		params.put("approve_desc", approveMsg);
		params.put("templateId", request.getParameter("templateId"));
		workFlowService.doApprove(params);
	}
	
	/**
	 * 获取流程对象
	 * @param workflowId  流程编号
	 * @return 
	 * @throws Exception 
	 */
	private Map<String,List<BaseFlow>> getFlowNodesList(String workflowId) throws Exception{
		
		Map<String,Object> Qparams = new HashMap<String, Object>();
		Qparams.put("flow_tmpl_id", workflowId);
		List<Map<String,Object>> result = workFlowService.getWorkFlowById(Qparams);
		if (result == null || result.size() <= 0) {
			throw new Exception("未获取到编号为"+workflowId+"的流程信息");
		}
		Map<String,Object> res = result.get(0);
		String flow_json = String.valueOf(res.get("json_code"));
	    Map<String,List<BaseFlow>> flowMap = WorkFlowUtil.WorkFlowHandler(flow_json);
		return flowMap;
	}
	
	
	/**
	 * 根据审批明细表判断当前数据是否为录入/修改
	 * @param workflowId  流程编号
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="checkDataUpd/{task_id}")
	public void checkDataUpd(@PathVariable String task_id,HttpServletResponse response) throws Exception{
		SysUserInfo user = getCurrentUser();
		Map<String,Object> Qparams = new HashMap<String, Object>();
		Qparams.put("current_user", user.getUser_id());
		Qparams.put("task_id", task_id);
		int row = workFlowService.checkUpd(Qparams);
		PrintWriter out = response.getWriter();
		out.print(row);
		out.flush();
		out.close();
	}
	
}
