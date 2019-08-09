package com.shuhao.clean.apps.meta.databaseUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 缓存Store管家（如果一些类不能通过Spring注入访问缓存Store，则通过这个类来访问）
 * @author qisheng
 */
public class StoreManager implements BeanFactoryAware {
	
	private static StoreManager storeManager;
	private BeanFactory beanFactory;
	
	private StoreManager(){
	}
	
	public static StoreManager getInstance() {
		if(storeManager == null) {
			storeManager = new StoreManager();
		}
		return storeManager;
	}
	
	/**
	 * 获取数据库连接缓存
	 * @return
	 */
	public DbConnCfgStore getDbConnCfgStore() {
		return (DbConnCfgStore) beanFactory.getBean("dbConnCfgStore");
	}
	
	/**
	 * 获取SSH连接缓存
	 * @return
	 */
	public SSHConnCfgStore getSSHConnCfgStore() {
		return (SSHConnCfgStore) beanFactory.getBean("sshConnCfgStore");
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
