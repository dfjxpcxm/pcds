Ext.ns('clean');
/**
 * 扩展表单验证
 */
Ext.apply(Ext.form.VTypes,
{
	daterange : function(val, field) {
		var date = field.parseDate(val);
		if (!date) {
			return false;
		}
		if (field.startDateField) {
			var start = Ext.getCmp(field.startDateField);
			if (!start.maxValue
					|| (date.getTime() != start.maxValue.getTime())) {
				start.setMaxValue(date);
				start.validate();
			}
		} else if (field.endDateField) {
			var end = Ext.getCmp(field.endDateField);
			if (!end.minValue
					|| (date.getTime() != end.minValue.getTime())) {
				end.setMinValue(date);
				end.validate();
			}
		}
		return true;
	}
});

/**
 * 开始日期 结束日期控件
 * @memberOf {TypeName} 
 */
clean.DateRange = Ext.extend(Ext.Container, {
	
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.anchor = config.anchor || '81%';
		this.rxcfg.width = config.width;
		this.rxcfg.dateFormat = config.format||'Y-m-d';
		this.rxcfg.defaultValue = config.defaultValue;
		this.rxcfg.editable = config.editable;
		this.rxcfg.startDateLabel = config.startDateLabel||'开始日期';
		this.rxcfg.endDateLabel = config.endDateLabel||'结束日期';
		//this.rxcfg.displeyMode = PANEL_DISPLAY_MODE || '0';
        clean.DateRange.superclass.constructor.call(this, config);
    },
	initComponent : function() {
		//竖向
		Ext.applyIf(this, {
			border:false,
			layout:'form',
			items : [{
				xtype : 'datefield',
				anchor : this.rxcfg.anchor,
				name : 'ksrz',
				id : 'ksrq',
				width:this.rxcfg.width,
				format:this.rxcfg.dateFormat,
				fieldLabel : this.rxcfg.startDateLabel,
				vtype : 'daterange',
				endDateField : 'jsrq',
				editable:this.rxcfg.editable,
				value:this.rxcfg.defaultValue
			}, {
				xtype : 'datefield',
				anchor : this.rxcfg.anchor,
				name : 'jsrz',
				id : 'jsrq',
				width:this.rxcfg.width,
				format:this.rxcfg.dateFormat,
				fieldLabel : this.rxcfg.endDateLabel,
				vtype : 'daterange',
				startDateField : 'ksrq',
				editable:this.rxcfg.editable,
				value:this.rxcfg.defaultValue
			} ]
		});
		/*if(this.rxcfg.displeyMode == '1'){
			Ext.applyIf(this, {
				border:false,
				layout : 'column',
				labelAlign:'right',
				items : [{
					layout : 'form',
					columnWidth : 0.5,
					border : false,
					items : [ {
						xtype : 'datefield',
						anchor : this.rxcfg.anchor,
						name : 'ksrz',
						id : 'ksrq',
						width:this.rxcfg.width,
						format:this.rxcfg.dateFormat,
						fieldLabel : this.rxcfg.startDateLabel,
						vtype : 'daterange',
						endDateField : 'jsrq',
						editable:this.rxcfg.editable,
						value:this.rxcfg.defaultValue
					}  ]
					},{
						layout : 'form',
						columnWidth : 0.5,
						border : false,
						items : [{
							xtype : 'datefield',
							anchor : this.rxcfg.anchor,
							name : 'jsrz',
							id : 'jsrq',
							width:this.rxcfg.width,
							format:this.rxcfg.dateFormat,
							fieldLabel : this.rxcfg.endDateLabel,
							vtype : 'daterange',
							startDateField : 'ksrq',
							editable:this.rxcfg.editable,
							value:this.rxcfg.defaultValue
						}]
					}]
				})
		}else{
			
		}*/
		clean.DateRange.superclass.initComponent.call(this);
	}
});
Ext.reg('daterange', clean.DateRange);

/**
 * 方式1：
 * north 布局 默认转换为column; east默认转换为form; button单独占一列(必须)
 * columnSize : 列宽数组类型(必须),包含按钮列宽
 * <br>
 * 方式2：
 * columnComps : 控件列表可以直接使用items默认按items顺序生成组件列表;也可以使用二维数组
 * <br>
 * buttonLayout : 按钮排列方式 0横向,1纵向,默认1 非必须参数，默认根据控件行数排列按钮
 * <br>
 * bug : 有hidden控件时，将hidden控件放在items后边,使用xtype:hidden写法
 */
