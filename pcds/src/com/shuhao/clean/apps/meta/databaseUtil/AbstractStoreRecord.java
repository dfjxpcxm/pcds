package com.shuhao.clean.apps.meta.databaseUtil;

public abstract class AbstractStoreRecord {
	
	/**
	 * 返回主键
	 * @return
	 */
	public abstract Object getKey();

	/**
	 *重写equals： 如果主键相等，则equals
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) {
			return false;
		} if(obj.getClass().equals(this.getClass())) {
			AbstractStoreRecord store2 = (AbstractStoreRecord)obj;
			return getKey().equals(store2.getKey());
		}
		return false;
	}
}
