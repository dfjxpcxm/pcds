function showWindow(menuName,g){
	var rows = null;
	var initwindow = new Ext.Window({
		title : '初始页设置',
		width : 600,
		height : 400,
		modal : true,
		closable : false,
		resizable : false,
		layout  :'fit',
		buttonAlign : 'center',
		items : [{
			region : 'center',
			xtype : 'grid',
			id : 'initpage_grid',
			frame : false,
			split : true,
			columnLines : true,
			loadMask : true,
			border : false,
			ds : initpageGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url  : pathUrl + '/userExtAjax.do?method=getUserPage'
				}),
				autoLoad : true,
				reader : new Ext.data.JsonReader({
					root : 'results',
					totalProperty : 'totalCount'
				},[
					{name : 'uuid',mapping:'uuid',type : 'string'},
					{name : 'resource_id',mapping:'resource_id',type : 'string'},
					{name : 'resource_name',mapping:'resource_name',type : 'string'},
					{name : 'handler',mapping:'handler',type : 'string'}
				])
			}),
			cm : new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer(),
				{
					id : 'uuid',
					header : 'uuid',
					dataIndex : 'uuid',
					hidden : true
				},{
					id : 'resource_id',
					header : '菜单编号',
					dataIndex : 'resource_id'
				},{
					id : 'resource_name',
					header : '菜单名称',
					dataIndex : 'resource_name'
				},{
					id : 'handler',
					header : '菜单路径',
					dataIndex : 'handler'
				}
			]),
			viewConfig : {forceFit : true},
			listeners : {
				'rowclick' : function(){
					var gridPanel = Ext.getCmp('initpage_grid');
					rows = gridPanel.getSelectionModel().getSelections()[0];
				}
			},
			tbar : ['关键字：',{
				xtype : 'textfield',
				fieldLabel : '搜索',
				width : 150,
				id : 'searchKey',
				name : 'searchKey',
				emptyText : '请输入菜单名称'
			},'&nbsp;',{
				text : '查找',
				iconCls : 'search',
				tooltip : '查找',
				handler : function(){
					var searchKey = Ext.getCmp('searchKey').getValue();
					initpageGridStore.load({params : {searchKey:searchKey}});
				}
			},'-','当前主页:','<font color="red">'+menuName+'</font>']
		}],
//		listener : {
//			show : function (){
//				Ext.Ajax.request({
//					url : '',
//					method : 'POST',
//					callback : function(options,success,response){
//						var json = Ext.uril.JSON.decode(response.responseText);
//						if(json.success){
//							menuName = json.info;
//						}
//					}
//				});
//			}
//		},
		buttons : [{
			text : '保存',
			handler : function (){
				if(rows == null){
					Ext.MessageBox.alert('提示','请选择一条数据');
					return;
				}
				var resourceId = rows.get('uuid');
				var handler = rows.get('handler');
				Ext.Ajax.request({
					url : pathUrl + '/userExtAjax.do?method=setUserInitPage',
					method : 'POST',
					params : {resourceId : resourceId},
					callback : function(options,success,response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							Ext.MessageBox.alert('提示','设置成功');
							initwindow.destroy();
							g.reset(g.actionTabid,pathUrl+'/'+handler);
						}else{
							Ext.MessageBox.alert('提示','设置失败');
							return ;
						}
					}
				});
			}
		},{
			text : '取消',
			handler : function (){
				initwindow.destroy();
			}
		}]
	});
	initwindow.show();
}