clean.ColumnFrom = Ext.extend(Ext.FormPanel, {
	
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.columnSize = config.columnSize; //数组
		this.rxcfg.columnComps = config.columnComps || config.items; //二维数组数组
		this.rxcfg.buttonLayout = config.buttonLayout || '0'; //按钮排列，默认横向
		this.rxcfg.height = config.height || 'auto';
		this.rxcfg.width = config.width || 'auto';
        clean.ColumnFrom.superclass.constructor.call(this, config);
    },
	initComponent : function() {
		if(this.region=='north' || PANEL_DISPLAY_MODE == '1'){
			if(!this.rxcfg.columnSize){
				Ext.Msg.alert('错误信息','布局异常，缺少[columnSize]属性。');
				return ;
			}
			
			var columnSize = this.rxcfg.columnSize;
			var columnLength = this.buttons ? columnSize.length-1 : columnSize.length;
			var panelVal = [];
			var rsize = 0;
			if(!this.rxcfg.columnComps[0][0]){
				var columnComps = this.rxcfg.columnComps;
				var compsLength = columnComps.length;
				//计算真实控件真实数量，排除hidden
				var realSize = compsLength;
				//排除hidden
				for(var i=0; i< compsLength; i++){
					 if(columnComps[i].xtype && columnComps[i].xtype=='hidden' ){
						 realSize--;
					 }
					 //如果是daterange
					 if(columnComps[i].xtype && columnComps[i].xtype=='daterange' ){
						 realSize++;
					 }
				}
				//行
				rsize = Math.ceil(realSize/columnLength);
				//处理普通form控件,列
				for(var i=0,index=0;i<columnLength;i++){
					var rAry = [];
					//行
					for(var j=0;j<rsize && index < compsLength; j++){
						if(columnComps[index].xtype !=null && columnComps[index].xtype=='daterange' ){
							j++;
						}
						rAry.push(columnComps[index++]);
					}
					//防止数组越界
					if(rAry.length == 0){
						break;
					}
					//将剩余hidden添加到最后一个布局中
					if(i == columnLength-1){
						for(var k = index ;k<columnComps.length;k++){
							rAry.push(columnComps[k]);
						}
					}
					
					var temp =  {
							layout : 'form',
							columnWidth : columnSize[i],
							border : false,
							items : rAry
						}
					
					panelVal.push(temp);
				}
			}else{
				if(columnLength != this.rxcfg.columnComps.length ){
					Ext.Msg.alert('错误信息','属性异常，columnComps长度 与columnSize属性不匹配。');
					return ;
				}
				rsize = this.rxcfg.columnComps[0].length ;
				//普通form组件
				for(var i=0;i<columnLength;i++){
					var temp =  {
							layout : 'form',
							columnWidth : columnSize[i],
							border : false,
							items : [ this.rxcfg.columnComps[i] ]
						}
					
					panelVal.push(temp);
				}
			}
			
			//处理button
			if(this.buttons){
				var bAry = [];
				for(var j=0;j<this.buttons.length;j++){
					var o = {};
					Ext.applyIf(o, this.buttons[j]);
					bAry.push(o);
				}
				
				var btemp ;
		    	if(rsize > 1 || this.rxcfg.buttonLayout =='1' ){ //控件行数>1  纵向排列
				   btemp = {
						layout : 'form',
						columnWidth : columnSize[columnLength],
						defaults :{
							xtype:'button',
							style:{ marginLeft : '10px', marginTop : '2px' }
						},
						border : false,
						items : bAry
					}
			    }else{
				   btemp = {
						   layout:'table',
						   columnWidth : columnSize[columnLength],
						   defaults :{
								xtype:'button',
								style:{ marginLeft : '10px' }
						   },
						   border : false,
						   layoutConfig: {columns:this.buttons.length},
						   items :bAry
					}
			   }
			   panelVal.push(btemp);
			}
			this.buttons = null;
		 
			//设置高度
			if(this.rxcfg.height=='auto'){
				this.height = 29*rsize+40-rsize;
			}

			Ext.apply(this, {
				border:false,
				layout : 'column',
				region :'north',
				labelAlign:'right',
				bodyStyle:'padding:2px',
				items : panelVal
			});
		}else{
			Ext.applyIf(this, {
				border:false,
				layout:'form',
				items : this.rxcfg.items
			});
		}
		clean.ColumnFrom.superclass.initComponent.call(this);
	}
});
Ext.reg('columnform', clean.ColumnFrom);


