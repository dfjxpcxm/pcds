package com.shuhao.clean.apps.validate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.validate.entity.MetadataCheckRule;
import com.shuhao.clean.apps.validate.entity.UppCheckRule;
import com.shuhao.clean.apps.validate.service.ICheckRuleService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.PageResult;
import com.shuhao.clean.utils.xml.ValidateXmlUtil;

/**
 * 校验规则库维护
 * @author dongln
 * 
 */
@Controller
@RequestMapping("/checkRule")
public class CheckRuleCtrlr extends BaseCtrlr {
	
	@Autowired
	private ICheckRuleService checkRuleService;
	
	/**
	 * 添加校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="addCheckRule")
	@ResponseBody
	public Object addCheckRule() throws Exception {
		UppCheckRule checkRule = this.getParamObject(UppCheckRule.class);
		checkRule.setCreate_user_id(this.getCurrentUser().getUserID());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String chk_rule_code = this.checkRuleService.addCheckRule(checkRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
			result.put("chk_rule_code", chk_rule_code);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 配置元数据校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="addMetaRule")
	@ResponseBody
	public Object addMetaRule() throws Exception {
		MetadataCheckRule metadataCheckRule = this.getParamObject(MetadataCheckRule.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.checkRuleService.addMetaRule(metadataCheckRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 配置元数据校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="editMetaRule")
	@ResponseBody
	public Object editMetaRule() throws Exception {
		MetadataCheckRule metadataCheckRule = this.getParamObject(MetadataCheckRule.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.checkRuleService.updateMetaRule(metadataCheckRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteChkRule")
	@ResponseBody
	public Object deleteChkRule() throws Exception {
		UppCheckRule checkRule = this.getParamObject(UppCheckRule.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.checkRuleService.deleteCheckRule(checkRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 删除配置校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteMetaRule")
	@ResponseBody
	public Object deleteMetaRule() throws Exception {
		MetadataCheckRule metadataCheckRule = this.getParamObject(MetadataCheckRule.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.checkRuleService.deleteMetaRule(metadataCheckRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 修改校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="editCheckRule")
	@ResponseBody
	public Object editCheckRule() throws Exception {
		UppCheckRule checkRule = this.getParamObject(UppCheckRule.class);
		checkRule.setUpdate_user_id(this.getCurrentUser().getUserID());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.checkRuleService.updateCheckRule(checkRule);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询未配置校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listCheckRule")
	@ResponseBody
	public Object listCheckRule() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(map);
		try {
			PageResult<UppCheckRule> pr = this.checkRuleService.listCheckRule(map);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功:"+e.getMessage());
		}
		return result;
	}
	/**
	 * 查询校验规则库
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listCheckRuleLib")
	@ResponseBody
	public Object listCheckRuleLib() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(map);
		try {
			PageResult<UppCheckRule> pr = this.checkRuleService.listCheckRuleLib(map);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询元数据配置的校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listMetadataCheckRule")
	@ResponseBody
	public Object listMetadataCheckRule() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(map);
		try {
			PageResult<UppCheckRule> pr = this.checkRuleService.listMetadataCheckRule(map);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "查询失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询校验类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listCheckType")
	@ResponseBody
	public Object listCheckType() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PageResult<Map<String,Object>> pr = this.checkRuleService.listCheckType(map);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	/**
	 * 查询校验方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listCheckMethod")
	@ResponseBody
	public Object listCheckMethod() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list  = this.checkRuleService.listCheckMethod(map);
			result.put("results", list);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	/**
	 * 查询元数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getMetadata")
	@ResponseBody
	public Object getMetadata() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list  = this.checkRuleService.getMetadata(map);
			if(list.size()>0){
				result.put("result", list.get(0));
				result.put("success", Boolean.valueOf(true));
			}else{
				result.put("failure", Boolean.valueOf(true));
				result.put("info", "请选元数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
	/**
	 * 查询表字段
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listColumns")
	@ResponseBody
	public Object listColumns() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list  = this.checkRuleService.listColumns(map);
			result.put("results", list);
			result.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
	/**
	 * 查询目标表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listTargetTab")
	@ResponseBody
	public Object listTargetTab() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list  = this.checkRuleService.listTargetTab(map);
			result.put("results", list);
			result.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
	/**
	 * 查询函数列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listFunctions")
	@ResponseBody
	public Object listFunctions() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list  = ValidateXmlUtil.getFunctions();
			result.put("results", list);
			result.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取函数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getChkFnList")
	@ResponseBody
	public Object getChkFnList() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String type = String.valueOf(map.get("nodeId"));
			List<Map<String,Object>> list  = ValidateXmlUtil.getFunctions(map);
			//生成节点
			List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id", type+"_"+i);
				node.put("text", list.get(i).get("name"));
				node.put("desc", "<li>函数："+list.get(i).get("content")+"</li> <li>描述："+list.get(i).get("desc")+"</li>");
				node.put("content", list.get(i).get("content"));
				nodes.add(node);
			}
			result.put("results", nodes);
			result.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询规则树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryChkRuleTreeNode")
	@ResponseBody
	public Object queryChkRuleTreeNode() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		String node_id = getStringValue(map, "nodeID");
		try {
			if(node_id!=null){
				if("root".endsWith(node_id)){
					PageResult<Map<String, Object>> pr = this.checkRuleService.listCheckType(map);
					List<Map<String,Object>> list = pr.getResults();
					for (int i = 0; i < list.size(); i++) {
						Map<String,Object> resMap = new HashMap<String, Object>();
						Map<String,Object> temp = list.get(i);
						resMap.put("id", getStringValue(temp, "chk_type_cd"));
						resMap.put("text", getStringValue(temp, "chk_type_desc"));
						resMap.put("leaf", false);
						resMap.put("type", "chk_type");
						resList.add(resMap);
					}
				}else if(node_id.split(";").length==1){
					List<Map<String,Object>> list = this.checkRuleService.listCheckMethod(map);
					for (int i = 0; i < list.size(); i++) {
						Map<String,Object> resMap = new HashMap<String, Object>();
						Map<String,Object> temp = list.get(i);
						resMap.put("id", node_id+";"+getStringValue(temp, "chk_method_cd"));
						resMap.put("text", getStringValue(temp, "chk_method_name"));
						resMap.put("leaf", false);
						resMap.put("type", "chk_method");
						resList.add(resMap);
					}
				}else if(node_id.split(";").length==2){
					map.put("chk_type_code",node_id.split(";")[0]);
					map.put("chk_method_code",node_id.split(";")[1]);
					PageResult<UppCheckRule> pr  = this.checkRuleService.listCheckRuleLib(map);
					List<UppCheckRule> list = pr.getResults();
					for (int i = 0; i < list.size(); i++) {
						Map<String,Object> resMap = new HashMap<String, Object>();
						UppCheckRule temp = list.get(i);
						resMap.put("id",temp.getChk_rule_code());
						resMap.put("text",temp.getChk_rule_name());
						resMap.put("leaf", true);
						resMap.put("type", "chk_rule");
						resList.add(resMap);
					}
				}
				result.put("results", resList);
				result.put("success", Boolean.valueOf(true));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", e.getMessage());
		}
		return result;
	}
}
