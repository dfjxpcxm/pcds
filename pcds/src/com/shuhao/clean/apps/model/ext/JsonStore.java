package com.shuhao.clean.apps.model.ext;

import java.util.List;

import com.shuhao.clean.apps.model.ext.base.BaseExt;

public class JsonStore extends BaseExt {
	private String url;
	private List<String>  baseParams;    //待定
	private boolean autoLoad = false;
	private JsonReader reader;
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id).append("Store= new Ext.data.Store({").append(enter);
		buffer.append(" proxy: new Ext.data.HttpProxy({").append(enter);
		buffer.append("url : pathUrl + '").append(this.url).append("'").append(enter);
		buffer.append(" }),").append(enter);
		buffer.append("autoLoad : ").append(this.autoLoad).append(",").append(enter);
		if(this.baseParams != null){
			buffer.append("baseParams : {").append(enter);
			for (int i = 0; i < baseParams.size(); i++) {
				buffer.append(baseParams.get(i));
				if(i == baseParams.size() -1){
					buffer.append(enter);
				}else{
					buffer.append(",").append(enter);
				}
			}
			buffer.append("},").append(enter);
		}
		buffer.append("reader :").append(this.reader.output());
		buffer.append(",remoteSort: false").append(enter);
		buffer.append("})").append(enter);
		return buffer.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(List<String> baseParams) {
		this.baseParams = baseParams;
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public JsonReader getReader() {
		return reader;
	}

	public void setReader(JsonReader reader) {
		this.reader = reader;
	}
	
	
}
