package com.shuhao.clean.apps.meta.databaseUtil;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 缓存Store抽象类
 * @author cognos
 * @param <T> 缓存对象
 */
public abstract class AbstractDataStore<T extends AbstractStoreRecord> implements BeanFactoryAware{
	
	protected String storeDesc ; //store描述主要用于记录日志
	protected DataSource dataSource; //数据源 依赖Spring注入
	protected BeanFactory beanFactory;//spring bean 工厂
	protected Map<Object,T> recordMap = new HashMap<Object,T>();
	private Logger logger = Logger.getLogger(AbstractDataStore.class);

	/**
	 * 设置数据源，缓存描述， 初始化缓存
	 * @param dataSource 数据源
	 * @param storeDesc 缓存描述
	 * @throws Exception 
	 */
	AbstractDataStore(DataSource dataSource,String storeDesc) throws Exception {
		this.dataSource = dataSource;
		this.storeDesc = storeDesc;
		//this.load();
		logger.info("成功创建" + storeDesc + "....");
	}
	
	/**
	 * 装载缓存
	 */
	protected abstract void load() throws Exception;
	
	/**
	 * 刷新缓存
	 * @param ds
	 * @throws Exception 
	 */
	public void reload() throws Exception {
		recordMap.clear();
		load();
		logger.info("成功刷新" + storeDesc + ",准备刷新连接池.....");
		reloadCallback();
	}
	
	/**
	 * 刷新缓存后回调函数(默认空实现,子类可重写实现相应功能)
	 * @throws Exception 
	 */
	public  void reloadCallback() throws Exception{
		
	}
	
	/**
	 * 查询缓存（通过equals）
	 * @param record
	 */
	public T getRecord(Object key) {
		return recordMap.get(key);
	}

	/**
	 * 增加缓存一条记录
	 * @param record
	 */
	public void addRecord(T record) throws Exception{
		recordMap.put(record.getKey(), record);
		logger.info("成功增加key(" + record.getKey() + ")到" +  storeDesc + "....");
		addRecordCallback(record);
	}
	
	/**
	 * 增加一条缓存后回调函数(默认空实现,子类可重写实现相应功能)
	 * @throws Exception 
	 */
	public void addRecordCallback(T record) throws Exception{
	}
	
	
	/**
	 * 更新一条缓存记录
	 * @param record
	 */
	public void updateRecord(T record) throws Exception{
		recordMap.remove(record.getKey());
		recordMap.put(record.getKey(), record);
		logger.info("成功更新key(" + record.getKey() + ") in " +  storeDesc + "....");
		updateRecordCallback(record);
	}
	
	/**
	 * 更新一条缓存后回调函数(默认空实现,子类可重写实现相应功能)
	 * @throws Exception 
	 */
	public void updateRecordCallback(T record) throws Exception{
	}
	
	/**
	 * 删除一条缓存记录
	 * @param record
	 */
	public void deleteRecord(T record) throws Exception{
		recordMap.remove(record.getKey());
		logger.info("成功从" + storeDesc + "中删除key(" + record.getKey() + ")....");
		deleteRecordCallback(record);
	}
	
	/**
	 * 删除一条缓存后回调函数(默认空实现,子类可重写实现相应功能)
	 * @throws Exception 
	 */
	public void deleteRecordCallback(T record) throws Exception{
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public Map<Object, T> getDataStoreMap() {
		return recordMap;
	}
}
