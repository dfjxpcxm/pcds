package com.shuhao.clean.apps.model.ext.base;

import com.shuhao.clean.apps.model.ext.Ext;


public abstract class BaseExt implements Ext {
	
	protected String id;
	protected final String enter = "\n";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isNotNull(Object obj){
		boolean b = true;
		if(obj == null){
			b = false;
		}else{
			if(obj instanceof String){
				String str = (String) obj;
				if(str.trim().equals("")){
					b = false;
				}
			}
		}
		return b;
	}
	
	
}
