
/*AddWindow = Ext.extend(Ext.Window, {
	title : '添加页面事件',
	buttonAlign : 'center',
	id : 'addPageEventWindow',
	width : 950,
	height : 500,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'fit',
			listeners : {
				close : function() {
					Ext.getCmp("addPageEventWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'addPageEventForm',
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/pageEvent/addPageEvent',
				items : [{
					xtype : 'textfield',
					name : 'file_desc',
					id : 'file_desc',
					fieldLabel : '页面事件描述',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'event_file',
					id : 'event_file',
					fieldLabel : '页面事件内容',
					allowBlank : false,
					height:385,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				id : 'save_page_event',
				handler : function() {
					var formPanel = Ext.getCmp("addPageEventForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									page_event_ds.reload();
									Ext.getCmp('addPageEventWindow').destroy();
								}
							},
							failure : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
							}
						})
					} else {
						Ext.Msg.alert("提示信息", "请输入完整信息");
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("addPageEventWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});*/

var descHtml ="在编辑页面事件内容时,以下特殊符号需要用转义字符来代替：  &nbsp;&nbsp;&nbsp; &lt;: &#38;lt; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&gt;: &#38;gt; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &#38;:   &#38;amp;";
descHtml =  descHtml + "<br/>例如：a &lt; b 写成 a &#38;lt; b";

/**
 * 复制窗口
 * @class EditWindow
 * @extends Ext.Window
 */
CopyWindow = Ext.extend(Ext.Window, {
	title : '添加页面事件',
	buttonAlign : 'center',
	id : 'copyPageEventWindow',
	maximized:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'fit',
			listeners : {
				close : function() {
					Ext.getCmp("copyPageEventWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'copyPageEventForm',
				layout : 'form',
				frame:true,
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/pageEvent/addPageEvent',
				reader : new Ext.data.JsonReader({
					root : 'results'
				}, [
					{name : 'file_desc'}, 
					{name : 'event_file'}
				]),
				items : [{
					xtype : 'panel',
					name : 'desc',
					id : 'desc',
					fieldLabel : '注意事项',
					allowBlank : false,
					html:descHtml,
					readOnly:true,
					style:{
						color:'red',
						border:'none'
					},
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'file_desc',
					id : 'file_desc',
					fieldLabel : '页面事件描述',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'event_file',
					id : 'event_file',
					fieldLabel : '页面事件内容',
					allowBlank : false,
					height:400,
					maximized:true,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				handler : function() {
					
					var formPanel = Ext.getCmp("copyPageEventForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								if (action.result.success) {
									page_event_ds.reload();
									Ext.getCmp('copyPageEventWindow').destroy();
								}
							},
							failure : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
							}
						})
					} else {
						Ext.Msg.alert("提示信息", "请输入完整信息");
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("copyPageEventWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});


/**
 * 修改窗口
 * @class EditWindow
 * @extends Ext.Window
 */
EditWindow = Ext.extend(Ext.Window, {
	title : '修改页面事件',
	buttonAlign : 'center',
	id : 'editPageEventWindow',
	maximized:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'fit',
			listeners : {
				close : function() {
					Ext.getCmp("editPageEventWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [
			   {
				xtype : 'form',
				id : 'editPageEventForm',
				layout : 'form',
				frame:true,
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/pageEvent/editPageEvent',
				reader : new Ext.data.JsonReader({
					root : 'results'
				}, [
				    {name : 'file_id'}, 
					{name : 'file_desc'}, 
					{name : 'event_file'}
				]),
				items : [{
					xtype : 'hidden',
					name : 'file_id',
					id : 'file_id',
					fieldLabel : '页面事件ID',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'panel',
					name : 'desc',
					id : 'desc',
					fieldLabel : '注意事项',
					allowBlank : false,
					html:descHtml,
					readOnly:true,
					style:{
						color:'red',
						border:'none'
					},
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'file_desc',
					id : 'file_desc',
					fieldLabel : '页面事件描述',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'event_file',
					id : 'event_file',
					fieldLabel : '页面事件内容',
					allowBlank : false,
					height:400,
					anchor : '95%'
				}]
			}
			],
			buttons : [{
				text : '保存',
				handler : function() {
					
					Ext.MessageBox.confirm('提示信息','保存后本记录将自动成为系统当前使用的校验配置，确定要保存吗？',function(btn){
						if(btn == 'yes'){
							var formPanel = Ext.getCmp("editPageEventForm");
							if (formPanel.form.isValid()) {
								formPanel.form.submit({
									waitMsg : '正在处理,请稍后......',
									success : function(form, action) {
										if (action.result.success) {
											page_event_ds.reload();
											Ext.getCmp('editPageEventWindow').destroy();
										}
									},
									failure : function(form, action) {
										Ext.Msg.alert("消息", action.result.info);
									}
								})
							} else {
								Ext.Msg.alert("提示信息", "请输入完整信息");
							}
						}
					});
					
					
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("editPageEventWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});
/***
 * 删除方法
 * @param file_id
 */
function deletePageEvent(file_id,page_event_ds){
	Ext.Ajax.request({
		url:pathUrl+'/pageEvent/deletePageEvent',
		params:{
			file_id:file_id
		},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				page_event_ds.load();
			}
			Ext.Msg.alert('提示信息',json.info);
		}
		
	});
	
}

