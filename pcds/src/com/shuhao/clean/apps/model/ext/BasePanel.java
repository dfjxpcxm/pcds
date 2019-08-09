package com.shuhao.clean.apps.model.ext;

import java.util.List;

public abstract class BasePanel extends BoxComponent {
	protected String title;
	protected String layout;
	protected String iconCls;
	protected String html;
	protected Tbar tbar;
	protected Bbar  bbar;
	protected Layout items;
	protected String buttonAlign;
	protected List<Button> buttons;
	protected boolean border = true;
	protected boolean frame = false;
	protected boolean readOnly = false;
	protected boolean closable = true;
	protected boolean autoScroll = true;
	protected String bodyStyle =  "padding:5px 10px 0 10px";
	
	public String getBasePanel() throws Exception{
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.baseParams()).append(",").append(enter);
		
		if(isNotNull(this.title)){
			buffer.append("title : '").append(this.title).append("',").append(enter);
		}
		if(isNotNull(this.layout)){
			buffer.append("layout : '").append(this.layout).append("',").append(enter);
		}
		if(isNotNull(this.iconCls)){
			buffer.append("iconCls : '").append(this.iconCls).append("',").append(enter);
		}
		if(isNotNull(this.html)){
			buffer.append("html : '").append(this.html).append("',").append(enter);
		}
		buffer.append("border : ").append(this.border).append(",").append(enter);
		buffer.append("hidden : ").append(this.hidden).append(",").append(enter);
		buffer.append("readOnly : ").append(this.readOnly).append(",").append(enter);
		buffer.append("closable : ").append(this.closable).append(",").append(enter);
		buffer.append("frame : ").append(this.frame).append(",").append(enter);
		buffer.append("autoScroll : ").append(this.autoScroll);
		if(isNotNull(this.bodyStyle)){
			buffer.append(",").append(enter);
			buffer.append("bodyStyle : '").append(this.bodyStyle).append("'");
		}
		
		
		if(this.items != null){
			buffer.append(",").append(enter);
			buffer.append("items : ").append(this.items.output());
		}
		
		if(isNotNull(this.buttonAlign)){
			buffer.append(",").append(enter);
			buffer.append("buttonAlign : '").append(this.buttonAlign).append("'");
		}
		if(this.tbar != null){
			buffer.append(",").append(enter);
			buffer.append("tbar : [").append(this.tbar.output()).append("]");
		}
		
		if(this.bbar != null){
			buffer.append(",").append(enter);
			buffer.append("bbar : ").append(this.bbar.output());
		}
		
		
		if(this.buttons != null && this.buttons.size() > 0){
			buffer.append(",").append(enter);
			buffer.append("buttons : [ ");
			for (int i = 0; i < buttons.size(); i++) {
				Button button = buttons.get(i);
				if(i == buttons.size() -1){
					buffer.append(button.output()).append(enter);
				}else{
					buffer.append(button.output()).append(",").append(enter);
				}
			}
			buffer.append("]").append(enter);
		}
		
		return buffer.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}


	public Bbar getBbar() {
		return bbar;
	}

	public void setBbar(Bbar bbar) {
		this.bbar = bbar;
	}


	public String getButtonAlign() {
		return buttonAlign;
	}

	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isFrame() {
		return frame;
	}

	public void setFrame(boolean frame) {
		this.frame = frame;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public boolean isAutoScroll() {
		return autoScroll;
	}

	public void setAutoScroll(boolean autoScroll) {
		this.autoScroll = autoScroll;
	}

	public String getBodyStyle() {
		return bodyStyle;
	}

	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public Tbar getTbar() {
		return tbar;
	}

	public void setTbar(Tbar tbar) {
		this.tbar = tbar;
	}

	public Layout getItems() {
		return items;
	}

	public void setItems(Layout items) {
		this.items = items;
	}
	
	
	//获取自身对象引用
	public abstract String getCurrentRef();
}
