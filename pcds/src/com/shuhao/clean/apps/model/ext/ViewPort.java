package com.shuhao.clean.apps.model.ext;

import java.util.List;

import com.shuhao.clean.apps.model.ext.base.BaseExt;


public class ViewPort extends BoxComponent {
	private String layout = "border";
	private List<BaseExt> items;
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("var ").append(this.id).append("View = new Ext.Viewport({").append(enter);
		buffer.append("layout : '").append(this.layout).append("',").append(enter);
		buffer.append("items : [").append(enter);
		if(this.items.size() > 0){
			for (int i = 0; i < items.size(); i++) {
				BaseExt ext = items.get(i);
				buffer.append(ext.output());
				if(i == items.size() -1 ){
					buffer.append(enter);
				}else{
					buffer.append(",").append(enter);
				}
			}
		}
		buffer.append("]").append(enter);
		buffer.append("});");
		return buffer.toString();
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public List<BaseExt> getItems() {
		return items;
	}

	public void setItems(List<BaseExt> items) {
		this.items = items;
	}

}
