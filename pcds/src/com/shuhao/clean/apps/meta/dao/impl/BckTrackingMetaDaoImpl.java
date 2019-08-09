package com.shuhao.clean.apps.meta.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shuhao.clean.apps.meta.dao.BckTrackingMetaDao;
import com.shuhao.clean.base.BaseDao;
import com.shuhao.clean.utils.GlobalUtil;

@Component("bakTrackMetaDao")
@Transactional
public class BckTrackingMetaDaoImpl extends BaseDao implements
		BckTrackingMetaDao {
	Logger loggger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	//获取纬表关联的数据
	public List<Map<String, Object>> getDimLinkDataByComponentId(Map<String,Object> params)throws Exception {
		String sql = this.getExecuteSql(params);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	//3.根据元素  　load 属性　查询 模板元素表数据
	public List<Map<String,Object>> getMetaData(Map<String,Object> params) throws Exception{
			String sql = this.getExecuteSql(params);
			return this.jdbcTemplate.queryForList(sql);
	}
	
	//获取总记录数
	public int getMetaDataCounts(Map<String,Object> params) throws Exception{
		String sql = this.getExecuteSql(params);
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*)  from (");
		buffer.append(sql);
		buffer.append(") total");
		return this.jdbcTemplate.queryForInt(buffer.toString());
	}
	
	//4.操作模板数据
	
	public void executeMetaData(Map<String,Object> params)throws Exception{
		String sql = this.getExecuteSql(params);
		this.jdbcTemplate.execute(sql);
	}
	
	
	public List<Map<String,Object>> getMetaDataById(Map<String,Object> params) throws Exception{
		String sql = this.getExecuteSql(params);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @throws Exception
	 */
	public void batchExecSql(List<Map<String,Object>> sqlList )throws Exception{
		String[] sqls = new String[sqlList.size()];
		for (int i = 0; i < sqlList.size(); i++) {
			Map<String,Object> params = sqlList.get(i);
			String sql = getExecuteSql(params);
			sqls[i] = sql;
			loggger.debug(sql);
		}
		batchUpdate(sqls);
	}
	
	/**
	 * 更改数据状态
	 */
	public void updateBusinessData(Map<String,Object> params) throws Exception{
		StringBuffer buffer = new StringBuffer();
		buffer.append("update  ");
		buffer.append(params.get("tabName"));
		buffer.append(" set ");
		buffer.append(" flow_status_code = '").append(params.get("flow_code")).append("'");
		buffer.append(" where business_no = '").append(params.get("business_no")).append("'");
		this.jdbcTemplate.execute(buffer.toString());
	}
	
	/**
	 * 删除数据
	 */
	public void delBusinessData(Map<String,Object> params) throws Exception{
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete  ");
		buffer.append(params.get("tabName"));
		buffer.append(" where business_no = '").append(params.get("business_no")).append("'");
		this.jdbcTemplate.execute(buffer.toString());
	}
	
	
	//生成sql语句
	private String  getExecuteSql(Map<String,Object> params) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String sql =  (String)params.get("sql");
		//判断是否是查询
		String actionType = (String)params.get("action_type");
		//判断是否为分页查询
		if(params.get("ispage") != null && !"".equals(params.get("ispage"))){
			//buffer.append(" WITH T1 AS ( ");
			buffer.append("select e.* from  ( ");
			buffer.append(sql);
			/*buffer.append(" ),T2 AS (SELECT ROWNUM AS FRAMEWORKROWNUM, T1.* FROM T1) ");
			buffer.append(" SELECT * FROM T2 WHERE FRAMEWORKROWNUM BETWEEN (#start+1) AND (#start+#limit) ");*/
			buffer.append(" ) ");
			buffer.append(" e limit #start,#limit ");
		}else{
			buffer.append(sql);
		}
		sql = buffer.toString();
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();
			
			//排除sql，分页，动作类型
			if("sql".equals(key) ||"ispage".equals(key) || "action_type".equals(key)){
				continue;
			}
			
			String value = "";
			if (obj != null){
				value = String.valueOf(obj);
				if (value.indexOf(",")>-1) {
					value = value.replace(",", "");
				} 
				
				if (key.equals("start") || key.equals("limit")) {
					sql = sql.replace("#"+key, value);
				}else{
					//如果是查询
					if(actionType!=null && actionType.equals("query")){
						sql = sql.replace("#"+key, value);
					}else{
						sql = sql.replace("#"+key, "'"+value+"'");
					}
				}
			}else{
				sql = sql.replace("#"+key,"");
			}
		}
		return sql;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.dao.BckTrackingMetaDao#getTempletByParentId(java.util.Map)
	 */
	public List<Map<String, Object>> getTempletByParentId(
			Map<String, Object> param) {
		return this.jdbcTemplate.queryForList("select * from upp_template where parent_template_id = '"+GlobalUtil.getStringValue(param, "tmpl_id")+"'");
	}
}
