/**
 * FileName:     JavaValidRule.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-27 下午3:22:35 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-27       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * @Description:   校验解析器工厂 <br>
 * select count(*) r_size from dmb_save_account t.account_id = '#self_val'
 * <br>
 * 遗留问题：
 * <br>传入map的sql解析后，判断 >=0 即通过
 * <br>使用[#balance],不存在，会替换成''
 * <br>使用#balance，没有传入时会提示校验异常
 * 
 * @author         gongzhiyang
 */
public class SqlValidRule extends AbsValidRule  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6485154733397449105L;
	
	private JdbcTemplate jdbcTemplate ;
	
	public SqlValidRule(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.IValidRule#validate(com.shuhao.clean.apps.validate.entity.CheckRule, java.lang.Object[])
	 */
	public ValidResult validate(CheckRule rule, Object... arg) {
		ValidResult result = null;
		if(arg.length > 0){
			if(arg[0] instanceof String){
				result = doValid(rule, (String)arg[0]);
			}else if(arg[0] instanceof HashMap){
				result = doValid(rule, (HashMap<String, Object>)arg[0]);
			}else{
				logger.error("校验输入参数有误。");
			}
		}else{
			logger.error("校验输入参数有误，请传入至少1个参数。");
		}
		return result;
	}
	
	/**
	 * 保存校验结果
	 * @param result
	 */
	private void saveResult(ValidResult result){
		
	}
	/**
	 * 单值sql校验返回count(1) r_size
	 * @param rule
	 * @param value
	 * @return
	 */
	public ValidResult doValid(CheckRule rule,String value){
		try {
			String sql = rule.getChk_formula().replace(_SELF, value);
			List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql);
			//r_size 换个方式显示.
			boolean b = (dataList.size()>0 && getIntValue(dataList.get(0), "r_size")>0);
			return new ValidResult(b, dataList.get(0));
		} catch (DataAccessException e) {
			return new ValidResult(false, e.getMessage());
		}
	}
	
	/**
	 * 公式不转换为小写，区分大小写
	 * @param rule
	 * @param map
	 * @return
	 */
	public ValidResult doValid(CheckRule rule,HashMap<String, Object> map){
		try {
			String formula = rule.getChk_formula();
			//判断表达式是否为null
			if(GlobalUtil.isNull(formula)){
				return new ValidResult(false, "["+rule.getChk_type_code()+"]校验公式为null") ;
			}
			
			//公共参数替换
			formula = commonFormat(formula);
			
			//变量及参数替换,sql中的默认变量为null
			formula = formatAllVars(formula, map,"null");
			
			//判断sql传入的参数是否有问题
			int errorIndex = hasError(formula);
			if(errorIndex>-1){
				String val = formula.substring(errorIndex, formula.indexOf(" ",errorIndex));
				return new ValidResult(false, "以下sql片段配置(或传入)了无效的参数: "+val);
			}
			
			logger.debug("Sql:"+formula);
			
			//执行解析完成后的sql
			List<Map<String, Object>> dataList = jdbcTemplate.queryForList(formula);
			//校验.
			boolean success = (dataList.size() == 0);//==0通过
			if(success){
				return new ValidResult(success, "");
			}else{
				return new ValidResult(success, dataList.get(0));
			}
		} catch (DataAccessException e) {
			return new ValidResult(false, e.getMessage());
		}
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
