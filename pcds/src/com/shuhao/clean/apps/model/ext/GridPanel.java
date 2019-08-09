package com.shuhao.clean.apps.model.ext;

public class GridPanel extends BasePanel {
	private String region = "center";
	private JsonStore dataStore;
	private ColumnModel colModel;
	private boolean enableHdMenu = false;
	private boolean loadMask = true;
	private boolean stripeRows = false; //是否显示分割线
	private boolean isAddsm = true; //是否显示分割线
	
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id).append("Grid = new Ext.grid.GridPanel({").append(enter);
		buffer.append("region : '").append(this.region).append("',").append(enter);
		buffer.append("stripeRows : '").append(stripeRows).append("',").append(enter);
		//是否显示表列选项
		//buffer.append("enableHdMenu : ").append(this.enableHdMenu).append(",").append(enter);
		buffer.append("ds : ").append(this.dataStore.output()).append(",").append(enter);
		buffer.append("cm : ").append(this.colModel.output()).append(",").append(enter);
		if(isAddsm){
			buffer.append("sm:winRoleSm,").append(enter);
		}
		buffer.append("loadMask : ").append(this.loadMask).append(",").append(enter);
		buffer.append(this.getBasePanel()).append(enter);
		buffer.append("})").append(enter);
		return buffer.toString();
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public JsonStore getDataStore() {
		return dataStore;
	}
	public void setDataStore(JsonStore dataStore) {
		this.dataStore = dataStore;
	}
	public ColumnModel getColModel() {
		return colModel;
	}
	public void setColModel(ColumnModel colModel) {
		this.colModel = colModel;
	}
	
	public boolean isStripeRows() {
		return stripeRows;
	}
	public void setStripeRows(boolean stripeRows) {
		this.stripeRows = stripeRows;
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.BasePanel#getCurrentRef()
	 */
	public String getCurrentRef() {
		return this.id+"Grid";
	}
	public boolean isAddsm() {
		return isAddsm;
	}
	public void setAddsm(boolean isAddsm) {
		this.isAddsm = isAddsm;
	}
}
