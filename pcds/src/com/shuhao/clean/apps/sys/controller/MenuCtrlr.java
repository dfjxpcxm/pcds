package com.shuhao.clean.apps.sys.controller;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.LoginConstant;

/**
 * <p>Title: 页面跳转控制类  pathUlr+/menus/X99</p>
 * <p>Description: Controller类</p>
 * 跳转规则说明:<br>
 * 后缀为:.jsp跳转到jsp页面
 * 后缀为:.do 跳转到controller
 * 没有后缀:跳转到  url.jsp
 * <p>History:</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-8-20
 * @author gongzy
 * @version 1.0
 */
@Controller
public class MenuCtrlr extends BaseCtrlr {
	
	private Log logger = LogFactory.getLog(this.getClass());  

	/**
	 * 菜单转向类，用户菜单权限控制
	 * @param menuId
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/menus/{menuId}",method = RequestMethod.GET)
	public ModelAndView menu(@PathVariable String menuId, HttpServletRequest request,ModelAndView model) throws Exception {
		try {
			logger.debug("跳转菜单编号:"+menuId);
			//获取登陆用户菜单
			List<SysResourceInfo> userResourceList = (List<SysResourceInfo>)request.getSession().getAttribute(LoginConstant.USER_RESOURCE);
			for (SysResourceInfo sysResourceInfo : userResourceList) {
				if(sysResourceInfo.getResource_id().equals(menuId)){
					logger.debug("跳转菜单编号:"+sysResourceInfo.getHandler());
					//跳转到jsp中
					int j = sysResourceInfo.getHandler().indexOf(".jsp");
					//跳转到controller中
					int i = sysResourceInfo.getHandler().indexOf(".do");
					//优先判断跳转到jsp中
					if(j >-1 ){
						model.setViewName(sysResourceInfo.getHandler().substring(0,j));
					}else if(i >-1 ){
						model.setViewName("forward:/"+sysResourceInfo.getHandler().substring(0,i));
					}else{
						model.setViewName(sysResourceInfo.getHandler());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		return model;
	}
	
	/**
	 * forward方式跳转到资源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/forward/{path}")
	public String forwardto(@PathVariable String path,HttpServletRequest request) throws Exception {
		logger.debug("[forward]跳转路径 :"+path);
		return "forward:/"+path;
	}
	
	/**
	 * redirect方式跳转到资源
	 * @param path
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/redirect/{path}")
	public String redirectto(@PathVariable String path, HttpServletRequest request){
		logger.debug("[redirect]跳转路径 :"+path);
		return "redirect:/{path}";
	}
	
	public static void main(String[] args) {
		System.out.println("aaaaa.d2o".indexOf(".do"));
	}
}