/************************************************************* Ajax Util ********************************************************************/
cleanAjax = {};
cleanAjax.request = function(config) {
	
	var callback = config.callback||function(){};
	var url = config.url;
	var params = config.params;
	var scope = config.scope||window;
	var timeout = config.timeout||30000;
	var showMsg = config.showMsg || '1';
	 
	Ext.Ajax.request({
		url :  url,
		method : 'POST',
		params : params,
		timeout:timeout,
		callback : function(options, success,response) {
			if(success) {
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success) {
					if(showMsg=='1'){
						Ext.Msg.alert('提示信息', json.info);
					}
					callback.call(scope,options,success,response);
				} else {
					Ext.Msg.alert('错误', json.info);
				}
			} else {
				Ext.Msg.alert('错误', '请求失败 status:' + response.status +"!");
			}
		}
	});
}

/********************************************************** Form Util **********************************************************************/
cleanForm = {};
cleanForm.submit = function(config) {
			
		var waitMsg = config.waitMsg||'正在提交请稍后....';
		var form = config.form;
		var callback = config.callback||function(){};
		var params = config.params;
		var scope = config.scope||window;
		
		Ext.Msg.wait(waitMsg);
		form.getForm().submit({
					clientValidation : true,
					params:params,
					success : function(form, action) {
						Ext.Msg.alert('提示信息',action.result.info);
						callback.call(scope,form, action);
					},
					failure : function(form, action) {
						switch (action.failureType) {
							case Ext.form.Action.CLIENT_INVALID :
								Ext.Msg.alert('错误','表单未通过校验!');
								break;
							case Ext.form.Action.CONNECT_FAILURE :
								Ext.Msg.alert('错误','与服务器通讯异常!');
								break;
							case Ext.form.Action.SERVER_INVALID :
								Ext.Msg.alert('错误',action.result.info);
						}
					}
	});
}

/*************************************************************大批量导出工具 Ajax Util ********************************************************************/
//调用方式
//exp_AjaxRequest('exportData','string','grid_store');
//exp_AjaxRequest('exportData','object','grid_store');
//exp_AjaxRequest('exportData');
exp_AjaxRequest = function(fn,type,param) {
	//导出专用event.
	if(window.onbeforeunload==null){
		window.onbeforeunload=function(){
			window.parent.destoryAction();
		};
	}
	//
	Ext.Ajax.request({
		timeout : 10 * 60000,
		url : pathUrl + '/loginAjax.do?method=getThreadStatus' ,
		method : 'POST',
		failure : function(response, options) {
		},
		success : function(response, options) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				 if(json.isExport){
					Ext.Msg.confirm('提示信息', json.info, 
							function(btn) {
								if(btn == 'yes')
								{
									if(type){
										if(type == 'string'){
											eval(fn+'(\''+param+'\')');
										}else{
											eval(fn+'('+param+')');
										}
									}else{
										eval(fn+'()');
									}
								}
							}
						);
				 }else{
				 	if(type){
						if(type == 'string'){
							eval(fn+'(\''+param+'\')');
						}else{
							eval(fn+'('+param+')');
						}
					}else{
						eval(fn+'()');
					}
				 }
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	});
}

/**
 * 通用的下拉选择控件
 * @param {} config
 */
ArrayCombo = function(config) {
	
	var anchor = config.anchor ? config.anchor : '81%';
	var hiddenName = config.hiddenName ? config.hiddenName : 'arrayCombo';
	var data = config.data;
	var id = config.id ? config.id : 'arrayComboSelector';
	var defaultValue = config.defaultValue ? config.defaultValue : '';
	var fieldLabel = config.fieldLabel ? config.fieldLabel : '';
	var itemCls = config.itemCls ? config.itemCls : '';
	
	var arrayStore = new Ext.data.ArrayStore({
		fields : ['valueField','displayField'],
		data : data
	});
	
	ArrayCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		mode: 'local',
		displayField : 'displayField',
		valueField : 'valueField',
		triggerAction : "all",
		editable: false,
		itemCls  : itemCls,
		value : defaultValue,
		fieldLabel : fieldLabel,
		store: arrayStore,
		hiddenName : hiddenName,
		name : hiddenName
	});
	//是否有事件
	if(config.listeners){
		ArrayCombo.superclass.constructor.call(this,{
	        listeners: config.listeners
		});
	}
	//赋值宽度
	if(config.width){
		ArrayCombo.superclass.constructor.call(this,{
			width: config.width
		});
	}else{
		ArrayCombo.superclass.constructor.call(this,{
			anchor : anchor
		});
	}
}
Ext.extend(ArrayCombo,Ext.form.ComboBox);

/**
 * 通用的Ajax请求下拉选择控件
 * @param {} config
 */
