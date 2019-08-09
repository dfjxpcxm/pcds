/**
 * FileName:     BaseJdbcRuleLoader.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 上午9:16:44 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-28       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.support;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.shuhao.clean.apps.validate.entity.UppCheckRule;

/**
 * @Description:   规则缓存管理器,这是一个单例类
 * 
 * @author:         gongzhiyang
 */
public class ValidRulesManager implements BeanFactoryAware{
	
	private static final Logger logger = Logger.getLogger(ValidRulesManager.class);
	
	private BeanFactory beanFactory;
	/**
	 * 规则缓存
	 */
	private Cache ruleCache;
	/**
	 * 规则缓存管理器
	 */
	public static ValidRulesManager validRulesCache = null;
	
	//单例
	private ValidRulesManager(){
	}
	
	private ValidRulesManager(Cache ruleCache){
		if(ruleCache == null){
			this.ruleCache = beanFactory.getBean("ruleCache",Cache.class);
		}else{
			this.ruleCache = ruleCache;
		}
		logger.info("创建规则缓存管理器成功.");
	}
	
	/**
	 * 获取管理器示例
	 * @param ruleCache
	 * @return
	 */
	public static ValidRulesManager getInstance(Cache ruleCache){
		if(validRulesCache == null){
			validRulesCache = new ValidRulesManager(ruleCache);
		}
		return validRulesCache;
	}
	
	/**
	 * 获取ValidRulesManager 实例对象
	 * @return
	 */
	public static ValidRulesManager getInstance(){
		return getInstance(null);
	}
	
	/**
	 * 初始化通用规则
	 * @throws Exception
	 */
	public static void loadCheckRules(List<UppCheckRule> checkRules)throws Exception{
		for (UppCheckRule uppCheckRule : checkRules) {
			validRulesCache.addToCache(uppCheckRule.getChk_rule_code(), uppCheckRule);
		}
	}
	
	/**
	 * 重新加载缓存
	 * @throws Exception
	 */
	public static void reloadCheckRuleCache(List<UppCheckRule> checkRules)throws Exception {
		validRulesCache.clearCache();
		loadCheckRules(checkRules);
	}
	
	/**
	 * 增加到缓存
	 * @param key
	 * @param rule
	 */
	public static synchronized void addCheckRuleToCache(UppCheckRule rule){
		if(validRulesCache.getCacheObject(rule.getChk_method_code())==null){
			validRulesCache.addToCache(rule.getChk_method_code(), rule);
		}
	}
	
	/**
	 *更新某规则
	 * @param rule
	 */
	public static synchronized void updateCheckRuleToCache(UppCheckRule rule){
		if(validRulesCache.getCacheObject(rule.getChk_method_code())!=null){
			deleteCheckRule(rule.getChk_method_code());
		}
		validRulesCache.addToCache(rule.getChk_method_code(), rule);
	}
	
	/**
	 * 删除某规则
	 * @param key
	 */
	public static synchronized void deleteCheckRule(String key) {
		validRulesCache.deleteCache(key);
	}
	
	/**
	 * 删除某规则
	 * @param key
	 */
	public static UppCheckRule getCheckRule(String key) {
		return validRulesCache.getCacheObject(key);
	}
	
	/**
	 * 获取缓存，单独操作
	 * @return
	 */
	public static Cache getRulesCache(){
		return validRulesCache.ruleCache;
	}
	
	/**
	 * 返回缓存大小
	 * @return
	 */
	public static int getSize(){
		return validRulesCache.ruleCache.getSize();
	}
	
	/***********************cache操作私有方法***************************************************/
	/**
	 * 手动清除缓存
	 * @param key 缓存的key
	 */
	private void deleteCache(String key) {
		logger.info("清除缓存对象---> key = " +key);
		this.ruleCache.remove(key);
	}

	/**
	 * 将对象添加到缓存容器
	 * @param key
	 * @param obj
	 */
	private void addToCache(String key, Object obj) {
		logger.info("缓存对象---> key = " +key);
		Element element = new Element(key, obj);
		this.ruleCache.put(element);
	}
	/**
	 * 清空缓存
	 */
	private void clearCache() {
		logger.info("清空缓存对象");
		this.ruleCache.removeAll();
	}
	
	/**
	 * 从缓存中获取对象
	 * @param key
	 * @return
	 */
	private UppCheckRule getCacheObject(String key) {
		UppCheckRule value = null;
		if(this.ruleCache.get(key) != null) {
			value = (UppCheckRule) this.ruleCache.get(key).getObjectValue();
		}
		return value;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
