package com.shuhao.clean.apps.model.ext;

import java.util.ArrayList;
import java.util.List;

import com.shuhao.clean.apps.model.ext.base.BaseExt;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Tbar extends BaseExt{
	
	private List<Button> tbars  = new ArrayList<Button>();

	public String output()throws Exception {
		StringBuffer buffer = new StringBuffer();
		if(this.tbars != null && this.tbars.size() > 0){
			for (int i = 0; i < tbars.size(); i++) {
				buffer.append(tbars.get(i).output());
				if( i == tbars.size() -1){
					buffer.append(enter);
				}else{
					buffer.append(",");
					if(!tbars.get(i).isHidden()){
						buffer.append("'-',");
					}
					buffer.append(enter);
				}
			}
		}
		return buffer.toString();
	}

	public List<Button> getTbars() {
		return tbars;
	}

	public void setTbars(List<Button> tbars) {
		this.tbars = tbars;
	}
	
	public void addBtn(Button btn) {
		this.tbars.add(btn);
	}
	
	

}