UrlRemoteCombo = function(config) {
	
	var anchor = config.anchor ? config.anchor : '81%';
	var hiddenName = config.hiddenName ? config.hiddenName : 'urlCombo'+new Date().getTime();
	var url = config.url;
	var displayField = config.displayField;
	var valueField = config.valueField;
	var selectFirst = config.selectFirst || false;
	var autoLoad = config.autoLoad || true;
	var listeners = config.listeners || {};
	var id = config.id ? config.id : 'arrayComboSelector';
	var defaultValue = config.defaultValue ? config.defaultValue : '';
	var fieldLabel = config.fieldLabel ? config.fieldLabel : '';
	var itemCls = config.itemCls ? config.itemCls : '';
	
	var store = new Ext.data.JsonStore({
		url : url,
		root : 'results',
		totalProperty : 'totalCount',
		fields : [displayField, valueField]
	});
	
	if(selectFirst) {
		store.on("load",function() {
			if(store.getCount() > 0) {
				var cmp = Ext.getCmp(id);
				cmp.setValue(store.getAt(0).get(valueField));
				cmp.fireEvent('select', cmp, store.getAt(0), 0); 
			}
		});
	}
	
	if(autoLoad)
		store.load();
	
	UrlRemoteCombo.superclass.constructor.call(this,{
        id: id,
		autoSelect:true,
		store : store,
		mode: 'local',
		displayField : displayField,
		valueField : valueField,
		triggerAction : "all",
		editable: false,
		anchor : anchor,
		listeners : listeners,
		itemCls  : itemCls,
		fieldLabel : fieldLabel,
		hiddenName : hiddenName,
		name : hiddenName
	});
}
Ext.extend(UrlRemoteCombo,Ext.form.ComboBox);

/**
 * 树形下拉框
 * @param {} config
 */
var TreeCombo=function(config){
	var id = config.id || 'treeCombo';
	var fieldLabel = config.fieldLabel || '';
	var anchor = config.anchor || '91%';
	var listWidth = config.listWidth || 180;
	var listHeight = config.listWidth || 320;
	var rootExpand = config.is_expand || false;
	var allowBlank = config.allowBlank || false;
	var expandFn = config.expandFn;
	var hiddenName = config.hiddenName || 'treeComboHiddenName';
	var filteClickFn = config.filteClickFn || null;
	var getTextFn = config.getTextFn || null;
	
	//配置根节点
	var root = config.root;
	if (!root) {
		var rootId = config.rootId;
		var rootName = config.rootName;
		var customerAttr = config.customerAttr ? config.customerAttr : null;
		
		root = new Ext.tree.AsyncTreeNode({
			id : rootId,
			text : rootName,
			customerAttr : customerAttr,
			children : [{
				text : 'loading',
				cls : 'x-tree-node-loading',
				leaf : true
			}]
		});
		
		if (expandFn != undefined) {
			root.on('expand', expandFn);
		}
	}
	
	TreeCombo.superclass.constructor.call(this, {
		id : id,
		autoSelect : true,
		mode : 'local',
		triggerAction : "all",
		editable : false,
		valueField : 'valueField',
		displayField : 'displayField',
		name : hiddenName,
		allowBlank : allowBlank,
		hiddenName : hiddenName,
		anchor : anchor,
		fieldLabel : fieldLabel,
		store : {
			xtype : 'arraystore',
			fields : ['displayField', 'valueField'],
			data : [['', '']]
		}
	});
	
	this.setVal = function(text,value) {
		var comboStore = Ext.getCmp(id).getStore();
		comboStore.removeAll();
		comboStore.insert(0, new Ext.data.Record({displayField : text, valueField : value}));
		Ext.getCmp(id).setValue(value);
	}
	
	var menu = null;
	//展开事件
	this.expand = function() {
		if (this.menu == null) {
			this.menu = new Ext.menu.Menu({
				items : [{
					xtype : 'treepanel',
					border : false,
					id : id + 'Tree',
					autoScroll : true,
					width : listWidth,
					height : listHeight,
					bodyStyle : 'padding:2px;',
					rootVisible : true,
					root : root,
					listeners : {
						click : function(node) {
							if(!filteClickFn || !filteClickFn(node)) {
								return;
							}
							var comboStore = Ext.getCmp(id).getStore();
							comboStore.removeAll();
							var text = getTextFn != null ? getTextFn(node) : node.text;
							comboStore.insert(0, new Ext.data.Record({displayField : text, valueField : node.id}));
							Ext.getCmp(id).setValue(node.id);
							//关闭面板
							this.ownerCt.hide();
							//回调函数
							if (null != config.callback) {
								config.callback(node.id);
							}
						}
					}
				}]
			});
			this.menu.show(this.el, "tl-bl?");
			Ext.getCmp(id + 'Tree').getRootNode().expand();
		} else {
			this.menu.show(this.el, "tl-bl?");
		}
	};
}
Ext.extend(TreeCombo,Ext.form.ComboBox);