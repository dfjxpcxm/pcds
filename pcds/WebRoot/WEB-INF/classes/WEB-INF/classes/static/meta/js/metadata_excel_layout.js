var formItemAnchor = '91%';

var activedTabId = '';
var loadedPanelIds = '';
var sheet_id = '';
Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		region : 'north',
		height : 150,
		title : 'Excel模板属性',
		layout : 'form',
		url : pathUrl + "/metadata/excel/save",
		method : 'POST',
		border : false,
		split : false,
		frame : false,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 10px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'excel_id'}, 
			{name : 'excel_tmpl_name'}
		]),
		items : [{
			xtype : 'textfield',
			name : 'excel_id',
			fieldLabel : 'Excel模板ID',
			readOnly : true,
			allowBlank : false,
			anchor : formItemAnchor
		}, {
			xtype : 'textfield',
			itemCls  : 'uxHeight',
			id : 'excel_tmpl_name',
			name : 'excel_tmpl_name',
			fieldLabel : '模板名',
			allowBlank : false,
			anchor : formItemAnchor
		}],
		buttons : [{
			xtype : 'button',
			text : '保存',
			handler : function() {
				saveExcel(infoPanel);
			}
		}, {
			xtype : 'button',
			text : '删除',
			handler : function() {
				Ext.MessageBox.confirm("确认信息", "是否删除该表信息?", function(btn) {
					if (btn == 'yes')
						deleteExcel();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/excel/load',
		params : {
			excel_id : metadata_id
		}
	});
	
	var tabpanel = new Ext.TabPanel({
		title : 'sheet页',
		activeTab : 0,
		tabPosition : 'bottom',
		region : 'center',
		split : false,
		border : false,
		frame : true,
		listeners : {
			'tabchange' : function(tab,p){
				var pid = p.getId();
				if(pid != 'addTab'){
					activedTabId = pid;
					sheet_id  = activedTabId.substring(activedTabId.indexOf('_')+1);
				}else  {
					addSheet(tab);
					tab.setActiveTab(activedTabId);
					return;
				}
				
				if(loadedPanelIds.indexOf(pid) < 0) {
					p.getStore().load();
					loadedPanelIds += ";" + pid;
				}
			}
		},
		items : []
	});
	
	Ext.Ajax.request({
		url : pathUrl + '/metadata/sheet/querySheetByExcelId',
		params : {
			excel_id : metadata_id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				var r = json.results;
				for (var i = 0; i < r.length; i++) {
					tabpanel.add(getSheetPanel(r[i].sheet_id, r[i].sheet_name));
					if(i == 0) {
						tabpanel.setActiveTab("gridPanel_" + r[i].sheet_id);
					}
				}
				
				tabpanel.add({
					id : 'addTab',
					title : '添加Sheet页(+)'
				});
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		border : false,
		items : [infoPanel, tabpanel, {region : 'south', height : 5}]
	});
});
