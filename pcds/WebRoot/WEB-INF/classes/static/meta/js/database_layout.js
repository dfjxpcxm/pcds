
//var linkDS = null; //连接数据源
var userDS = null; //用户数据
var baseDS = null; //数据库信息
var tablespaceDS = null; //表空间信息
Ext.onReady(function() {
	//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		//数据库表
		var baseCM = new Ext.grid.ColumnModel( [ {
			header : '数据库名称',
			width : 100,
			sortable : true,
			dataIndex : 'database_name'
		}, {
			header : '数据库IP',
			width : 100,
			sortable : true,
			dataIndex : 'database_ip'
		}, {
			header : '数据库类型',
			width : 100,
			sortable : true,
			dataIndex : 'database_type_name'
		}, {
			header : '连接字符串',
			width : 100,
			sortable : true,
			dataIndex : 'conn_url'
		}, {
			header : '访问端口',
			width : 100,
			sortable : true,
			dataIndex : 'access_post'
		}, {
			header : '字符编码',
			width : 100,
			sortable : true,
			dataIndex : 'encoding'
		}, {
			header : '连接方式',
			width : 100,
			dataIndex : 'conn_type',
			renderer : function(v) {
				if (v == "1") {
					return "数据库";
				}
				return "SSH";

			}
		}, {
			header : '配置日期',
			width : 100,
			sortable : true,
			dataIndex : 'config_date'
		}, {
			header : '数据库描述',
			width : 100,
			dataIndex : 'database_desc'
		}, {
			header : '显示顺序',
			width : 50,
			sortable : true,
			dataIndex : 'display_order'
		}, {
			header : '是否显示',
			width : 50,
			dataIndex : 'is_display',
			renderer : displayShow
		} ]);

		function displayShow(v) {
			if (v == "1") {
				return "是";
			} else {
				return "否";
			}
		}
		//数据库数据源
		baseDS = new Ext.data.JsonStore( {
			url : pathUrl + '/uppDatabase/queryUppDatabaseList',
			root : 'results',
			fields : [ 'database_id', 'database_name', 'database_ip',
					'database_type_name', 'conn_url', 'access_post',
					'encoding', 'conn_type', 'config_date', 'database_desc',
					'display_order', 'is_display' ]
			,autoLoad : true
		});
		//数据库维护菜单
		var dbasebar = [
				{
					text : '添加',
					iconCls : 'add',
					handler : function() {
						doAddDataBase();
					}
				},
				'-',
				{
					text : '编辑',
					iconCls : 'edit',
					handler : function() {
						if (basePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}

						var baseid = basePanel.getSelectionModel().selections
								.get(0).get("database_id");
						updateBaseInfo(baseid);
					}
				},
				'-',
				{
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						if (basePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}

						var baseid = basePanel.getSelectionModel().selections
								.get(0).get("database_id");
						deleteBaseInfo(baseid);
					}
				} ];

		//数据库信息列表
		var basePanel = new Ext.grid.GridPanel( {
			id : "bspanel",
			region:'north',
			height : 200,
			split : true,
			title : '数据库信息',
			style:'padding:4px 4px 0px 4px;',
			sm : new Ext.grid.RowSelectionModel({
                			 singleSelect : true
             				}),
			ds : baseDS,
			cm : baseCM,
			tbar : dbasebar,
			loadMask : true,
			viewConfig : {
				forceFit : true
			}
		});
		basePanel.getSelectionModel().on("rowselect",function(sm,rowIndex,record){
			var id =record.get('database_id');
			tablespaceDS.reload( {
				params : {
					database_id : id
				}
			});
			userDS.reload( {
				params : {
					database_id : id
				}
			});
			/*linkDS.reload( {
				params : {
					database_id : id
				}
			});*/
		});
		//数据库表空间信息
		var tablespaceCM = new Ext.grid.ColumnModel( [ {
			header : '表空间名称',
			width : 100,
			sortable : true,
			dataIndex : 'tablespace_name'
		}, {
			header : '表空间描述',
			width : 100,
			dataIndex : 'tablespace_desc'
		}, {
			header : '显示顺序',
			width : 60,
			sortable : true,
			dataIndex : 'display_order'
		}, {
			header : '是否显示',
			width : 60,
			sortable : true,
			dataIndex : 'is_display',
			renderer : displayShow
		} ]);

		//表空间数据源
		tablespaceDS = new Ext.data.JsonStore( {
			url : pathUrl + '/uppDatabase/queryUppTablespaceList',
			root : 'results',
			fields : [ 'tablespace_id','database_id', 'tablespace_name', 'tablespace_desc',
					'display_order', 'is_display' ]
		});

		//表空间菜单
		var dbSpacebar = [
				{
					text : '添加',
					iconCls : 'add',
					handler : function() {
						if (basePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条数据库记录，继续进行！');
							return;
						}
						var database_id = basePanel.getSelectionModel()
								.getSelections()[0].get('database_id');
						doAddTabSpace(database_id);
					}
				},
				'-',
				{
					text : '编辑',
					iconCls : 'edit',
					handler : function() {
						if (spacePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}
						/*var database_id = spacePanel.getSelectionModel()
								.getSelections()[0].get('database_id');*/
						var tablespace_id = spacePanel.getSelectionModel().selections
						.get(0).get("tablespace_id");
						var tablespace_name = spacePanel.getSelectionModel().selections
								.get(0).get("tablespace_name");
						
						updateTabSpaceInfo(tablespace_name, tablespace_id);
					}
				},
				'-',
				{
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						/*var database_id = spacePanel.getSelectionModel()
								.getSelections()[0].get('database_id');*/
						if(!spacePanel.getSelectionModel().selections.getCount()){
							return;
						}
						var tablespace_id = spacePanel.getSelectionModel().selections
						.get(0).get("tablespace_id");
						
						var tablespace_name = spacePanel.getSelectionModel().selections
								.get(0).get("tablespace_name");
						deleteTabSpaceInfo(tablespace_name, tablespace_id);
					}
				} ];

		var spacePanel = new Ext.grid.GridPanel( {
			id : "spacePanel",
			width : "100%",
			height : 370,
			split : true,
			border : false,
			title : '表空间信息',
			ds : tablespaceDS,
			cm : tablespaceCM,
			tbar : dbSpacebar,
			loadMask : true,
			viewConfig : {
				forceFit : true
			}
		});
		//数据库授权用户
		var userCM = new Ext.grid.ColumnModel( [ {
			header : '用户名',
			width : 120,
			sortable : true,
			dataIndex : 'owner_name'
		}, {
			header : '用户描述',
			width : 120,
			dataIndex : 'owner_desc'
		}, {
			header : '显示顺序',
			width : 70,
			sortable : true,
			dataIndex : 'display_order'
		}, {
			header : '是否显示',
			width : 70,
			sortable : true,
			dataIndex : 'is_display',
			renderer : displayShow
		} ]);

		//用户数据源
		userDS = new Ext.data.JsonStore({
			url : pathUrl + '/uppDatabase/queryUppDatabaseUserList',
			root : 'results',
			fields : ['db_user_id', 'database_id', 'owner_name', 'owner_password',
					'owner_desc', 'display_order', 'is_display' ]
		});

		//用户菜单栏
		var dbUserBar = [
				{
					text : '添加',
					iconCls : 'add',
					handler : function() {
						if (basePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条数据库记录，继续进行！');
							return;
						}
						var database_id = basePanel.getSelectionModel()
								.getSelections()[0].get('database_id');
						doAddPrivUser(database_id);
					}
				},
				'-',
				{
					text : '编辑',
					iconCls : 'edit',
					handler : function() {
						if (userPanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}

						/*var baseid = userPanel.getSelectionModel().selections
								.get(0).get("database_id");*/
						var db_user_id = userPanel.getSelectionModel().selections
						.get(0).get("db_user_id");
						var user_name = userPanel.getSelectionModel().selections
								.get(0).get("owner_name");
						updatePrivUserInfo(db_user_id, user_name);
					}
				},
				'-',
				{
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						if (userPanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}
						//删除用户
						var db_user_id = userPanel.getSelectionModel().selections
						.get(0).get("db_user_id");
						/*var baseid = userPanel.getSelectionModel().selections
								.get(0).get("database_id");*/
						var user_name = userPanel.getSelectionModel().selections
								.get(0).get("owner_name");
						deletePrivUserInfo(db_user_id, user_name);
					}
				} ];

		//用户信息列表
		var userPanel = new Ext.grid.GridPanel( {
			id : "userPanel",
			region : "center",
			//width : "100%",
			split : true,
			//border : false,
			title : '用户信息',
			ds : userDS,
			cm : userCM,
			style:'padding:0px 0px 4px 0px;', 
			tbar : dbUserBar,
			loadMask : true,
			viewConfig : {
				forceFit : true
			}
		});
		//数据库表链接
		/*var linkCM = new Ext.grid.ColumnModel( [ {
			header : '连接名称',
			sortable : true,
			dataIndex : 'dblink_name'
		}, {
			header : '连接描述',
			dataIndex : 'dblink_desc'
		}, {
			header : '显示顺序',
			sortable : true,
			dataIndex : 'display_order'
		}, {
			header : '是否显示',
			sortable : true,
			dataIndex : 'display_flag',
			renderer : displayShow
		} ]);

		linkDS = new Ext.data.JsonStore( {
			url : pathUrl + '/uppDatabase/?',
			root : 'results',
			fields : [ 'database_id', 'dblink_name', 'dblink_desc',
					'display_order', 'display_flag' ]
		});

		//连接菜单栏
		var dbLinkBar = [
				{
					text : '添加',
					iconCls : 'add',
					handler : function() {
						if (basePanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条数据库记录，继续进行！');
							return;
						}
						var database_id = basePanel.getSelectionModel()
								.getSelections()[0].get('database_id');
						doAddLinkInfo(database_id);
					}
				},
				'-',
				{
					text : '编辑',
					iconCls : 'edit',
					handler : function() {
						if (linkPanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}

						var baseid = linkPanel.getSelectionModel().selections
								.get(0).get("database_id");
						var linkName = linkPanel.getSelectionModel().selections
								.get(0).get("dblink_name");
						updateDbLinkInfo(baseid, linkName);
					}
				},
				'-',
				{
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						if (linkPanel.getSelectionModel().selections.length != 1) {
							Ext.MessageBox.alert('错误', '请选择一条记录，继续进行！');
							return;
						}
						//删除数据库连接
						var baseid = linkPanel.getSelectionModel().selections
								.get(0).get("database_id");
						var linkName = linkPanel.getSelectionModel().selections
								.get(0).get("dblink_name");
						deleteDbLinkInfo(baseid, linkName);
					}
				} ];

		//连接列表
		var linkPanel = new Ext.grid.GridPanel( {
			id : "linkPanel",
			split : true,
			width : "100%",
			height : 370,
			border : false,
			title : '数据库连接信息',
			ds : linkDS,
			cm : linkCM,
			tbar : dbLinkBar,
			loadMask : true,
			viewConfig : {
				forceFit : true
			}
		});*/

		var viewport = new Ext.Viewport( {
			border : false,
			layout : 'fit',
			items : [ {
				xtype : 'panel',
				layout : "border",
				border : false,
				items : [ basePanel, userPanel, {
					id : "west",
					region : "west",
					split : true,
					width : "50%",
					title : "",
					style:'padding:0px 0px 4px 4px;',
					items : [ spacePanel ]
				} ]
			} ]
		});
	viewport.render("content");
	
	//baseDS.load();
});