package com.shuhao.clean.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageRange;
import com.shuhao.clean.utils.PageResult;

/**
 * 抽象Service
 * 包含一些Service通用工具方法,包含jdbcTemplate
 * @author gongzy
 *
 */
public abstract class BaseJdbcService extends BaseService{
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected TransactionTemplate transactionTemplate ;
	

	/**
	 * 得到某序列的nextval
	 * @throws Exception 
	 * */
	protected String getSeqNextval(String seq) throws Exception{
		String sql = "select "+seq+".nextval seq from dual";
		List<Map<String,Object>> seqList = this.toLowerMapList(this.jdbcTemplate.queryForList(sql));
		if(null == seqList || seqList.size() != 1){
			throw new Exception("获取序列值错误");
		}
		return String.valueOf(seqList.get(0).get("seq"));
	}
	
	/**
	 * 批量执行带事务
	 * @param sqlArray
	 * @throws Exception
	 */
	protected void transactionUpdate(final String[] sqlArray) throws Exception {
		if(sqlArray.length>0){
			Object exeRs = transactionTemplate.execute(new TransactionCallback<String>() {
				public String doInTransaction(TransactionStatus status) {
					try {
						jdbcTemplate.batchUpdate(sqlArray);
					} catch (Exception e) {
						e.printStackTrace();
						status.setRollbackOnly();
						return e.getMessage();
					}
					return "success";
				}
			});
			if (!"success".equals(exeRs)) {
				Exception e = (Exception) exeRs;
				throw e;
			}
		}else{
			logger.info("批量执行sql列表为空.");
		}
	}
	
	protected List<Map<String, Object>> getPageResult(String sql,PageRange range){
		//StringBuffer sqlBuffer = new StringBuffer("WITH T1 AS (");
		StringBuffer sqlBuffer = new StringBuffer("select e.* from (");
		sqlBuffer.append(sql);
		/*sqlBuffer.append("	),T2 AS (SELECT ROWNUM AS FRAMEWORKROWNUM, T1.* FROM T1)");
		sqlBuffer.append("SELECT * FROM T2 WHERE FRAMEWORKROWNUM BETWEEN ("+range.getStart()+"+1) AND ("+range.getStart()+"+"+range.getLimit()+")");*/
		sqlBuffer.append(" ) ");
		sqlBuffer.append(" e limit #start,#limit "); 
		return GlobalUtil.lowercaseListMapKey(jdbcTemplate.queryForList(sqlBuffer.toString()));
	}
	
	protected int getTotalCount(String sql){
		StringBuffer sqlBuffer = new StringBuffer("select count(1) as r_count from (");
		sqlBuffer.append(sql).append(")");
		Map<String, Object> map = jdbcTemplate.queryForMap( sqlBuffer.toString());
		if(map!=null && !map.isEmpty()){
			return Integer.parseInt(GlobalUtil.getIgnoreCaseValue(map, "r_count"));
		}
		return 0; 
	}
}
