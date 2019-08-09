package com.shuhao.clean.apps.flow.interceptor;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.flow.service.IWorkFlowService;
import com.shuhao.clean.utils.workflow.SequenceUtil;
/**
 * 拦截业务数据操作类，用户业务数据和审批信息同步
 * <br>
 * /pageManager/executeMetaData
 * @author yangmou
 *
 */
public class WorkFlowInterceptor extends AbsHandlerInterceptor{
	
	@Autowired
	private IWorkFlowService workFlowService;
	
	private final static Logger logger = Logger.getLogger(WorkFlowInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		return true;
	}  
	
	/**
	 * 子模版不执行
	 * 每次修改和删除都初始化审批信息
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView model) throws Exception {
		if(request.getAttribute("hasException")!=null){
			logger.info("--------程序异常，跳过流程相关更新--------");
		}else{
			String is_child = request.getParameter("is_child");
			if(is_child!=null && is_child.equals("N")){
				String execType = request.getParameter("execType");
				String templateId = request.getParameter("tmpl_id");
				//是否有审批流程
				Object workFlowId = request.getSession().getAttribute("workflowId_"+templateId);
				if(workFlowId == null){ //如果没有审批流程
					//清空流程相关
					if("del".equals(execType) ){
						String businessNos = request.getParameter("key");
						if (businessNos != null && !"".equals(businessNos)) {
							String [] busNoAry = businessNos.split("_");
							for (int i = 0; i < busNoAry.length; i++) {
								String busNo = busNoAry[i];
								Map<String,Object> param = new HashMap<String, Object>();
								param.put("business_no", busNo);
								workFlowService.deleteApproveByNo(param);
							}
						}
					}else if("upd".equals(execType)){
						Map<String, Object> param = new HashMap<String, Object>();
						Map<String,Object> map = getRequestParam(request);
						for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
							String key =  iterator.next();
							Object o = map.get(key);
							if (!key.equals("sql") && !key.contains("_")) {
								param.put(key, o);
							}else {
								if (key.split("_").length > 1) {
									int n = key.split("_")[0].length();
									param.put(key.substring(n+1, key.length()), o);
								}else{
									param.put(key, o);
								}
							}
						}
						//清楚状态
						workFlowService.deleteApproveByNo(param);
					}
				}else{
					//有审批流程
					if ("del".equals(execType)) {
						/*String task_ids = request.getParameter("task_id");
						if (task_ids != null && !"".equals(task_ids)) {
							String [] taskIds = task_ids.split("_");
							for (int i = 0; i < taskIds.length; i++) {
								String task_id = taskIds[i];
								if(!task_id.equals("")){
									Map<String,Object> param = new HashMap<String, Object>();
									param.put("task_id", task_id);
									param.put("templateId", templateId);
									//按任务编号删除flow信息
									workFlowService.deleteApprove(param);
								}
							}
						}*/
						//去掉flow_status_code后，只关注flow表的删除
						String businessNos = request.getParameter("key");
						if (businessNos != null && !"".equals(businessNos)) {
							String [] busNoAry = businessNos.split("_");
							for (int i = 0; i < busNoAry.length; i++) {
								String busNo = busNoAry[i];
								Map<String,Object> param = new HashMap<String, Object>();
								param.put("business_no", busNo);
								workFlowService.deleteApproveByNo(param);
							}
						}
					}else if("upd".equals(execType)){
						boolean valid_success = request.getAttribute("valid_success") == null ;
						//校验通过才执行
						if(valid_success){
							Map<String, Object> param = new HashMap<String, Object>();
							Map<String,Object> map = getRequestParam(request);
							for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
								String key =  iterator.next();
								Object o = map.get(key);
								if (!key.equals("sql") && !key.contains("_")) {
									param.put(key, o);
								}else {
									if (key.split("_").length > 1) {
										int n = key.split("_")[0].length();
										param.put(key.substring(n+1, key.length()), o);
									}else{
										param.put(key, o);
									}
								}
							}
							param.put("templateId", templateId);
							
		//					workFlowService.deleteApproveByNo(param);
							//更新为初始状态状态
							workFlowService.updateApprove(param);
						}else{
							logger.info("校验不通过，跳过更新");
						}
					}else if(execType.equals("add")){
						boolean valid_success = request.getAttribute("valid_success") == null ;
						//校验通过才执行
						if(valid_success){
							//交易通过，添加流程信息
							String userId = String.valueOf(request.getAttribute("new_user_id"));
							String businessNo = String.valueOf(request.getAttribute("new_business_no"));
							String flowId = String.valueOf(request.getSession().getAttribute("workflowId_"+templateId)); 
			
							Map<String, Object>  params = new HashMap<String, Object>();
							params.put("task_id", SequenceUtil.next("t"));
							params.put("flow_id", flowId);
							params.put("current_node_id", "-1");   //当前审批人节点id
							params.put("next_node_id", "-1");  //下级审批人节点id
							params.put("task_status", "-1");  //状态节点
							params.put("create_user", userId);
							params.put("business_no", businessNo);
							
							workFlowService.addApproveSeq(params);
						}
					}
				}
			}
			logger.debug("--------结束审批信息处理--------");
		}
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object obj, Exception e) throws Exception {
	}
}  