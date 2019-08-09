package com.shuhao.clean.apps.model.ext;

public class ComboBox extends BaseField {
	
	private JsonStore store;
	private String storeName;
	private String displayField;
	private String valueField;
	private String hiddenName;
	private Integer maxHeight;
	private Integer minHeight;
	private String triggerAction = "all";
	private String loadingText = "查询中...";
	private String mode = "local";
	private boolean allowBlank = true;

	
//	var currencyDataStore = new Ext.data.Store({
//		proxy: new Ext.data.HttpProxy({
//			url: pathUrl + '/selectorAjax.do?method=listCurrency'
//		}),
//		reader: new Ext.data.JsonReader({
//			root: 'results',
//			totalProperty: 'totalCount',
//			id:'currency_code'
//		},
//		[{name: 'currency_code', mapping:'currency_code'},
//		 {name: 'currency_desc', mapping:'currency_desc'}]),
//		remoteSort: false
//	});
//	currencyDataStore.load();
	
	
//	dataTypeDS.on('load',function(){
//		   if(dataTypeDS.getCount()>0){
//		      Ext.getCmp('data_type').setValue(data_type_id);
//		   }else{
//			   Ext.getCmp('data_type').setValue(null); 
//		   }
//	  });
	
//	measureComboBox=new Ext.form.ComboBox({
//	store: fieldListDataStore,
//	valueField :'field',
//	displayField:'desc',
//	mode: 'local',
//	editable: false,
//	triggerAction: 'all',
//	fieldLabel:'指标',
//	name: 'measureComboBox',
//	id: 'measureComboBox',
//	anchor: '100%'
//}),

	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Combo = new Ext.form.ComboBox({").append(enter);
		buffer.append("store : ").append(this.storeName).append(",").append(enter);
		buffer.append("valueField : '").append(this.valueField).append("',").append(enter);
		buffer.append("displayField : '").append(this.displayField).append("',").append(enter);
		buffer.append("mode : '").append(this.mode).append("',").append(enter);
		buffer.append("loadingText : '").append(this.loadingText).append("',").append(enter);
		buffer.append("triggerAction : '").append(this.triggerAction).append("',").append(enter);
		buffer.append("hiddenName : '").append(this.hiddenName).append("',").append(enter);
		buffer.append("allowBlank : ").append(this.allowBlank).append(",").append(enter);
		if (isNotNull(this.maxHeight)) {
			buffer.append("maxHeight : ").append(this.maxHeight).append(",").append(enter);
		}
		if (isNotNull(this.minHeight)) {
			buffer.append("minHeight : ").append(this.minHeight).append(",").append(enter);
		}
		buffer.append(this.fieldParams());
		
		buffer.append("})");

		return buffer.toString();
	}



	public JsonStore getStore() {
		return store;
	}



	public void setStore(JsonStore store) {
		this.store = store;
	}



	public String getDisplayField() {
		return displayField;
	}



	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}



	public String getValueField() {
		return valueField;
	}



	public void setValueField(String valueField) {
		this.valueField = valueField;
	}



	public String getHiddenName() {
		return hiddenName;
	}



	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}



	public Integer getMaxHeight() {
		return maxHeight;
	}



	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}



	public Integer getMinHeight() {
		return minHeight;
	}



	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}



	public String getTriggerAction() {
		return triggerAction;
	}



	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}



	public String getMode() {
		return mode;
	}



	public void setMode(String mode) {
		this.mode = mode;
	}



	public boolean isAllowBlank() {
		return allowBlank;
	}



	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
	
}
