var expander = new Ext.grid.RowExpander({
	tpl :new Ext.Template('<br><p><b>文件内容:</b><br/> {event_file}</p><br/>')
});

//store
var page_event_ds = new Ext.data.JsonStore({
	url : pathUrl + '/pageEvent/getPageEvents',
	root : 'results',
	fields : ['file_id', 'file_desc', 'update_time', 'update_user','event_file'],
	autoLoad : true
});
 

//column
var page_event_cm = [expander,{
	header : '事件文件编号',
	dataIndex : 'file_id'
}, {
	header : '事件文件描述',
	dataIndex : 'file_desc'
}, {
	header : '最后修改时间',
	dataIndex : 'update_time'
},{
	header : '修改用户',
	dataIndex : 'update_user'
}];

Ext.onReady(function() {
	var myview = new MyViewportUi();
	/*page_event_ds.load({params:{aa:'bb'},callback:function(){
		alert(this.getAt(0).get('event_file'));
	}});*/
});

var pageEventGrid ={
	xtype : 'grid',
	region : 'center',
	id : 'pageEventGrid',
	title : '页面事件列表',
	columns : page_event_cm,
	plugins : expander,
	store : page_event_ds,
	loadMask : true,
	split : true,
	sm : new Ext.grid.RowSelectionModel({
		singleSelect : true
	}),
	flex : 1,
	viewConfig : {
		forceFit : true
	},
	tbar : [ {
		xtype : 'button',
		text : '创建新版本',
		iconCls : 'copy',
		handler : function() {
			var array = Ext.getCmp("pageEventGrid").getSelectionModel().getSelections();
			var copyWindow = new CopyWindow();
			copyWindow.show();
			if (array.length > 0) {
				var file_id = array[0].get('file_id');
				Ext.getCmp('copyPageEventForm').load({url:pathUrl+'/pageEvent/getPageEventById', params: {file_id:file_id} });
			} 
		}
	}, '-', {
		xtype : 'button',
		text : '修改',
		iconCls : 'edit',
		handler : function() {
			var array = Ext.getCmp("pageEventGrid").getSelectionModel().getSelections();
			if (array.length == 0) {
				Ext.Msg.alert("提示信息", "请选择需要修改的记录");
			} else {
				var editWindow = new EditWindow();
				editWindow.show();
				var file_id = array[0].get('file_id');
				Ext.getCmp('editPageEventForm').load({url:pathUrl+'/pageEvent/getPageEventById', params: {file_id:file_id} });
			}
		}
	}, '-', {
		xtype : 'button',
		text : '删除',
		iconCls : 'delete',
		handler : function() {
			var array = Ext.getCmp("pageEventGrid").getSelectionModel().getSelections();
			if (array.length == 0) {
				Ext.Msg.alert("提示信息", "请选择需要删除的记录");
			} else {
				var file_id = array[0].get('file_id');
				if(page_event_ds.getCount()==1){
					Ext.Msg.alert('提示信息','唯一的记录，不可被删除');
					return;
				}
				if(file_id != page_event_ds.getAt(0).get('file_id')){
					Ext.Msg.alert("提示信息", "历史记录不可删除");
					return;
				} 
				Ext.MessageBox.confirm('确认信息', '是否确认删除选中的记录?',
					function(btn) {
						if (btn == 'yes'){
							deletePageEvent(file_id,page_event_ds);
						}
					});
			}
		}
	}]

}

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [pageEventGrid]
		});

		MyViewportUi.superclass.initComponent.call(this);

	}
});


AddWindow = Ext.extend(Ext.Window, {
	title : '添加页面事件',
	buttonAlign : 'center',
	id : 'addPageEventWindow',
	width : 430,
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
									src_dim_ds.reload();
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
});

