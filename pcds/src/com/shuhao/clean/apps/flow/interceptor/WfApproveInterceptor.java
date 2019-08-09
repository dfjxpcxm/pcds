package com.shuhao.clean.apps.flow.interceptor;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.validate.service.ICheckService;
import com.shuhao.clean.constant.WorkFlowConstant;
import com.shuhao.clean.utils.GlobalUtil;
/**
 * 拦截审批流程提交时执行的校验 ,主要应用于包含子模版的情况<br>
 * 执行按钮类型：buttonType = 00提交，01撤回，02跟踪，03流程详情,-1不处理类型
 * @author gongzhiyang
 *
 */
public class WfApproveInterceptor extends AbsHandlerInterceptor{
	
	@Autowired
	private ICheckService checkService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		logger.debug("--------提交时校验--------");
		Map<String, Object> params = getRequestParam(request);
		String buttonType = GlobalUtil.getStringValue(params, "buttonType");
		String approveValue = request.getParameter("approveValue"); //审批操作
		//提交，并且是同意，或无状态时，才执行校验
		if(buttonType.equals("00") && (approveValue.equals("")||approveValue.equals(WorkFlowConstant.WORK_FLOW_AGREE))){
			Map<String, Object> errors = checkService.validSubmitApprove(params, getContext(request));
			if(errors!=null){
				request.setAttribute("validErrors", errors);
			}
		}
		return true;
	}  
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView model) throws Exception {
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object obj, Exception e) throws Exception {
		logger.debug(e == null ? "------审批提交时校验完成------" :e.getMessage());
	}
	 
}  
