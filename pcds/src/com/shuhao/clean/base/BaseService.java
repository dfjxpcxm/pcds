package com.shuhao.clean.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.shuhao.clean.utils.GlobalUtil;

/**
 * 抽象Service
 * 包含一些 Service通用工具方法
 * @author gongzy
 *
 */
@Service(value="baseService")
public abstract class BaseService {
	
	/**
	 * 日志记录
	 */
	protected Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 将查询出的Map中的Key转换成小写
	 * @param paramMap
	 * @return
	 */
	protected List<Map<String, Object>> toLowerMapList(List<Map<String, Object>> dataList){
		return GlobalUtil.lowercaseListMapKey(dataList);
	}
	
	/**
	 * 转换为小写的map
	 * @param map
	 * @return
	 */
	protected Map<String, Object> toLowerMap(Map<String, Object> map){
		return GlobalUtil.lowercaseMapKey(map);
	}
	
	/**
	 * 以String类型返回Map中对应的值
	 * @param map
	 * @param key
	 * @return
	 */
	public String getStringValue(Map<String, Object> paramMap,String key){
		return GlobalUtil.getStringValue(paramMap, key);
	}
	
	/**
	 * 从List中过滤出同给定Map中的字段值一致的记录
	 * @param list
	 * @param keys
	 * @return
	 */
	protected List<Map<String, Object>> getGroupList(List<Map<String, Object>> list,String[] keys) throws Exception{
		List<Map<String, Object>> groupList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = list.size() > 0 ? list.get(0) : new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			boolean b = true;
			for (int j = 0; j < keys.length; j++) {
				if(getStringValue(m, keys[j]).equals(getStringValue(map, keys[j])))
					continue;
				b = false;
				break;
			}
			if(b) {
				groupList.add(m);
				list.remove(m);
				i--;
			}
		}
		return groupList;
	}
	
	/**
	 * 生成simpleData格式数据
	 * @param result
	 * @return
	 */
	protected String parsetoString(String result){
		String info = "[";
		String[] results = result.split(";");
		for(int i=0;i<results.length;i++){
			String[] array = results[i].split(",");
			if(array.length==1){
				info += "['"+array[0]+"','','',''],";
				continue;
			}
			String dependId = array[0];
			String dependName = array[1];
			String dependOwnerBankId = array[2];
			String dependOwnerBankName = array[3];
			
			info += "['"+dependId+"','"+dependName+"','"+dependOwnerBankId+"','"+dependOwnerBankName+"'],";
		}
		info = info.substring(0,info.lastIndexOf(','));
		return info+"]";
	}
	
}
