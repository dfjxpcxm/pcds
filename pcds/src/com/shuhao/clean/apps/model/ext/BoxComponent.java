package com.shuhao.clean.apps.model.ext;

public abstract class BoxComponent extends Layout{

	protected Integer width;
	protected Integer height;
	protected String fieldLabel;
	protected String xtype;
	protected String anchor;
	protected String renderTo;
	protected boolean hidden = false;
	protected boolean disabled = false;
	protected boolean autoWidth = true;
	protected boolean autoHeight= true;
	
	public String baseParams(){
		StringBuffer buffer = new StringBuffer();
		if(isNotNull(this.id)){
			buffer.append("id : '").append(this.id).append("',").append(enter);
		}
		if(isNotNull(this.xtype)){
			buffer.append("xtype : '").append(this.xtype).append("',").append(enter);
		}
		if(isNotNull(this.width)){
			buffer.append("width : ").append(this.width).append(",").append(enter);
		}
		if(isNotNull(this.height)){
			buffer.append("height : ").append(this.height).append(",").append(enter);
		}
		
		if(isNotNull(this.fieldLabel)){
			buffer.append("fieldLabel : '").append(this.fieldLabel).append("',").append(enter);
		}
		if(isNotNull(this.anchor)){
			buffer.append("anchor : '").append(this.anchor).append("',").append(enter);
		}
		if(isNotNull(this.renderTo)){
			buffer.append("renderTo : '").append(this.renderTo).append("',").append(enter);
		}
		buffer.append("disabled : ").append(this.disabled).append(",").append(enter);
		buffer.append("hidden : ").append(this.hidden);
		return buffer.toString();
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public boolean isAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
	}

	public boolean isAutoHeight() {
		return autoHeight;
	}

	public void setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getRenderTo() {
		return renderTo;
	}

	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	
	
}
