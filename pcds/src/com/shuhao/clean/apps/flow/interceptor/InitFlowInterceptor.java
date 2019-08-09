package com.shuhao.clean.apps.flow.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.flow.entity.BaseFlow;
import com.shuhao.clean.apps.flow.entity.FlowNode;
import com.shuhao.clean.apps.flow.service.IWorkFlowService;
import com.shuhao.clean.apps.meta.service.IPageManagerService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.constant.WorkFlowConstant;
import com.shuhao.clean.utils.workflow.WorkFlowUtil;

/**
 * 用户初始化流程所需数据
 * 
 * @author yangmou
 * 
 */
public class InitFlowInterceptor implements HandlerInterceptor {

	@Autowired
	private IWorkFlowService workFlowService;

	@Autowired
	private IPageManagerService pageManagerService;

	private final static Logger logger = Logger.getLogger(InitFlowInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		try {
			Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			String templateId = "";
			for (Iterator<?> iterator = pathVariables.keySet().iterator(); iterator.hasNext();) {
				String key = String.valueOf(iterator.next());
				templateId = String.valueOf(pathVariables.get(key));
				break;
			}

			logger.debug("--------开始缓存当前登陆用户权限节点信息--------");
			Map<String, Object> params_ = new HashMap<String, Object>();
			params_.put("templateId", templateId);
			List<Map<String, Object>> workflow = pageManagerService.getWorkFlowById(params_);
			if (workflow != null && workflow.size() > 0 ) {
				String workflowId = String.valueOf(workflow.get(0).get("flow_tmpl_id")); //流程ID
				//缓存模版对应的流程ID
				request.getSession().setAttribute("workflowId_"+templateId, workflowId); 
				SysUserInfo currUser = (SysUserInfo) request.getSession().getAttribute(LoginConstant.CURRENT_USER);
				
				Map<String, Object> Qparams = new HashMap<String, Object>();
				Qparams.put("flow_tmpl_id", workflowId);
				List<Map<String, Object>> result = workFlowService.getWorkFlowById(Qparams); //流程信息
				if (result != null && result.size() > 0 && result.get(0) != null && result.get(0).size() > 0) {
					Map<String, Object> res = result.get(0);
					String flow_json = String.valueOf(res.get("json_code"));
					Map<String, List<BaseFlow>> flowMap = WorkFlowUtil.WorkFlowHandler(flow_json);
					String bank_org_id = currUser.getBank_org_id();//机构
					String user_id = currUser.getUser_id(); //用户
					String post_id = currUser.getPost_type_code(); //岗位

					//--------缓存的对象列表---------
					List<FlowNode> currNode = new ArrayList<FlowNode>();
					List<String> currNodeIds = new ArrayList<String>();
					StringBuffer nodeBuffers = new StringBuffer();
					
					// 获取所有节点
					List<BaseFlow> listNode = flowMap.get(WorkFlowConstant.WORK_FLOW_NODES);
					for (int i = 0; i < listNode.size(); i++) {
						FlowNode node = (FlowNode) listNode.get(i);
						//System.out.println(node.getId()+".."+node.getName()+".."+node.getType()+".."+node.getVvalue());
						if (WorkFlowConstant.WORK_FLOW_TASK.equals(node.getType())) {
							
							//将所有审批节点放入nodeBuffers
							if(node.isVcheck()){
								nodeBuffers.append(node.getId()).append("[").append(node.getName()).append("]");
							}
							
							String vModel = node.getVmodel();
							String vValue = node.getVvalue();

							if (WorkFlowConstant.WORK_FLOW_ORG.equals(vModel)) { // 机构策略
								//机构选择值为 [8888]总行  开始拆分
								vValue = formatVvalue(vValue);
								 
								if (bank_org_id.equals(vValue)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							} else if (WorkFlowConstant.WORK_FLOW_ROLE.equals(vModel)) { // 角色策略
								// 开始获取当前用户的角色信息
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("user_id", user_id);
								List<String> role_ids = workFlowService.getCurrentUserRole(param);
								if (role_ids.contains(vValue)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							} else if (WorkFlowConstant.WORK_FLOW_DEPT.equals(vModel)) { // 部门策略
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("type", "dept");
								params.put("user_id", user_id);
								List<String> deptIds = workFlowService.getPermissionUserId(params);
								
								vValue = formatVvalue(vValue);

								if (deptIds.contains(vValue)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							} else if (WorkFlowConstant.WORK_FLOW_POST.equals(vModel)) { // 岗位策略
								if (post_id.equals(vValue)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							} else if (WorkFlowConstant.WORK_FLOW_TEAM.equals(vModel)) { // 团队策略
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("type", "team");
								params.put("user_id", user_id);
								List<String> teamIds = workFlowService.getPermissionUserId(params);
								if (teamIds.contains(vValue)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							} else if (WorkFlowConstant.WORK_FLOW_EPM.equals(vModel)) { // 人员策略
								String[] epms = vValue.split(",");
								List<String> empList = new ArrayList<String>();
								for (int j = 0; j < epms.length; j++) {
									String users = epms[j];
									String userId = formatVvalue(users);
									empList.add(userId);
								}
								if (empList.contains(user_id)) {
									currNode.add(node);
									currNodeIds.add(node.getId());
								}
							}
						}
					}
					
					//CurrentRole = 01 无审批权限,00 显示撤回
					String hasApply = "N"; //是否有提交权限
					String hasApprove = "N";//是否有审批权限
					if (currNode.size() <= 0) {
						request.setAttribute("CurrentRole", "01");   //撤回显示标识
						logger.debug("登陆用户无审批权限，请检查流程节点权限配置");
					}else{
						// 根据登陆用户获取当前用户所属节点类型 若为录入人员，显示撤回
						//1。判断是否有提交权限
						//2。判断是否有审批权限
						FlowNode firstNode = WorkFlowUtil.getFristTaskNode(flowMap); // 流程中第一个审批节点
						 //request.setAttribute("CurrentRole", "00");
						for (FlowNode fNode : currNode){
							//首节点默认是提交节点
							if(fNode.getId().equals(firstNode.getId())){
								hasApply = "Y";
							}else if(fNode.isVcheck()){
								hasApprove = "Y";
							}
						}
						
						//有申请权限
						if (hasApply.equals("Y")) {
							request.setAttribute("CurrentRole", "00");   //撤回显示标识
						}
						
						//request.setAttribute("is_approve", "N");   //控制界面工具条,申请还是审批
						
						request.getSession().setAttribute("CurrentNode", currNode); //当前用户拥有的审批节点
						request.getSession().setAttribute("CurrentNodeId",currNodeIds);//当前用户用友审批节点ID
					}
					request.setAttribute("hasApply", hasApply);
					request.setAttribute("hasApprove", hasApprove);
					request.setAttribute("approveNodeStr", nodeBuffers.toString());
				} else {
					//clear session状态信息
					request.getSession().removeAttribute("workflowId_"+templateId); 
					request.getSession().removeAttribute("CurrentNode"); 
					request.getSession().removeAttribute("CurrentNodeId"); 
					
					logger.debug("--------未获取到编号为" + workflowId + "的流程信息--------");
					return false;
				}
			} else {
				//clear session状态信息
				request.getSession().removeAttribute("workflowId_"+templateId); 
				request.getSession().removeAttribute("CurrentNode"); 
				request.getSession().removeAttribute("CurrentNodeId"); 
				
				logger.debug("--------当前模板未配置流程信息--------");
			}
		} catch (Exception e) {
			logger.debug("--------初始化审批信息异常--------");
			logger.debug(e.getMessage());
			e.printStackTrace();
			return false;
		}
		logger.debug("--------缓存当前登陆用户权限节点信息结束--------");
		return true;
	}
	
	private String formatVvalue(String vValue){
		if (vValue.contains("[") && vValue.contains("]")) {
			vValue = vValue.split("]")[0];
			vValue = vValue.split("\\[")[1];
		}
		return vValue;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e)
			throws Exception {
		logger.debug(e == null ? "执行成功" : e.getMessage());

	}
}
